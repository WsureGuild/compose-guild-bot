package bot.tx.wsure.top.bililiver.dtos.event.cmd
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class GuardBuy(
    @SerialName("end_time")
    val endTime: Int,
    @SerialName("gift_id")
    val giftId: Int,
    @SerialName("gift_name")
    val giftName: String,
    @SerialName("guard_level")
    val guardLevel: Int,
    @SerialName("num")
    val num: Int,
    @SerialName("price")
    val price: Int,
    @SerialName("start_time")
    val startTime: Int,
    @SerialName("uid")
    val uid: Int,
    @SerialName("username")
    val username: String
)