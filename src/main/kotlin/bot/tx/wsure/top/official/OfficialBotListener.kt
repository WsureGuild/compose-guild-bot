package bot.tx.wsure.top.official

import bot.tx.wsure.top.common.BaseBotListener
import bot.tx.wsure.top.official.dtos.DispatchDto
import bot.tx.wsure.top.official.dtos.DispatchEnums
import bot.tx.wsure.top.official.dtos.IdentifyOpDto
import bot.tx.wsure.top.official.dtos.event.AtMessageCreateEvent
import bot.tx.wsure.top.official.dtos.operation.HeartbeatDto
import bot.tx.wsure.top.official.dtos.operation.Operation
import bot.tx.wsure.top.official.enums.OPCodeEnums
import bot.tx.wsure.top.official.intf.OfficialBotEvent
import bot.tx.wsure.top.utils.JsonUtils.jsonToObjectOrNull
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
import bot.tx.wsure.top.utils.ScheduleUtils
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import okhttp3.WebSocket
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.atomic.AtomicLong


class OfficialBotListener(
    token:String,
    private val officialEvents:List<OfficialBotEvent> = emptyList(),
    private val heartbeatDelay: Long = 30000,
    private val reconnectTimeout: Long = 60000,
): BaseBotListener() {
    private var hbTimer: Timer? = null

    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val identifyOpDto = IdentifyOpDto(token).objectToJson()
    private val messageCount by lazy { AtomicLong(0) }

    private val lastReceivedHeartBeat = AtomicLong(0)


    override fun onOpen(webSocket: WebSocket, response: Response) {
        logger.info("onOpen ")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        logger.debug("received message $text")
        text.jsonToObjectOrNull<Operation>()?.also { opType ->

            when(opType.type()){
                OPCodeEnums.Heartbeat_ACK -> {
                    lastReceivedHeartBeat.getAndSet(System.currentTimeMillis())
                }
                //首次连接 发送Identify信息鉴权
                OPCodeEnums.Heartbeat_Config -> {
                    // 初始化操作
                    initConnection(webSocket)
                }
                //收到事件
                OPCodeEnums.Dispatch -> {
                    text.jsonToObjectOrNull<DispatchDto>()?.also { dispatchDto ->
                        messageCount.getAndSet(dispatchDto.s)
                        when(dispatchDto.type){
                            DispatchEnums.AT_MESSAGE_CREATE -> {
                                text.jsonToObjectOrNull<AtMessageCreateEvent>()?.also { guildAtMessage ->
                                    logger.debug("received AT_MESSAGE_CREATE ${guildAtMessage.objectToJson()}")
                                    officialEvents.forEach { runBlocking { it.onAtMessageCreate(guildAtMessage) } }
                                }
                            }

                        }
                    }
                }
                OPCodeEnums.Reconnect -> {
                    logger.warn("need reconnect !!")
                    reconnectClient()
                }
                OPCodeEnums.Invalid_Session -> {
                    webSocket.cancel()
                    hbTimer?.cancel()
                    logger.error(OPCodeEnums.Invalid_Session.description)
                    throw RuntimeException(OPCodeEnums.Invalid_Session.description)
                }

            }
        }




    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        logger.warn("onFailure try to reconnect")
        reconnectClient()
    }

    fun WebSocket.logInfo():String{
        return "${this.request().url} - ${this.request().body.toString()}"
    }

    private fun initConnection(webSocket: WebSocket){
        // 鉴权
        webSocket.sendAndPrintLog(identifyOpDto)
        // 启动心跳发送
        lastReceivedHeartBeat.getAndSet(System.currentTimeMillis())
        val processor = createHeartBeatProcessor(webSocket)
        //  先取消以前的定时器
        hbTimer?.cancel()
        // 启动新的心跳
        hbTimer = ScheduleUtils.loopEvent(processor,Date(),heartbeatDelay)
    }

    private fun createHeartBeatProcessor(webSocket: WebSocket):suspend () ->Unit {
        return suspend {
            val last = lastReceivedHeartBeat.get()
            val now = System.currentTimeMillis()
            if( now - last > reconnectTimeout){
                logger.warn("heartbeat timeout , try to reconnect")
                reconnectClient()
            } else {
                val hb = HeartbeatDto(messageCount.get()).objectToJson()
                webSocket.sendAndPrintLog(hb,true)

            }

        }
    }

    private fun reconnectClient(){
        hbTimer?.cancel()
        reconnect()
    }

    private fun WebSocket.sendAndPrintLog(text: String, isHeartbeat:Boolean = false){
        if(isHeartbeat){
            logger.debug("send Heartbeat $text")
        } else {
            logger.info("send text message $text")
        }
        this.send(text)
    }
}