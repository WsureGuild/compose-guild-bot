package bot.tx.wsure.top.bililiver.dtos.api.danmu
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class DanmuRes(
    @SerialName("mode_info")
    val modeInfo: ModeInfo
)

@Serializable
data class ModeInfo(
    @SerialName("extra")
    val extra: String,
    @SerialName("mode")
    val mode: Int,
    @SerialName("show_player_type")
    val showPlayerType: Int
)