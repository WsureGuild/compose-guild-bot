package bot.tx.wsure.top

import bot.tx.wsure.top.cache.MapDBManager
import bot.tx.wsure.top.component.bililiver.SuperChatNotify
import bot.tx.wsure.top.component.official.EditRoles
import bot.tx.wsure.top.component.unofficial.YbbTrainMapDB
import bot.tx.wsure.top.config.BotTypeEnum
import bot.tx.wsure.top.config.Config
import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.config.Global.CONFIG_PATH
import bot.tx.wsure.top.schedule.JobManager
import bot.tx.wsure.top.utils.FileUtils
import bot.tx.wsure.top.utils.FileUtils.readFileJson
import bot.tx.wsure.top.utils.FileUtils.readResourceJson
import io.ktor.util.*
import kotlinx.coroutines.InternalCoroutinesApi
import top.wsure.bililiver.bililiver.BiliLiverConsole
import top.wsure.guild.official.OfficialClient
import top.wsure.guild.official.dtos.operation.IdentifyConfig
import top.wsure.guild.official.intf.OfficialBotApi
import top.wsure.guild.unofficial.UnOfficialClient
import top.wsure.guild.unofficial.intf.UnofficialApi

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
    initCacheConfig(config)

    // init gocqhttp bot listener
    val unofficialListeners = listOf(
        // todo
        YbbTrainMapDB(config.toYbbConfig()),
    )
    Global.botSenderMap[BotTypeEnum.UNOFFICIAL] = UnofficialApi()
    // init gocqhttp bot client
    val unOfficialBotClient = UnOfficialClient(unofficialListeners)

    // boot official
    if(config.officialConfig != null){
        val identifyConfig = IdentifyConfig(config.officialConfig.botId,config.officialConfig.token)
        Global.botSenderMap[BotTypeEnum.OFFICIAL] = OfficialBotApi(identifyConfig.getBotToken())
        OfficialClient(identifyConfig, listOf(EditRoles()))
    }
    // todo
    val useBL = true

    val scConfig = config.toScConfig()
    if (useBL) {
        scConfig.onEach { room ->
            BiliLiverConsole(room.key, mutableListOf(
                SuperChatNotify(room.value, unOfficialBotClient)
            ))
        }

    }

    JobManager(config.jobConfig).start()
}

fun initConfig(): Config {

    return CONFIG_PATH.readFileJson<Config>()?.also {
        println(" 读取配置成功！ ")
    }?: CONFIG_PATH.readResourceJson<Config>()?.also {
        println(" 读取配置失败，使用默认配置 ")
    }?: throw Exception(" 读取配置失败，无法启动")
}

fun initCacheConfig(config: Config){
    // init wb_config
    MapDBManager.WB_CONFIG.cache.clear()
    config.toWbConfig().onEach {
        MapDBManager.WB_CONFIG[it.key] = it.value
    }
    // init bl_config
    MapDBManager.BL_CONFIG.cache.clear()
    config.toBLConfig().onEach {
        MapDBManager.BL_CONFIG[it.key] = it.value
    }
}