package bot.tx.wsure.top.bililiver.dtos.event.cmd
import bot.tx.wsure.top.bililiver.dtos.event.CmdType
import bot.tx.wsure.top.bililiver.enums.NoticeCmd
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class NoticeMsg(
    @SerialName("business_id")
    val businessId: String,
    @SerialName("cmd")
    val cmd: NoticeCmd,
    @SerialName("full")
    val full: Full,
    @SerialName("half")
    val half: Half,
    @SerialName("id")
    val id: Int,
    @SerialName("link_url")
    val linkUrl: String,
    @SerialName("msg_common")
    val msgCommon: String,
    @SerialName("msg_self")
    val msgSelf: String,
    @SerialName("msg_type")
    val msgType: Int,
    @SerialName("name")
    val name: String,
    @SerialName("real_roomid")
    val realRoomid: Int,
    @SerialName("roomid")
    val roomid: Int,
    @SerialName("scatter")
    val scatter: Scatter,
    @SerialName("shield_uid")
    val shieldUid: Int,
    @SerialName("side")
    val side: Side
)

@Serializable
data class Full(
    @SerialName("background")
    val background: String,
    @SerialName("color")
    val color: String,
    @SerialName("head_icon")
    val headIcon: String,
    @SerialName("head_icon_fa")
    val headIconFa: String,
    @SerialName("head_icon_fan")
    val headIconFan: Int,
    @SerialName("highlight")
    val highlight: String,
    @SerialName("tail_icon")
    val tailIcon: String,
    @SerialName("tail_icon_fa")
    val tailIconFa: String,
    @SerialName("tail_icon_fan")
    val tailIconFan: Int,
    @SerialName("time")
    val time: Int
)

@Serializable
data class Half(
    @SerialName("background")
    val background: String,
    @SerialName("color")
    val color: String,
    @SerialName("head_icon")
    val headIcon: String,
    @SerialName("highlight")
    val highlight: String,
    @SerialName("tail_icon")
    val tailIcon: String,
    @SerialName("time")
    val time: Int
)

@Serializable
data class Scatter(
    @SerialName("max")
    val max: Int,
    @SerialName("min")
    val min: Int
)

@Serializable
data class Side(
    @SerialName("background")
    val background: String,
    @SerialName("border")
    val border: String,
    @SerialName("color")
    val color: String,
    @SerialName("head_icon")
    val headIcon: String,
    @SerialName("highlight")
    val highlight: String
)