package bot.tx.wsure.top.utils

import bot.tx.wsure.top.config.Global
import io.ktor.client.request.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object HttpUtils {
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    suspend inline fun <reified T> String.getJsonObject():T?{
        return kotlin.runCatching { Global.httpClient.get<T>(this) }
            .onFailure {
                logger.warn("Call url {} by get method failure !!",this,it)
            }.getOrNull()
    }

    suspend inline fun <reified T> String.getWithHeaderAndQuery(headerMap:Map<String,Any?>):T?{
        return kotlin.runCatching { Global.httpClient.get<T>(this){
            headerMap.onEach { header(it.key,it.value) }
        } }.onFailure {
                logger.warn("Call url {} by get method failure !!",this,it)
            }.getOrNull()
    }

    suspend inline fun <reified T> String.officialApiGet():T?{
        return kotlin.runCatching { Global.httpClient.get<T>(this){
            header("Authorization",Global.token)
        } }.onFailure {
            logger.warn("Call url {} by get method failure !!",this,it)
        }.getOrNull()
    }

    suspend inline fun <reified T> String.officialApiPut():T?{
        return kotlin.runCatching { Global.httpClient.put<T>(this){
            header("Authorization",Global.token)
        } }.onFailure {
            logger.warn("Call url {} by put method failure !!",this,it)
        }.getOrNull()
    }

    suspend inline fun <reified T> String.officialApiDelete():T?{
        return kotlin.runCatching { Global.httpClient.delete<T>(this){
            header("Authorization",Global.token)
        } }.onFailure {
            logger.warn("Call url {} by delete method failure !!",this,it)
        }.getOrNull()
    }
}
/*
No transformation found: class io.ktor.utils.io.ByteBufferChannel -> class bot.tx.wsure.top.Dtos.RolesApiRes
with response from https://api.sgroup.qq.com/guilds/14543211858034966728/roles:
status: 200 OK
response headers:
Date: Thu, 18 Nov 2021 18:00:32 GMT
, Content-Type: application/json
, Content-Length: 1505
, Connection: keep-alive
, Trpc-Trans-Info: {"oidb_head":"CNC+/tAK","traceparent":"MDAtNDQ4NDczMmU5M2UxZGM4YjUxMDQ1ZmVhNjBiM2YxZGUtNDdmNWVhZmYxZThkNDEwMC0wMQ==","tracestate":"","trpc-env":""}
, X-Content-Type-Options: nosniff
, X-Tps-Trace-Id: 4484732e93e1dc8b51045fea60b3f1de

 */