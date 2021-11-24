package bot.tx.wsure.top.bililiver.dtos.event.cmd
import bot.tx.wsure.top.bililiver.enums.NoticeCmd
import bot.tx.wsure.top.bililiver.enums.Operation
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


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