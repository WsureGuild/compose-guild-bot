package bot.tx.wsure.top.plugins

import bot.tx.wsure.top.utils.JsonUtils.jsonToObject
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.*
import java.util.concurrent.TimeUnit


fun goCqHttp() {
    val mClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(3, TimeUnit.SECONDS) //设置读取超时时间
        .writeTimeout(3, TimeUnit.SECONDS) //设置写的超时时间
        .connectTimeout(3, TimeUnit.SECONDS) //设置连接超时时间
        .build()

    //连接地址
    val url = "ws://127.0.0.1:6700"
    //构建一个连接请求对象
    val request: Request = Request.Builder().get().url(url).build()
    val wsClient = mClient.newWebSocket(request,object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            println(text)
            val message:GuildMessage? = kotlin.runCatching { text.jsonToObject<GuildMessage>() }.getOrNull()
            if(message != null && message.channelId == 1454836L && message.guildId == 6000051636714649){
                ybb(webSocket,message)
            }
        }
        val catchMap:MutableMap<Long,Long> = mutableMapOf()
        fun ybb(webSocket: WebSocket, message: GuildMessage){
            if(message.message == "ybb"){
                if(catchMap[message.sender.userId] ==null){
                    val speed = (0 .. 1000L).random()
                    catchMap[message.sender.userId] = speed
                    val res = message.buildChannelMessage("${message.sender.nickname},你今天的小火车速度为${speed}km/h,看我把你绑在铁轨上")
                    webSocket.send(res.objectToJson())
                } else {
                    val res = message.buildChannelMessage("${message.sender.nickname},你今天已经被创过了,明天再来吧")
                    webSocket.send(res.objectToJson())
                }
            }
        }

    })

}
@Serializable
data class GuildMessage(
    @SerialName("channel_id") val channelId:Long,
    @SerialName("guild_id") val guildId:Long,
    val message:String,
    @SerialName("message_id") val messageId:String,
    @SerialName("message_type") val messageType:String,
    @SerialName("post_type") val postType:String,
    @SerialName("self_id") val selfId:Long,
    @SerialName("self_tiny_id") val selfTinyId:Long,
    val sender: Sender,
    @SerialName("sub_type") val subType:String,
    val time:Long,
    @SerialName("user_id") val userId:Long,
){

}
@Serializable
data class Sender(
    val nickname:String,
    @SerialName("user_id") val userId:Long
){

}

@Serializable
data class BaseAction<T>(
    val action:String,
    val params:T,
    val echo:String,
)

@Serializable
data class SendGuildChannelMsgAction(
    @SerialName("guild_id") val guildId:Long,
    @SerialName("channel_id") val channelId:Long,
    val message:String
)

@Serializable
data class TextMessage(
    val type:String = "text",
    val data:MutableMap<String,String>
)

fun GuildMessage.buildChannelMessage(text:String):BaseAction<SendGuildChannelMsgAction>{
    return BaseAction("send_guild_channel_msg",
        SendGuildChannelMsgAction(this.guildId,this.channelId, text),
        UUID.randomUUID().toString()
    )
}