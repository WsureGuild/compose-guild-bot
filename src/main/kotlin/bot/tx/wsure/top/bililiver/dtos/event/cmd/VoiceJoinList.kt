package bot.tx.wsure.top.bililiver.dtos.event.cmd

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VoiceJoinList(
    @SerialName("apply_count")
    val applyCount: Int,
    @SerialName("category")
    val category: Int,
    @SerialName("red_point")
    val redPoint: Int,
    @SerialName("refresh")
    val refresh: Int,
    @SerialName("room_id")
    val roomId: Int
)