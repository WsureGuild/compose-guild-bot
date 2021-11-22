package bot.tx.wsure.top.bililiver.dtos.event.cmd

import bot.tx.wsure.top.bililiver.dtos.event.Gift
import bot.tx.wsure.top.bililiver.dtos.event.MedalInfo
import bot.tx.wsure.top.bililiver.dtos.event.UserInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuperChatMessage(
    @SerialName("background_bottom_color")
    val backgroundBottomColor: String,
    @SerialName("background_color")
    val backgroundColor: String,
    @SerialName("background_color_end")
    val backgroundColorEnd: String,
    @SerialName("background_color_start")
    val backgroundColorStart: String,
    @SerialName("background_icon")
    val backgroundIcon: String,
    @SerialName("background_image")
    val backgroundImage: String,
    @SerialName("background_price_color")
    val backgroundPriceColor: String,
    @SerialName("color_point")
    val colorPoint: Double,
    @SerialName("dmscore")
    val dmscore: Int,
    @SerialName("end_time")
    val endTime: Int,
    @SerialName("gift")
    val gift: Gift,
    @SerialName("id")
    val id: Int,
    @SerialName("is_ranked")
    val isRanked: Int,
    @SerialName("is_send_audit")
    val isSendAudit: Int,
    @SerialName("medal_info")
    val medalInfo: MedalInfo?,
    @SerialName("message")
    val message: String,
    @SerialName("message_font_color")
    val messageFontColor: String,
    @SerialName("message_trans")
    val messageTrans: String,
    @SerialName("price")
    val price: Int,
    @SerialName("rate")
    val rate: Int,
    @SerialName("start_time")
    val startTime: Int,
    @SerialName("time")
    val time: Int,
    @SerialName("token")
    val token: String,
    @SerialName("trans_mark")
    val transMark: Int,
    @SerialName("ts")
    val ts: Int,
    @SerialName("uid")
    val uid: Int,
    @SerialName("user_info")
    val userInfo: UserInfo
)