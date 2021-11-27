package bot.tx.wsure.top.official.dtos.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TextMessage(
    @SerialName("content")
    val content:String,
    @SerialName("msg_id")
    val msgId:String,
) {
}