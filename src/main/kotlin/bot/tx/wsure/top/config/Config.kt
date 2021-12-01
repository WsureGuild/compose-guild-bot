package bot.tx.wsure.top.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Config {
    @Serializable
    data class ConfigData(
        @SerialName("weiboCookie")
        val weiboCookie: String,
    )
}