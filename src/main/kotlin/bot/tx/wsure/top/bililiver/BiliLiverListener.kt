package bot.tx.wsure.top.bililiver

import bot.tx.wsure.top.bililiver.api.BiliLiverChatUtils.toChatPackage
import bot.tx.wsure.top.bililiver.dtos.event.ChatPackage
import bot.tx.wsure.top.bililiver.dtos.event.EnterRoom
import bot.tx.wsure.top.bililiver.enums.Operation
import bot.tx.wsure.top.common.BaseBotListener
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class BiliLiverListener(
    val roomId: String,
    val token: String,
    val biliLiverEvents: List<BiliLiverEvent>
) : BaseBotListener() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    val enterRoom = EnterRoom(roomId.toLong(),token)
    override fun onOpen(webSocket: WebSocket, response: Response) {
        logger.info("onOpen ,send enterRoom package")
        webSocket.send(enterRoom.toPackage().encode())
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        val pkg = kotlin.runCatching { bytes.toChatPackage() }.getOrNull()

        //如果无法解析消息，直接打印hex并返回
        if(pkg == null){
            logger.warn("onMessage ,but can't decode hex:${ bytes.hex() }")
            return
        }
        logger.info("onMessage ,context:${ pkg.content() }")

        when(pkg.operation){
            Operation.HELLO_ACK -> {
                initHeartbeat()
            }
            Operation.HEARTBEAT_ACK -> {
                receivedHeartbeat()
            }
            Operation.NOTICE -> {
                onNotice(webSocket,pkg)
            }
            else -> {
                logger.debug("unhandled operation")
            }
        }

    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
    }

    private fun onNotice(webSocket: WebSocket, pkg: ChatPackage) {

    }
    private fun initHeartbeat() {
        TODO("Not yet implemented")
    }

    private fun receivedHeartbeat() {
        TODO("Not yet implemented")
    }
}
