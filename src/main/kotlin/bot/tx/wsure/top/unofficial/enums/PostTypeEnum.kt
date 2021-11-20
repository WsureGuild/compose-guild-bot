package bot.tx.wsure.top.unofficial.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PostTypeEnum {
    @SerialName("message")
    MESSAGE,
    @SerialName("notice")
    NOTICE,
    @SerialName("meta_event")
    META_EVENT
}