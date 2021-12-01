package bot.tx.wsure.top.bililiver.dtos.event.cmd

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class OnlineRankTop3(
    @SerialName("dmscore")
    val dmscore: Int,
    @SerialName("list")
    val list: List<RankMsg>
)

@Serializable
data class RankMsg(
    @SerialName("msg")
    val msg: String,
    @SerialName("rank")
    val rank: Int
)