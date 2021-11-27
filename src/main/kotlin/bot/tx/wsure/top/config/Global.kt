package bot.tx.wsure.top.config

import bot.tx.wsure.top.official.intf.OfficialBotApi
import bot.tx.wsure.top.official.intf.OfficialBotApi.roles
import bot.tx.wsure.top.utils.FileUtils.readResourceJson
import bot.tx.wsure.top.utils.JsonUtils
import bot.tx.wsure.top.utils.ac.AhoCorasickMatcher
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


object Global {
    val CONFIG by lazy { "config.json".readResourceJson<ConfigData>()!! }
    val httpClient:HttpClient = HttpClient(CIO){
        install(JsonFeature){
            acceptContentTypes = acceptContentTypes + ContentType("text","plain")
            serializer = KotlinxSerializer(JsonUtils.formatter)
        }
    }
    val rolesMap by lazy {
        CONFIG.guilds.associate { it.id to OfficialBotApi.getRolesOkhttp(it.botName,it.id) }
    }
    val rolesAhoCorasickMatcherMap = rolesMap.entries.associate { guild -> guild.key to AhoCorasickMatcher(guild.value){ it.name } }

}
@Serializable
data class ConfigData(
    @SerialName("weiboCookie")
    val weiboCookie: String,
    @SerialName("officialBots")
    val officialBots: List<OfficialBot>,
    @SerialName("guilds")
    val guilds:List<DevGuild>,
)

@Serializable
data class DevGuild(
    @SerialName("name")
    val name: String,
    @SerialName("id")
    val id: String,
    @SerialName("channels")
    val channels: MutableMap<String,String>,
    @SerialName("botName")
    val botName:String,
)

@Serializable
data class OfficialBot(
    @SerialName("name")
    val name: String,
    @SerialName("id")
    val id: Long,
    @SerialName("token")
    val token: String,
    @SerialName("guilds")
    val guilds: List<String>,
){
    fun botToken():String{
        return "Bot ${id}.${token}"
    }
}