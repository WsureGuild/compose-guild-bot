package bot.tx.wsure.top.utils

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.memberProperties

object JsonUtils {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @OptIn(ExperimentalSerializationApi::class)
    val formatter = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        explicitNulls = false
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T : Any> T.objectToJson(): String {
        return formatter.encodeToString(this)
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> String.jsonToObject(): T {
        return formatter.decodeFromString(this)
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> String.jsonToObjectOrNull(failureReason:Boolean = true): T? {
        return kotlin.runCatching { formatter.decodeFromString<T>(this) }
            .onFailure {
                if(failureReason) {
                    logger.warn("format string to json failed !! string :{}", this, it)
                }
            }.getOrNull()
    }

    fun String.toJsonElement(): JsonElement {
        return formatter.parseToJsonElement(this)
    }

    inline fun <reified T> JsonElement.jsonToObject(): T {
        return formatter.decodeFromJsonElement(this)
    }

    inline fun <reified T> JsonElement.jsonToObjectOrNull(failureReason:Boolean = true): T? {
        return kotlin.runCatching { formatter.decodeFromJsonElement<T>(this) }
            .onFailure {
                if(failureReason) {
                    logger.warn("format string to json failed !! string :{}", this, it)
                }
            }.getOrNull()
    }

    inline fun <reified T : Any> T.toMap():Map<String,String>{
        val props = T::class.memberProperties.associateBy { it.name }
        return props.keys.associateWith { props[it]?.get(this).toString() }
    }
}