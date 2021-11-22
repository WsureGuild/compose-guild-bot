package bot.tx.wsure.top

import bot.tx.wsure.top.bililiver.BiliLiverConsole
import bot.tx.wsure.top.bililiver.BiliLiverEvent
import bot.tx.wsure.top.bililiver.dtos.api.room.Room
import bot.tx.wsure.top.component.bililiver.SuperChatNotify
import bot.tx.wsure.top.config.Global.token
import bot.tx.wsure.top.official.OfficialBotClient
import bot.tx.wsure.top.component.official.EditRoles
import bot.tx.wsure.top.component.unofficial.YbbTrain
import bot.tx.wsure.top.plugins.*
import bot.tx.wsure.top.unofficial.UnOfficialBotClient
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(InternalAPI::class, InternalCoroutinesApi::class)
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
        configureSockets()

        val scConfig = mutableMapOf<String,List<Pair<Long,Long>>>(
            "21452505" to listOf(6000051636714649 to 1370732), // 七海 七海频道直播讨论子频
            "213" to listOf(6000051636714649 to 1454836), // C酱 七海频道 火车站子频
            "876396" to listOf(6000051636714649 to 1454836), // 忘了是谁了  七海频道直播讨论子频
            "22047448" to listOf(6000051636714649 to 1454836), // 星宮汐Official 七海频道直播讨论子频
            "21402309" to listOf(6000051636714649 to 1454836), // 眞白花音_Official 七海频道直播讨论子频
            "14327465" to listOf(6000051636714649 to 1454836), // 花园Serena 七海频道直播讨论子频
        )

        val unOfficialBotClient = UnOfficialBotClient( listOf(
            YbbTrain(),
//            SendRoles()
        ))
        val useBL = true
        if(useBL){
            scConfig.onEach { room ->
                BiliLiverConsole(room.key){ initBiliLiverEvents(it,unOfficialBotClient,room.value) }
            }

        }

        val botClient = OfficialBotClient(token, listOf(
            EditRoles(unOfficialBotClient)
        ))

        this.attributes.put(AttributeKey("officeBot"), botClient)

    }.start(wait = true)


}
fun initBiliLiverEvents(room: Room,unOfficialBotClient: UnOfficialBotClient,guildAndChannel:List<Pair<Long,Long>>):List<BiliLiverEvent>{
    return mutableListOf(
        SuperChatNotify(guildAndChannel,room,unOfficialBotClient)
    )
}
