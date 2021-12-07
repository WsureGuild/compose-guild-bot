package bot.tx.wsure.top.bililiver.dtos.event.cmd


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InteractWord(
    @SerialName("contribution")
    val contribution: Contribution,
    @SerialName("dmscore")
    val dmscore: Int,
    @SerialName("fans_medal")
    val fansMedal: FansMedal,
    @SerialName("identities")
    val identities: List<Int>,
    @SerialName("is_spread")
    val isSpread: Int,
    @SerialName("msg_type")
    val msgType: Int,
    @SerialName("roomid")
    val roomid: Long,
    @SerialName("score")
    val score: Long,
    @SerialName("spread_desc")
    val spreadDesc: String,
    @SerialName("spread_info")
    val spreadInfo: String,
    @SerialName("tail_icon")
    val tailIcon: Int,
    @SerialName("timestamp")
    val timestamp: Long,
    @SerialName("trigger_time")
    val triggerTime: Long,
    @SerialName("uid")
    val uid: Long,
    @SerialName("uname")
    val uname: String,
    @SerialName("uname_color")
    val unameColor: String
)

@Serializable
data class FansMedal(
    @SerialName("anchor_roomid")
    val anchorRoomid: Int,
    @SerialName("guard_level")
    val guardLevel: Int,
    @SerialName("icon_id")
    val iconId: Int,
    @SerialName("is_lighted")
    val isLighted: Int,
    @SerialName("medal_color")
    val medalColor: Int,
    @SerialName("medal_color_border")
    val medalColorBorder: Int,
    @SerialName("medal_color_end")
    val medalColorEnd: Int,
    @SerialName("medal_color_start")
    val medalColorStart: Int,
    @SerialName("medal_level")
    val medalLevel: Int,
    @SerialName("medal_name")
    val medalName: String,
    @SerialName("score")
    val score: Long,
    @SerialName("special")
    val special: String,
    @SerialName("target_id")
    val targetId: Int
)
@Serializable
data class Contribution(
    @SerialName("grade")
    val grade: Int
)