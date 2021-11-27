package bot.tx.wsure.top.bililiver.dtos.event.cmd
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class HotRoomNotify(
    @SerialName("exit_no_refresh")
    val exitNoRefresh: Int,
    @SerialName("random_delay_req_v2")
    val randomDelayReqV2: List<RandomDelayReqV2>,
    @SerialName("threshold")
    val threshold: Int,
    @SerialName("ttl")
    val ttl: Int
)

@Serializable
data class RandomDelayReqV2(
    @SerialName("delay")
    val delay: Int,
    @SerialName("path")
    val path: String
)