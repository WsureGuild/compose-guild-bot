package bot.tx.wsure.top.config

import bot.tx.wsure.top.official.intf.OfficialBotApi
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
    val token = "Bot ${CONFIG.officialBot.id}.${CONFIG.officialBot.token}"
    val httpClient:HttpClient = HttpClient(CIO){
        install(JsonFeature){
            acceptContentTypes = acceptContentTypes + ContentType("text","plain")
            serializer = KotlinxSerializer(JsonUtils.formatter)
        }
    }
    val roles by lazy {  OfficialBotApi.getRolesOkhttp(CONFIG.devGuild.id) }
    val rolesAhoCorasickMatcher = AhoCorasickMatcher(roles){ it.name }

}
@Serializable
data class ConfigData(
    @SerialName("devGuild")
    val devGuild: DevGuild,
    @SerialName("officialBot")
    val officialBot: OfficialBot,
    @SerialName("weiboCookie")
    val weiboCookie: String
)

@Serializable
data class DevGuild(
    @SerialName("channels")
    val channels: MutableMap<String,String>,
    @SerialName("id")
    val id: String
)

@Serializable
data class OfficialBot(
    @SerialName("id")
    val id: Int,
    @SerialName("token")
    val token: String
)