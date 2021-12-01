package bot.tx.wsure.top.config

import bot.tx.wsure.top.utils.FileUtils.readResourceJson
import bot.tx.wsure.top.utils.JsonUtils
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*


object Global {
    val CONFIG by lazy { "config.json".readResourceJson<Config.ConfigData>()!! }
    val httpClient:HttpClient = HttpClient(CIO){
        install(JsonFeature){
            acceptContentTypes = acceptContentTypes + ContentType("text","plain")
            serializer = KotlinxSerializer(JsonUtils.formatter)
        }
    }
}
