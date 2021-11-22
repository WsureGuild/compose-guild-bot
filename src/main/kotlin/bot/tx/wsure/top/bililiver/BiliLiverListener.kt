package bot.tx.wsure.top.bililiver

import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.brotli
import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.toChatPackage
import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.toChatPackageList
import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.zlib
import bot.tx.wsure.top.bililiver.dtos.event.*
import bot.tx.wsure.top.bililiver.dtos.event.cmd.RoomRealTimeMessageUpdate
import bot.tx.wsure.top.bililiver.dtos.event.cmd.SendGift
import bot.tx.wsure.top.bililiver.dtos.event.cmd.SuperChatMessage
import bot.tx.wsure.top.bililiver.enums.NoticeCmd
import bot.tx.wsure.top.bililiver.enums.Operation
import bot.tx.wsure.top.bililiver.enums.ProtocolVersion
import bot.tx.wsure.top.common.BaseBotListener
import bot.tx.wsure.top.utils.JsonUtils.jsonToObject
import bot.tx.wsure.top.utils.JsonUtils.jsonToObjectOrNull
import bot.tx.wsure.top.utils.ScheduleUtils
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class BiliLiverListener(
    val roomId: String,
    val token: String,
    val biliLiverEvents: List<BiliLiverEvent>,
    private val heartbeatDelay: Long = 30000,
    private val reconnectTimeout: Long = 60000,
) : BaseBotListener() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private var hbTimer: Timer? = null
    private val lastReceivedHeartBeat = AtomicLong(0)

    var enterRoom = EnterRoom(roomId.toLong(),token)
    override fun onOpen(webSocket: WebSocket, response: Response) {
        logger.info("onOpen ,send enterRoom package")
        logger.info("enter room hex : ${enterRoom.toPackage().encode().hex()}")
        webSocket.sendAndPrintLog(enterRoom.toPackage())
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        logger.debug("onMessage ,context:${bytes.hex() }")
        val originPkg = bytes.toChatPackage()
        val pkgList = mutableListOf<ChatPackage>()
        when(originPkg.protocolVersion){
            ProtocolVersion.INT,ProtocolVersion.JSON -> pkgList.add(originPkg)
            ProtocolVersion.ZLIB_INFLATE -> pkgList.addAll(originPkg.body.zlib().toChatPackageList())
            ProtocolVersion.BROTLI -> pkgList.addAll(originPkg.body.brotli().toChatPackageList())
            else -> {
                logger.warn("onMessage : unknown Message  ,context:${bytes.hex() }")
                return
            }
        }
        pkgList.onEach { pkg ->
            logger.debug("onMessage ${pkg.protocolVersion},${pkg.operation} ,context:${ pkg.content() }")
            when(pkg.operation){
                Operation.HELLO_ACK -> {
                    initHeartbeat(webSocket)
                }
                Operation.HEARTBEAT_ACK -> {
                    receivedHeartbeat()
                }
                Operation.NOTICE -> {
                    onNotice(pkg)
                }
                else -> {
                    logger.debug("unhandled operation")
                }
            }
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        logger.error("onFailure , try to reconnect",t)
        reconnectClient()
    }

    private fun reconnectClient() {
        hbTimer?.cancel()
        reconnect()
    }

    private fun onNotice(pkg: ChatPackage) {
        val content = pkg.content()

        content.jsonToObjectOrNull<CmdType>()?.also { type ->

            when(type.cmd){
                NoticeCmd.SUPER_CHAT_MESSAGE -> {
                    logger.info("received ${type.cmd.description} :{}",content)
                    content.jsonToObjectOrNull<ChatCmdBody<SuperChatMessage>>()?.also { superChat ->
                        biliLiverEvents.onEach {
                            it.onSuperChatMessage(superChat.data)
                        }
                    }
                }
                NoticeCmd.SEND_GIFT -> {
                    logger.debug("received ${type.cmd.description} :{}",content)
                    content.jsonToObjectOrNull<ChatCmdBody<SendGift>>()?.also { sendGift ->
                        biliLiverEvents.onEach {
                            it.onSendGift(sendGift.data)
                        }
                    }
                }
                NoticeCmd.ROOM_REAL_TIME_MESSAGE_UPDATE -> {
                    logger.debug("received ${type.cmd.description} :{}",content)
                    content.jsonToObjectOrNull<ChatCmdBody<RoomRealTimeMessageUpdate>>()?.also { roomRealTimeMessageUpdate ->
                        biliLiverEvents.onEach {
                            it.onRoomRealTimeMessageUpdate(roomRealTimeMessageUpdate.data)
                        }
                    }
                }
                else -> {}
            }
        }
    }
    private fun initHeartbeat(webSocket:WebSocket) {
        lastReceivedHeartBeat.getAndSet(System.currentTimeMillis())
        val processor = createHeartBeatProcessor(webSocket)
        //  先取消以前的定时器
        hbTimer?.cancel()
        // 启动新的心跳
        hbTimer = ScheduleUtils.loopEvent(processor,Date(),heartbeatDelay)
    }

    private fun receivedHeartbeat() {
        lastReceivedHeartBeat.getAndSet(System.currentTimeMillis())
    }

    private fun createHeartBeatProcessor(webSocket: WebSocket):suspend () ->Unit {
        return suspend {
            val last = lastReceivedHeartBeat.get()
            val now = System.currentTimeMillis()
            if( now - last > reconnectTimeout){
                logger.warn("heartbeat timeout , try to reconnect")
                reconnectClient()
            } else {

                webSocket.sendAndPrintLog(HeartbeatPackage,true)

            }

        }
    }

    private fun WebSocket.sendAndPrintLog(pkg: ChatPackage, isHeartbeat:Boolean = false){
        if(isHeartbeat){
            logger.info("send Heartbeat ${pkg.content()}")
        } else {
            logger.info("send text message ${pkg.content()}")
        }
        this.send(pkg.encode())
    }
}
