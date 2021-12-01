package bot.tx.wsure.top.bililiver.dtos.event.cmd

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CommonNoticeDanmaku(
    @SerialName("content_segments")
    val contentSegments: List<ContentSegment>,
    @SerialName("dmscore")
    val dmscore: Int,
    @SerialName("terminals")
    val terminals: List<Int>
)

@Serializable
data class ContentSegment(
    @SerialName("font_color")
    val fontColor: String,
    @SerialName("text")
    val text: String,
    @SerialName("type")
    val type: Int
)