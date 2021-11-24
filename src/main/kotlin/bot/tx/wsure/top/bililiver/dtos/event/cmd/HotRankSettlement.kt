package bot.tx.wsure.top.bililiver.dtos.event.cmd
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class HotRankSettlement(
    @SerialName("area_name")
    val areaName: String,
    @SerialName("cache_key")
    val cacheKey: String,
    @SerialName("dm_msg")
    val dmMsg: String,
    @SerialName("dmscore")
    val dmscore: Int?,
    @SerialName("face")
    val face: String,
    @SerialName("icon")
    val icon: String,
    @SerialName("rank")
    val rank: Int,
    @SerialName("timestamp")
    val timestamp: Int,
    @SerialName("uname")
    val uname: String,
    @SerialName("url")
    val url: String
)