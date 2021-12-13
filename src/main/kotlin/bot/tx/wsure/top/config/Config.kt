package bot.tx.wsure.top.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Config(
    @SerialName("channels")
    val channels : List<ChannelConfig>,

    @SerialName("ybbTranConfig")
    val ybbTranConfig: List<String>,
    @SerialName("superChatConfig")
    val superChatConfig: List<CommConfig>,
    @SerialName("weiboConfig")
    val weiboConfig : List<CommConfig>,
    @SerialName("bililiverConfig")
    val bililiverConfig : List<CommConfig>,

    @SerialName("jobConfig")
    val jobConfig : Map<String,Map<String,String>>,
){
    val channelsMap = channels.associateBy { it.name }
    fun List<String>.channelNameToConfig():List<ChannelConfig>{
        return channels.filter { this.contains(it.name) }
    }
    fun Map<String,List<CommConfig>>.toChannelConfig():Map<String, List<ChannelConfig>>{
        return this.mapValues { e -> e.value.mapNotNull { channelsMap[it.channelName] } }
    }

    fun toScConfig(): Map<String, List<ChannelConfig>> {
        return superChatConfig.groupBy { it.key }.toChannelConfig()
    }

    fun toWbConfig(): Map<String, List<ChannelConfig>> {
        return weiboConfig.groupBy { it.key }.toChannelConfig()
    }

    fun toBLConfig(): Map<String, List<ChannelConfig>> {
        return bililiverConfig.groupBy { it.key }.toChannelConfig()
    }

    fun toYbbConfig() : Map<Long, List<ChannelConfig>>{
        return ybbTranConfig.channelNameToConfig().groupBy { it.guildId }
    }
}

@Serializable
data class CommConfig(
    @SerialName("channelName")
    val channelName:String,
    @SerialName("key")
    val key: String
)


@Serializable
data class YbbTranConfig(
    @SerialName("channelId")
    val channelId: Long,
    @SerialName("guildId")
    val guildId: Long
)

@Serializable
data class ChannelConfig(
    @SerialName("channelId")
    val channelId: Long,
    @SerialName("guildId")
    val guildId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String? = null,
)