package bot.tx.wsure.top.unofficial.dtos.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendGroupMsg(
    @SerialName("group_id")
    val groupId: Long,                  //-	群号
    val message: String,                //-	要发送的内容
    @SerialName("auto_escape")
    val autoEscape: Boolean = false,            //false	消息内容是否作为纯文本发送 ( 即不解析 CQ 码) , 只在 message 字段是字符串时有效
){

    @Serializable
    data class Response(
        @SerialName("message_id")
        val messageId : Long
    )
}
