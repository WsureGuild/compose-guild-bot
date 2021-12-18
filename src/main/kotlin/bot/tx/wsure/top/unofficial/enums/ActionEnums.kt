package bot.tx.wsure.top.unofficial.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ActionEnums {
    @SerialName("send_guild_channel_msg")
    SEND_GUILD_CHANNEL_MSG,
    @SerialName("send_group_msg")
    SEND_GROUP_MSG,

    ;
}