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
    val ybbTranConfig: List<YbbTranConfig>,
    @SerialName("jobConfig")
    val jobConfig : Map<String,Map<String,String>>,
    @SerialName("weiboConfig")
    val weiboConfig : List<WeiboChatConfig>,
){
    fun toScConfig(): Map<String, List<SuperChatConfig>> {
        return superChatConfig.groupBy { it.roomId }
    }

    fun toWbConfig(): Map<String, List<WeiboChatConfig>> {
        return weiboConfig.groupBy { it.uid }
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
data class WeiboChatConfig(
    @SerialName("channelId")
    val channelId: Long,
    @SerialName("guildId")
    val guildId: Long,
    @SerialName("uid")
    val uid: String
)

@Serializable
data class YbbTranConfig(
    @SerialName("channelId")
    val channelId: Long,
    @SerialName("guildId")
    val guildId: Long
)