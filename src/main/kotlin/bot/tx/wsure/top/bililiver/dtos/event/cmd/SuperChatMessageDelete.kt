package bot.tx.wsure.top.bililiver.dtos.event.cmd
import bot.tx.wsure.top.bililiver.enums.NoticeCmd
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class SuperChatMessageDelete(
    @SerialName("ids")
    val ids: List<Int>
)