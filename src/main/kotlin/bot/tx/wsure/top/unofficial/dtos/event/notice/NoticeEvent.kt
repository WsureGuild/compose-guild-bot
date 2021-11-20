package bot.tx.wsure.top.unofficial.dtos.event.notice

import bot.tx.wsure.top.unofficial.dtos.event.BaseEventDto
import bot.tx.wsure.top.unofficial.enums.NoticeTypeEnums
import bot.tx.wsure.top.unofficial.enums.PostTypeEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class NoticeEvent(
    @SerialName("notice_type")
    val noticeType:NoticeTypeEnums
): BaseEventDto(PostTypeEnum.NOTICE)