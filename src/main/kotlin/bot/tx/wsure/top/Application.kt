package bot.tx.wsure.top

import bot.tx.wsure.top.config.Global.token
import bot.tx.wsure.top.official.OfficialBotClient
import bot.tx.wsure.top.component.official.EditRoles
import bot.tx.wsure.top.component.unofficial.SendRoles
import bot.tx.wsure.top.component.unofficial.YbbTrain
import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.official.intf.OfficialBotApi
import bot.tx.wsure.top.plugins.*
import bot.tx.wsure.top.unofficial.UnOfficialBotClient
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(InternalAPI::class, InternalCoroutinesApi::class)
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
        configureSockets()
        val unOfficialBotClient = UnOfficialBotClient( listOf(
            YbbTrain(),
//            SendRoles()
        ))
        val botClient = OfficialBotClient(token, listOf(
            EditRoles(unOfficialBotClient)
        ))

        this.attributes.put(AttributeKey("officeBot"), botClient)

    }.start(wait = true)


}
