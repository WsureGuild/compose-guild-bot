package bot.tx.wsure.top.unofficial

import bot.tx.wsure.top.common.BaseBotListener
import bot.tx.wsure.top.unofficial.dtos.api.BaseAction
import bot.tx.wsure.top.unofficial.dtos.event.BaseEventDto
import bot.tx.wsure.top.unofficial.dtos.event.message.GuildMessage
import bot.tx.wsure.top.unofficial.dtos.event.message.MessageEvent
import bot.tx.wsure.top.unofficial.enums.MessageTypeEnums
import bot.tx.wsure.top.unofficial.enums.PostTypeEnum
import bot.tx.wsure.top.unofficial.intf.UnOfficialBotEvent
import bot.tx.wsure.top.utils.JsonUtils.jsonToObjectOrNull
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import okhttp3.WebSocket
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class UnOfficialBotListener(
    private val officialEvents:List<UnOfficialBotEvent> = emptyList()
): BaseBotListener() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        logger.info("onOpen ")
    }

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
                                        officialEvents.onEach { runBlocking { it.onGuildMessage(UnofficialMessageSender(webSocket),guildMessage) } }
                                    }
                                }
                            }
                        }
                    }
                    PostTypeEnum.NOTICE -> {

                    }
                }
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

    private fun WebSocket.sendAndPrintLog(text: String, isHeartbeat:Boolean = false){
        if(isHeartbeat){
            logger.debug("send Heartbeat $text")
        } else {
            logger.info("send text message $text")
        }
        this.send(text)
    }

    override fun cancel(){
        super.cancel()
        // do something cancel heartbeat
    }

}
class UnofficialMessageSender(val webSocket: WebSocket){
    val logger: Logger = LoggerFactory.getLogger(BaseBotListener::class.java)

    fun sendMessage(text: String){
        logger.info("send message:$text")
        webSocket.send(text)
    }
    inline fun <reified T:BaseAction<*>> sendMessage(text:T){
        val message = text.objectToJson()
        logger.info("send message:$message")
        webSocket.send(message)
    }
}