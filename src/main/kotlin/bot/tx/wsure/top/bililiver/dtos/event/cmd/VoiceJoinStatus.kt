package bot.tx.wsure.top.bililiver.dtos.event.cmd

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VoiceJoinStatus(
    @SerialName("channel")
    val channel: String,
    @SerialName("channel_type")
    val channelType: String,
    @SerialName("current_time")
    val currentTime: Int,
    @SerialName("guard")
    val guard: Int,
    @SerialName("head_pic")
    val headPic: String,
    @SerialName("room_id")
    val roomId: Int,
    @SerialName("start_at")
    val startAt: Int,
    @SerialName("status")
    val status: Int,
    @SerialName("uid")
    val uid: Int,
    @SerialName("user_name")
    val userName: String,
    @SerialName("web_share_link")
    val webShareLink: String
)