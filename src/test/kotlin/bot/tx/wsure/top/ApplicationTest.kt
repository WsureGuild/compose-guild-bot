package bot.tx.wsure.top

import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.brotli
import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.toChatPackage
import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.toChatPackageList
import bot.tx.wsure.top.bililiver.BiliLiverConsole
import bot.tx.wsure.top.config.Global.CACHE_PATH
import bot.tx.wsure.top.utils.EhcacheManager
import bot.tx.wsure.top.utils.FileUtils
import bot.tx.wsure.top.utils.MapDBManager
import bot.tx.wsure.top.utils.WeiBoUtils
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okio.ByteString.Companion.decodeHex
import org.mapdb.DB
import org.mapdb.DBMaker
import kotlin.io.path.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testJson() = runBlocking {
        val roles =  WeiBoUtils.getMLogByUid(7198559139L,"")
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
        val danmuText = "{\"cmd\":\"DANMU_MSG\",\"info\":[[0,1,25,4546550,1637854165951,1830478663,0,\"46095640\",0,0,5,\"#1453BAFF,#4C2263A2,#3353BAFF\",0,\"{}\",\"{}\",{\"mode\":0,\"extra\":\"{\\\"send_from_me\\\":false,\\\"content\\\":\\\"小猪学妹来了？\\\",\\\"mode\\\":0,\\\"font_size\\\":25,\\\"color\\\":4546550,\\\"user_hash\\\":\\\"1175017024\\\",\\\"direction\\\":0,\\\"pk_direction\\\":0}\"}],\"小猪学妹来了？\",[4659548,\"夏眠不知\",0,0,0,10000,1,\"#00D1F1\"],[],[14,0,6406234,\"\\u003e50000\",0],[\"\",\"\"],0,3,null,{\"ts\":1637854165,\"ct\":\"5BC2DE66\"},0,0,null,null,0,105]}"
        val regex = Regex("(?<=],\").+?(?=\",\\[)").findAll(danmuText).joinToString(""){ it.value }
        val uid = Regex("(?<=\",\\[)\\d+").findAll(danmuText).joinToString(""){ it.value }
        val uname = Regex("(?<=\",\\[)\\d+,\".*?(?=\")").findAll(danmuText).joinToString(""){ it.value }
        val color = Regex("(?<=\"color\\\\\":)\\d+(?=,)").findAll(danmuText).joinToString(""){ it.value }
        println(regex)
        println(uid)
        println(uname.replace(Regex("^.*\""),""))
        println(color)
    //        BiliLiverConsole("21452505")
//        runBlocking { delay(99999999999999) }
    }

    @Test
    fun testRetry(){
//        println("欢迎 \\u003c%Nana5mi%\\u003e 进入直播间".replace("欢迎 \\u003c%","").replace("%\\u003e 进入直播间",""))
//        val welcome = Welcome()
//        BiliLiverConsole("10339464",listOf(welcome))
        val dbFile = Path(CACHE_PATH).toFile()
        FileUtils.createFileAndDirectory(dbFile)
        val db: DB = DBMaker.fileDB(dbFile)
            .fileMmapEnable()
            .closeOnJvmShutdown()
            .make()
        val map = DB.HashMapMaker<String, String>(db, "map").createOrOpen()
        map["asdas"] = "adssadas"

        println(map["asdas1"])
    }


    @Test
    fun testEhcache(){
        EhcacheManager.ybb.put("AA", mutableMapOf("1" to 1L))
        println( EhcacheManager.ybb.get("AA").entries.joinToString { it.key + " :  " +it.value })
    }

    @Test
    fun testMapDB(){
//        val t1 = MapDBManager.YBB["111"] ?: mutableMapOf<String,Long>().also {
//            MapDBManager.YBB["111"] = it
//        }
//        t1["AAAA"] = 11L
//        MapDBManager.YBB["111"] = t1
//        MapDBManager.db.commit()

        println(MapDBManager.YBB["111"])
    }
}