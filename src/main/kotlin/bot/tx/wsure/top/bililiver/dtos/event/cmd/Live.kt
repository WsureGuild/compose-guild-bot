package bot.tx.wsure.top.bililiver.dtos.event.cmd

import bot.tx.wsure.top.bililiver.enums.NoticeCmd
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Live(
    @SerialName("cmd")
    val cmd: NoticeCmd,
    @SerialName("live_key")
    val liveKey: String,
    @SerialName("live_model")
    val liveModel: Int,
    @SerialName("live_platform")
    val livePlatform: String,
    @SerialName("roomid")
    val roomid: Int,
    @SerialName("sub_session_key")
    val subSessionKey: String,
    @SerialName("voice_background")
    val voiceBackground: String
)