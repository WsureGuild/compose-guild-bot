package bot.tx.wsure.top.bililiver

import bot.tx.wsure.top.common.BaseBotListener
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString

class BiliLiverListener(
    val roomId: String,
    val biliLiverEvents: List<BiliLiverEvent>
) : BaseBotListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {

    }

    override fun onMessage(webSocket: WebSocket, text: String) {
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
    }

}
