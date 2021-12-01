package bot.tx.wsure.top.bililiver.dtos.event.cmd

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VoiceJoinRoomCountInfo(
    @SerialName("apply_count")
    val applyCount: Int,
    @SerialName("notify_count")
    val notifyCount: Int,
    @SerialName("red_point")
    val redPoint: Int,
    @SerialName("room_id")
    val roomId: Int,
    @SerialName("room_status")
    val roomStatus: Int,
    @SerialName("root_status")
    val rootStatus: Int
)