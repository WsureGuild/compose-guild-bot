package bot.tx.wsure.top.unofficial.dtos.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoCQApiRes(
    @SerialName("echo")
    val echo: String,
    @SerialName("retcode")
    val retcode: Int,
    @SerialName("status")
    val status: ApiResStatus
)