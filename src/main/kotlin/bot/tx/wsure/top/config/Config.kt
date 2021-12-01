package bot.tx.wsure.top.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Config(
    @SerialName("weiboCookie")
    val weiboCookie: String,
    @SerialName("superChatConfig")
    val superChatConfig: List<SuperChatConfig>,
    @SerialName("ybbTranConfig")
    val ybbTranConfig: List<YbbTranConfig>
){
    fun toScConfig(): Map<String, List<SuperChatConfig>> {
        return superChatConfig.groupBy { it.roomId }
    }

    fun toYbbConfig() : Map<Long, List<YbbTranConfig>>{
        return ybbTranConfig.groupBy { it.guildId }
    }
}

@Serializable
data class SuperChatConfig(
    @SerialName("channelId")
    val channelId: Long,
    @SerialName("guildId")
    val guildId: Long,
    @SerialName("roomId")
    val roomId: String
)

@Serializable
data class YbbTranConfig(
    @SerialName("channelId")
    val channelId: Long,
    @SerialName("guildId")
    val guildId: Long
)