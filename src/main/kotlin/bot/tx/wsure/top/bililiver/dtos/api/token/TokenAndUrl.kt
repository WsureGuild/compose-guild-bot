package bot.tx.wsure.top.bililiver.dtos.api.token
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class TokenAndUrl(
    @SerialName("business_id")
    val businessId: Int,
    @SerialName("group")
    val group: String,
    @SerialName("host_list")
    val hostList: List<Host>,
    @SerialName("max_delay")
    val maxDelay: Int,
    @SerialName("refresh_rate")
    val refreshRate: Int,
    @SerialName("refresh_row_factor")
    val refreshRowFactor: Double,
    @SerialName("token")
    val token: String
)

@Serializable
data class Host(
    @SerialName("host")
    val host: String,
    @SerialName("port")
    val port: Int,
    @SerialName("ws_port")
    val wsPort: Int,
    @SerialName("wss_port")
    val wssPort: Int
){
    fun toWssUrl():String{
        return "ws://$host:$wsPort/sub"
    }
}