package bot.tx.wsure.top.unofficial.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sender(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("user_id")
    val userId: Long
)