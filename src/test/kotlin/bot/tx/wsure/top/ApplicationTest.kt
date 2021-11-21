package bot.tx.wsure.top

import bot.tx.wsure.top.bililiver.api.BiliLiverApi
import bot.tx.wsure.top.bililiver.dtos.event.HeartbeatPackage
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import bot.tx.wsure.top.plugins.*
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
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
        val room = BiliLiverApi.getRealRoomId("510")
        val tokenAndUrl = BiliLiverApi.getTokenAndUrl(room!!.roomid)
        println(tokenAndUrl?.objectToJson())
        println(HeartbeatPackage.encode().toByteString().hex())
//        OfficialBotApi.delRoles(Global.CONFIG.devGuild.id,"15112013223705719381","10014689")
    }
}