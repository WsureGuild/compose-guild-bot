package bot.tx.wsure.top.bililiver.dtos.event

import bot.tx.wsure.top.bililiver.enums.NoticeCmd
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class CmdType(
    @SerialName("cmd")
    open val cmd: NoticeCmd
)
@Serializable
open class ChatCmdBody<T>(
    val cmd: NoticeCmd,
    @SerialName("data")
    val `data`: T,
    @SerialName("roomid")
    val roomid: Int?,
)

@Serializable
data class Gift(
    @SerialName("gift_id")
    val giftId: Int,
    @SerialName("gift_name")
    val giftName: String,
    @SerialName("num")
    val num: Int
)

@Serializable
data class MedalInfo(
    @SerialName("anchor_roomid")
    val anchorRoomid: Int,
    @SerialName("anchor_uname")
    val anchorUname: String,
    @SerialName("guard_level")
    val guardLevel: Int,
    @SerialName("icon_id")
    val iconId: Int,
    @SerialName("is_lighted")
    val isLighted: Int,
    @SerialName("medal_color")
    val medalColor: String?,
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
    @SerialName("special")
    val special: String,
    @SerialName("target_id")
    val targetId: Int
)

@Serializable
data class UserInfo(
    @SerialName("face")
    val face: String,
    @SerialName("face_frame")
    val faceFrame: String,
    @SerialName("guard_level")
    val guardLevel: Int,
    @SerialName("is_main_vip")
    val isMainVip: Int,
    @SerialName("is_svip")
    val isSvip: Int,
    @SerialName("is_vip")
    val isVip: Int,
    @SerialName("level_color")
    val levelColor: String,
    @SerialName("manager")
    val manager: Int,
    @SerialName("name_color")
    val nameColor: String,
    @SerialName("title")
    val title: String,
    @SerialName("uname")
    val uname: String,
    @SerialName("user_level")
    val userLevel: Int
)