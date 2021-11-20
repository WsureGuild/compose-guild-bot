package bot.tx.wsure.top.unofficial.dtos.event

import bot.tx.wsure.top.unofficial.enums.PostTypeEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseEventDto(
    @SerialName("post_type")
    val postType: PostTypeEnum,
)