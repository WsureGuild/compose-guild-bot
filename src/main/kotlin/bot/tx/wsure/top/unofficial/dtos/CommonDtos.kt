package bot.tx.wsure.top.unofficial.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildSender(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("user_id")
    val userId: Long
)

@Serializable
data class GroupSender(
    val age: Int,
    val area: String,
    val card: String,
    val level: String,
    val nickname: String,
    val role: String,
    val sex: String,
    val title: String,
    @SerialName("user_id")
    val userId: Long
)

@Serializable
data class Anonymous(
    val id:	Long , //	匿名用户 ID
    val name:	String , //	匿名用户名称
    val flag:	String , //	匿名用户 flag, 在调用禁言 API 时需要传入
)