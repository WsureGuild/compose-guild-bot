package bot.tx.wsure.top.common

import bot.tx.wsure.top.utils.UA
import io.ktor.util.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

open class WsBotClient<T:BaseBotListener>(
    private val wsUrl: String,
    private val listener: T
):BotClient {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val wsClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS) //设置读取超时时间
            .writeTimeout(3, TimeUnit.SECONDS) //设置写的超时时间
            .connectTimeout(3, TimeUnit.SECONDS) //设置连接超时时间
            .build()
    }
    private val wsRequest: Request by lazy { Request.Builder()
        .get()
        .addHeader("User-Agent",UA.PC.getValue())
        .url(wsUrl)
        .build()
    }
    private var connectWebSocket = wsClient.newWebSocket(wsRequest, listener)

    init {
        logger.info("url :$wsUrl ")
        listener.reconnect { reconnect() }
    }

    override fun reconnect(){
        connectWebSocket.close(1000,"reconnect")
        logger.info("reconnecting ... ")
        connectWebSocket = wsClient.newWebSocket(wsRequest,listener)
    }

    override fun sendMessage(text: String){
        logger.info("send text message $text")
        connectWebSocket.send(text)
    }
}
