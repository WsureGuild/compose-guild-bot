package bot.tx.wsure.top.unofficial

import bot.tx.wsure.top.common.BaseBotListener
import bot.tx.wsure.top.unofficial.dtos.api.BaseAction
import bot.tx.wsure.top.unofficial.dtos.api.SendGroupMsg
import bot.tx.wsure.top.unofficial.dtos.api.SendGuildChannelMsg
import bot.tx.wsure.top.unofficial.dtos.event.BaseApiRes
import bot.tx.wsure.top.unofficial.dtos.event.BaseEventDto
import bot.tx.wsure.top.unofficial.dtos.event.GoCQApiRes
import bot.tx.wsure.top.unofficial.dtos.event.message.GroupMessage
import bot.tx.wsure.top.unofficial.dtos.event.message.GuildMessage
import bot.tx.wsure.top.unofficial.dtos.event.message.MessageEvent
import bot.tx.wsure.top.unofficial.enums.ActionEnums
import bot.tx.wsure.top.unofficial.enums.MessageTypeEnums
import bot.tx.wsure.top.unofficial.enums.PostTypeEnum
import bot.tx.wsure.top.unofficial.intf.UnOfficialBotEvent
import bot.tx.wsure.top.utils.AsyncUtils
import bot.tx.wsure.top.utils.JsonUtils.jsonToObject
import bot.tx.wsure.top.utils.JsonUtils.jsonToObjectOrNull
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class UnOfficialBotListener(
    private val officialEvents:List<UnOfficialBotEvent> = emptyList()
): BaseBotListener() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val asyncUtil = AsyncUtils<String>()
    override fun onOpen(webSocket: WebSocket, response: Response) {
        logger.info("onOpen ")
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onMessage(webSocket: WebSocket, text: String) {
        kotlin.runCatching {
            stopRetryReconnect()
            logger.debug("received message $text")
            text.jsonToObjectOrNull<BaseEventDto>(false)?.also { event->
                when (event.postType){
                    PostTypeEnum.MESSAGE -> {
                        text.jsonToObjectOrNull<MessageEvent>()?.also { message ->
                            when(message.messageType){
                                MessageTypeEnums.GUILD -> {
                                    text.jsonToObjectOrNull<GuildMessage>()?.also { guildMessage ->
                                        logger.debug("received GUILD_MESSAGE $text")
                                        officialEvents.onEach { GlobalScope.launch { it.onGuildMessage(UnofficialMessageSender(webSocket,asyncUtil),guildMessage) } }
                                    }
                                }
                                MessageTypeEnums.GROUP -> {
                                    text.jsonToObjectOrNull<GroupMessage>()?.also { groupMessage ->
                                        logger.debug("received GROUP_MESSAGE $text")
                                        officialEvents.onEach { GlobalScope.launch { it.onGroupMessage(UnofficialMessageSender(webSocket,asyncUtil),groupMessage) } }
                                    }
                                }
                            }
                        }
                    }
                    PostTypeEnum.NOTICE -> {

                    }
                }
            }
            text.jsonToObjectOrNull<GoCQApiRes>(false)?.also { wsRes ->
                logger.debug("received message $text")
                asyncUtil.onReceive(wsRes.echo,text)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        logger.warn("onClosing code:$code reason:$reason")
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        logger.warn("onClosed code:$code reason:$reason")

    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        logger.error("onFailure response:${response?.message} ",t)
        startRetryReconnect()
    }

    override fun cancel(){
        super.cancel()
        // do something cancel heartbeat
    }

}
class UnofficialMessageSender(val webSocket: WebSocket,val asyncUtils: AsyncUtils<String>){
    val logger: Logger = LoggerFactory.getLogger(BaseBotListener::class.java)

    fun sendMessage(text: String){
        logger.info("send message:$text")
        webSocket.send(text)
    }
    private inline fun <reified T:BaseAction<*>> sendMessage(text:T){
        val message = text.objectToJson()
        logger.info("send message:$message")
        webSocket.send(message)
    }
    private suspend inline fun <reified T:BaseAction<*>,reified R> sendMessageAsync(text:T):BaseApiRes<R>{
        val message = text.objectToJson()
        logger.info("send message:$message")
        webSocket.send(message)
        return asyncUtils.sendAndWaitResult<String>(text.echo).jsonToObject()
    }


    suspend fun sendGroupMsgAsync(msg:SendGroupMsg):SendGroupMsg.Response{
        val msgReq = BaseAction(ActionEnums.SEND_GROUP_MSG,msg)
        return sendMessageAsync<BaseAction<SendGroupMsg>,SendGroupMsg.Response>(msgReq).data
    }

    suspend fun sendGuildChannelMsgAsync(msg:SendGuildChannelMsg):SendGuildChannelMsg.Response{
        val msgReq = BaseAction(ActionEnums.SEND_GUILD_CHANNEL_MSG,msg)
        return sendMessageAsync<BaseAction<SendGuildChannelMsg>,SendGuildChannelMsg.Response>(msgReq).data
    }
}