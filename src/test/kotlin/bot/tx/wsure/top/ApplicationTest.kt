package bot.tx.wsure.top

import bot.tx.wsure.top.bililiver.event.HeartbeatPackage
import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.official.intf.OfficialBotApi
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import bot.tx.wsure.top.plugins.*
import bot.tx.wsure.top.utils.WeiBoUtils
import kotlinx.coroutines.runBlocking
import okio.ByteString.Companion.toByteString

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }

    @Test
    fun testJson() = runBlocking {
        val roles =  WeiBoUtils.getMLogByUid(7198559139L)
        println(roles)
    }

    @Test
    fun testRegex(){
        val content = "\u003c@!16689623218838592690\u003e \u003c@!16689623218838592690\u003e  11"
        println(content.replace(Regex("<@!\\d+>"),""))
    }

    @Test
    fun testOkhttp(){

        println(HeartbeatPackage.decode().toByteString().hex())
//        OfficialBotApi.delRoles(Global.CONFIG.devGuild.id,"15112013223705719381","10014689")
    }
}