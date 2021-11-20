package bot.tx.wsure.top.unofficial.dtos.event.message

import bot.tx.wsure.top.unofficial.dtos.event.BaseEventDto
import bot.tx.wsure.top.unofficial.enums.MessageTypeEnums
import bot.tx.wsure.top.unofficial.enums.PostTypeEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class MessageEvent(
    @SerialName("message_type")
    val messageType:MessageTypeEnums,
    ): BaseEventDto(PostTypeEnum.MESSAGE)