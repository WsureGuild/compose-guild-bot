package bot.tx.wsure.top.unofficial.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class NoticeTypeEnums {
    @SerialName("message_reactions_updated")
    MESSAGE_REACTIONS_UPDATED,
    @SerialName("channel_updated")
    CHANNEL_UPDATED,
    @SerialName("channel_created")
    CHANNEL_CREATED,
    @SerialName("channel_destroyed")
    CHANNEL_DESTROYED,
}