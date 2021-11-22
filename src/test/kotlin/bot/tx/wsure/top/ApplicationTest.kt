package bot.tx.wsure.top

import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.brotli
import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.toChatPackage
import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.toChatPackageList
import bot.tx.wsure.top.bililiver.BiliLiverConsole
import bot.tx.wsure.top.bililiver.dtos.event.ChatCmdBody
import bot.tx.wsure.top.bililiver.dtos.event.CmdType
import bot.tx.wsure.top.bililiver.dtos.event.cmd.SuperChatMessage
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import bot.tx.wsure.top.plugins.*
import bot.tx.wsure.top.utils.JsonUtils.jsonToObject
import bot.tx.wsure.top.utils.WeiBoUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okio.ByteString.Companion.decodeHex

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
    fun testPage(){
        val hex = "000001b50010000300000005000000001bcb04202c0fb873470aa8b9b65278698d5cf1082264b9bbfd16c971248956943fb507b3bafaf516ea43ac8211509846b2bdfc7c283fd8d6525f2e2b1e8fa05f74ab81a9930d82af62cabba5b45be8e49093540715517d1938a76e36b38397f971110df44a8a16ece66315389f700ab23b1cb9885cc01600862ddeebcdd802ad1cfd0b71d5401de3f8e1a8045a0883af84fc07e60e4dd8401c7478d86eea6447e0c8b32272a58bcecec067057276950247c160ad02cdd5ceee63620162cde2ec5bc43baeb74e4f19de7e6dab828ef2cb6000b5d044a7492c8642fd0bdc853a39d533d0cbc5f45505268702c00ad45985fc2a4e35ff816b3cce748e4f05bffd8a8f69b000d3bde7a888f53709d882b36edbf7616a676fdec82f503feb348ab59a2c868e6264b7b83dc28b196ac68037aca23b971518a758aba82ff9423c8ecb99016f1463355ad62438245922cc9032ae4b5a8ea97f8f192b21879a67811180f5912d14d9aa17f749ba11ce3ead6e6dc8f22d1d19b67658d0c2f955187d8ae571df505e5a44a8e6f07fd16beddded7bc55d13df312597a22e38c0cbf14ecdcacfd1df329201"
        val pkg = hex.decodeHex().toChatPackage()
        println("pkg.length : ${pkg.packetLength} ,body.size : ${pkg.body.size} ,\n${pkg.body.brotli().toChatPackageList().joinToString("\n") { it.content() }}")
    }
    @Test
    fun testOkhttp(){
        mutableSetOf( "14475263","14343955","22582590","23208247","725364","23612283","23256987").map {
            BiliLiverConsole(it)
        }

        runBlocking { delay(99999999999999) }
//        OfficialBotApi.delRoles(Global.CONFIG.devGuild.id,"15112013223705719381","10014689")
    }

    @Test
    fun testEvents(){
        val type :ChatCmdBody<SuperChatMessage> = "{\"cmd\":\"SUPER_CHAT_MESSAGE\",\"data\":{\"background_bottom_color\":\"#2A60B2\",\"background_color\":\"#EDF5FF\",\"background_color_end\":\"#405D85\",\"background_color_start\":\"#3171D2\",\"background_icon\":\"\",\"background_image\":\"https://i0.hdslb.com/bfs/live/a712efa5c6ebc67bafbe8352d3e74b820a00c13e.png\",\"background_price_color\":\"#7497CD\",\"color_point\":0.7,\"dmscore\":80,\"end_time\":1637586602,\"gift\":{\"gift_id\":12000,\"gift_name\":\"醒目留言\",\"num\":1},\"id\":2677557,\"is_ranked\":1,\"is_send_audit\":0,\"medal_info\":null,\"message\":\"白菜什么时候也能读评论啊\",\"message_font_color\":\"#A3F6FF\",\"message_trans\":\"\",\"price\":30,\"rate\":1000,\"start_time\":1637586542,\"time\":59,\"token\":\"F093618C\",\"trans_mark\":0,\"ts\":1637586543,\"uid\":8434308,\"user_info\":{\"face\":\"http://i0.hdslb.com/bfs/face/875b52c14bfeaceec7292cf64583947314cdaff7.gif\",\"face_frame\":\"\",\"guard_level\":0,\"is_main_vip\":1,\"is_svip\":0,\"is_vip\":0,\"level_color\":\"#5896de\",\"manager\":0,\"name_color\":\"#666666\",\"title\":\"task-year\",\"uname\":\"风行丶星云\",\"user_level\":30}},\"roomid\":21402309}"
            .jsonToObject()
        println(type.cmd)
//        BiliLiverConsole("21452505")
//        runBlocking { delay(99999999999999) }
    }


}