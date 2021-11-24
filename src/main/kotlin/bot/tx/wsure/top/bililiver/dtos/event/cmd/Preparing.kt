package bot.tx.wsure.top.bililiver.dtos.event.cmd

import bot.tx.wsure.top.bililiver.enums.NoticeCmd
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Preparing(
    @SerialName("cmd")
    val cmd: NoticeCmd,
    @SerialName("roomid")
    val roomid: String
)