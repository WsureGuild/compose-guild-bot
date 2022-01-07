package bot.tx.wsure.top.config

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import top.wsure.guild.common.utils.JsonUtils


object Global {
    const val CONFIG_PATH = "config/config.json"
    const val CACHE_PATH = "cache/file.db"
    val httpClient:HttpClient = HttpClient(CIO){
        install(JsonFeature){
            acceptContentTypes = acceptContentTypes + ContentType("text","plain")
            serializer = KotlinxSerializer(JsonUtils.formatter)
        }
    }
}
