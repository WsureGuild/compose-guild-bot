package bot.tx.wsure.top.bililiver.dtos.event.cmd
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class EntryEffect(
    @SerialName("basemap_url")
    val basemapUrl: String,
    @SerialName("business")
    val business: Int,
    @SerialName("copy_color")
    val copyColor: String,
    @SerialName("copy_writing")
    val copyWriting: String,
    @SerialName("copy_writing_v2")
    val copyWritingV2: String,
    @SerialName("effective_time")
    val effectiveTime: Int,
    @SerialName("face")
    val face: String,
    @SerialName("highlight_color")
    val highlightColor: String,
    @SerialName("icon_list")
    val iconList: List<Int>,
    @SerialName("id")
    val id: Int,
    @SerialName("identities")
    val identities: Int,
    @SerialName("max_delay_time")
    val maxDelayTime: Int,
    @SerialName("mock_effect")
    val mockEffect: Int,
    @SerialName("priority")
    val priority: Int,
    @SerialName("privilege_type")
    val privilegeType: Int,
    @SerialName("show_avatar")
    val showAvatar: Int,
    @SerialName("target_id")
    val targetId: Int,
    @SerialName("trigger_time")
    val triggerTime: Long,
    @SerialName("uid")
    val uid: Long,
    @SerialName("web_basemap_url")
    val webBasemapUrl: String,
    @SerialName("web_close_time")
    val webCloseTime: Int,
    @SerialName("web_effect_close")
    val webEffectClose: Int,
    @SerialName("web_effective_time")
    val webEffectiveTime: Int
)