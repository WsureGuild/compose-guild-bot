package bot.tx.wsure.top

import bot.tx.wsure.top.bililiver.BiliLiverConsole
import bot.tx.wsure.top.bililiver.BiliLiverEvent
import bot.tx.wsure.top.bililiver.dtos.api.room.Room
import bot.tx.wsure.top.component.bililiver.SuperChatNotify
import bot.tx.wsure.top.component.official.EditRoles
import bot.tx.wsure.top.component.unofficial.YbbTrain
import bot.tx.wsure.top.config.Global.token
import bot.tx.wsure.top.official.OfficialBotClient
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

        val scConfig = mutableMapOf<String, List<Pair<Long, Long>>>(
            "21452505" to listOf(
                6000051636714649 to 1370732,// 七海 七海频道直播讨论子频
            ),
            "510" to listOf(
                36667731636792997 to 1365148,// 阿梓 阿梓 直播讨论
            ),
            "605" to listOf(
                19995411637241900 to 1542326, // 小可 小可 直播机器人频
            ),
        )

        val unOfficialBotClient = UnOfficialBotClient(
            listOf(
                YbbTrain(),
//            SendRoles()
            )
        )
        val useBL = true
        if (useBL) {
            scConfig.onEach { room ->
                BiliLiverConsole(room.key) { initBiliLiverEvents(it, unOfficialBotClient, room.value) }
            }

        }

        val botClient = OfficialBotClient(
            token, listOf(
                EditRoles(unOfficialBotClient)
            )
        )

        this.attributes.put(AttributeKey("officeBot"), botClient)

    }.start(wait = true)


}

fun initBiliLiverEvents(
    room: Room,
    unOfficialBotClient: UnOfficialBotClient,
    guildAndChannel: List<Pair<Long, Long>>
): List<BiliLiverEvent> {
    return mutableListOf(
        SuperChatNotify(guildAndChannel, room, unOfficialBotClient)
    )
}
