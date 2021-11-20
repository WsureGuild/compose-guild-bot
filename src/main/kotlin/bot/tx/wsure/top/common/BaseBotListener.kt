package bot.tx.wsure.top.common

import okhttp3.WebSocketListener

abstract class BaseBotListener : WebSocketListener() {
     lateinit var reconnect :()->Unit

    fun reconnect(func:()->Unit){
        reconnect = func
    }

}