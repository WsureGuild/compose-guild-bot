package bot.tx.wsure.top.official.dtos.event

import bot.tx.wsure.top.official.dtos.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AtMessageCreateEvent(
    @SerialName("d")
    val d: AtMessageCreateData,
): DispatchDto(0, DispatchEnums.AT_MESSAGE_CREATE)

@Serializable
data class AtMessageCreateData(
    @SerialName("author")
    val author: Author,
    @SerialName("channel_id")
    val channelId: String,
    @SerialName("content")
    val content: String,
    @SerialName("guild_id")
    val guildId: String,
    @SerialName("id")
    val id: String,
    @SerialName("member")
    val member: Member,
    @SerialName("mentions")
    val mentions: List<Mention>,
    @SerialName("timestamp")
    val timestamp: String
){
    fun messageContent():String{
        return content.replace(Regex("<@!\\d+>"),"")
    }
}