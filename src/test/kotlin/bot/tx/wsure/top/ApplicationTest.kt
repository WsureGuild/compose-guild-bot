package bot.tx.wsure.top

import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.official.intf.OfficialBotApi
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import bot.tx.wsure.top.plugins.*
import bot.tx.wsure.top.utils.WeiBoUtils
import kotlinx.coroutines.runBlocking

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
        val roles = OfficialBotApi.getRolesOkhttp(Global.CONFIG.devGuild.id)
        println(roles)
    }
}