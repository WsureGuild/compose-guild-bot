package bot.tx.wsure.top.bililiver.dtos.event.cmd

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomRealTimeMessageUpdate(
    @SerialName("fans")
    val fans: Int,
    @SerialName("fans_club")
    val fansClub: Int,
    @SerialName("red_notice")
    val redNotice: Int,
    @SerialName("roomid")
    val roomid: Int
)