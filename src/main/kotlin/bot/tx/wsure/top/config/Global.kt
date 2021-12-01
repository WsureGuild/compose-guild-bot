package bot.tx.wsure.top.config

import bot.tx.wsure.top.utils.JsonUtils
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*


object Global {
    const val CONFIG_PATH = "config/config.json"
    val httpClient:HttpClient = HttpClient(CIO){
        install(JsonFeature){
            acceptContentTypes = acceptContentTypes + ContentType("text","plain")
            serializer = KotlinxSerializer(JsonUtils.formatter)
        }
    }
}
