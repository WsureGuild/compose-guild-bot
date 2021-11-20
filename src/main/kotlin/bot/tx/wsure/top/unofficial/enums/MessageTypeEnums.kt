package bot.tx.wsure.top.unofficial.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MessageTypeEnums {
    @SerialName("guild")
    GUILD,
    @SerialName("group")
    GROUP,
    @SerialName("private")
    PRIVATE
}