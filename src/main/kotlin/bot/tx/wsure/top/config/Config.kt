package bot.tx.wsure.top.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Config(
    @SerialName("officialConfig")
    val officialConfig: OfficialConfig? = null,
    @SerialName("channels")
    val channels : List<ChannelConfig> = emptyList(),
    @SerialName("ybbTranConfig")
    val ybbTranConfig: List<String> = emptyList(),
    @SerialName("superChatConfig")
    val superChatConfig: List<CommConfig> = emptyList(),
    @SerialName("biliDynamicConfig")
    val biliDynamicConfig: List<CommConfig> = emptyList(),
    @SerialName("weiboConfig")
    val weiboConfig : List<CommConfig> = emptyList(),
    @SerialName("bililiverConfig")
    val bililiverConfig : List<CommConfig> = emptyList(),

    @SerialName("jobConfig")
    val jobConfig : Map<String,Map<String,String>> = emptyMap(),
){
    private val channelsMap = channels.associateBy { it.name }
    private fun List<String>.channelNameToConfig():List<ChannelConfig>{
        return channels.filter { this.contains(it.name) }
    }

    fun toChannelConfig(configs:List<CommConfig>):Map<String, List<ChannelConfig>>{
        return configs.filter { it.key!= null }.groupBy { it.key!! }
            .mapValues { e -> e.value.mapNotNull { channelsMap[it.channelName] } }
    }

    fun toYbbConfig() : Map<String, List<ChannelConfig>>{
        return ybbTranConfig.channelNameToConfig().groupBy { it.guildId }
    }
}

@Serializable
data class CommConfig(
    @SerialName("channelName")
    val channelName:String,
    @SerialName("key")
    val key: String?
)

@Serializable
data class ChannelConfig(
    @SerialName("channelId")
    val channelId: String,
    @SerialName("guildId")
    val guildId: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("channelType")
    val type: BotTypeEnum? = BotTypeEnum.UNOFFICIAL
)
@Serializable
enum class BotTypeEnum{
    OFFICIAL,
    UNOFFICIAL
}

@Serializable
data class OfficialConfig(
    val botId: Long,
    val token: String,
)