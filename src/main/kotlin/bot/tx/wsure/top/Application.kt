package bot.tx.wsure.top

import bot.tx.wsure.top.bililiver.BiliLiverConsole
import bot.tx.wsure.top.bililiver.BiliLiverEvent
import bot.tx.wsure.top.bililiver.dtos.api.room.Room
import bot.tx.wsure.top.component.bililiver.SuperChatNotify
import bot.tx.wsure.top.component.unofficial.YbbTrain
import bot.tx.wsure.top.config.Config
import bot.tx.wsure.top.config.Global.CONFIG_PATH
import bot.tx.wsure.top.config.SuperChatConfig
import bot.tx.wsure.top.plugins.*
import bot.tx.wsure.top.unofficial.UnOfficialBotClient
import bot.tx.wsure.top.utils.FileUtils
import bot.tx.wsure.top.utils.FileUtils.readFileJson
import bot.tx.wsure.top.utils.FileUtils.readResourceJson
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(InternalAPI::class, InternalCoroutinesApi::class)
fun main() {
    FileUtils.copyResource(CONFIG_PATH)
    bootBot()
    /*
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
        configureSockets()



    }.start(wait = true)
     */

}

fun bootBot(){
    val config : Config = initConfig()

    val unOfficialBotClient = UnOfficialBotClient(
        listOf(
            YbbTrain(config.toYbbConfig()),
        )
    )
    val useBL = false
    if (useBL) {
        config.toScConfig().onEach { room ->
            BiliLiverConsole(room.key,mutableListOf(SuperChatNotify(room.value, unOfficialBotClient))
            )
        }

    }
}

fun initConfig(): Config {

    return CONFIG_PATH.readFileJson<Config>()?.also {
        println(" 读取配置成功！ ")
    }?: CONFIG_PATH.readResourceJson<Config>()?.also {
        println(" 读取配置失败，使用默认配置 ")
    }?: throw Exception(" 读取配置失败，无法启动")
}