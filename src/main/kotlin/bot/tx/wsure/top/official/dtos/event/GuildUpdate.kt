package bot.tx.wsure.top.official.dtos.event
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class GuildUpdate(
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String,
    @SerialName("id")
    val id: String,
    @SerialName("joined_at")
    val joinedAt: String,
    @SerialName("max_members")
    val maxMembers: Int,
    @SerialName("member_count")
    val memberCount: Int,
    @SerialName("name")
    val name: String,
    @SerialName("op_user_id")
    val opUserId: String,
    @SerialName("owner_id")
    val ownerId: String
)