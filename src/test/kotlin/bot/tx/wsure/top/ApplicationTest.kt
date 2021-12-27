package bot.tx.wsure.top

import bot.tx.wsure.top.component.TestResponse
import bot.tx.wsure.top.config.Global.CACHE_PATH
import bot.tx.wsure.top.schedule.BaseCronJob
import bot.tx.wsure.top.unofficial.UnOfficialBotClient
import bot.tx.wsure.top.utils.EhcacheManager
import bot.tx.wsure.top.utils.FileUtils
import bot.tx.wsure.top.utils.MapDBManager
import bot.tx.wsure.top.utils.TimeUtils.DATE_FORMATTER
import bot.tx.wsure.top.utils.WeiBoUtils
import bot.tx.wsure.top.utils.WeiBoUtils.WBFacePrefix
import bot.tx.wsure.top.utils.WeiBoUtils.WBFaceSuffix
import bot.tx.wsure.top.utils.WeiBoUtils.filterMblogContext
import bot.tx.wsure.top.utils.WeiBoUtils.toUnofficialMessageText
import it.justwrote.kjob.InMem
import it.justwrote.kjob.kjob
import it.justwrote.kjob.kron.Kron
import it.justwrote.kjob.kron.KronModule
import it.skrape.core.htmlDocument
import it.skrape.fetcher.skrape
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okio.ByteString.Companion.decodeHex
import org.mapdb.DB
import org.mapdb.DBMaker
import top.wsure.bililiver.bililiver.BiliLiverChatUtils.brotli
import top.wsure.bililiver.bililiver.BiliLiverChatUtils.toChatPackage
import top.wsure.bililiver.bililiver.BiliLiverChatUtils.toChatPackageList
import top.wsure.bililiver.bililiver.BiliLiverConsole
import top.wsure.bililiver.bililiver.api.BiliLiverApi
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.set
import kotlin.io.path.Path
import kotlin.test.Test


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
        val listOfRTV = listOf(
        "23260993",
        "21672023",
        "8721033",
        "22778596",
        "21484828",
        "21403601",
        "33942",
        "22470208",
        "22605464",
        "21452505",)

        listOfRTV.onEach {
            BiliLiverConsole(it)
        }

        runBlocking { delay(999999999999999999L) }
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
        val t1 = MapDBManager.YBB["111", { mutableMapOf() }]
        t1.set {
            it["1112"] = 1010L
        }
//        MapDBManager.YBB["111"] = t1

        val t2 = t1.get { it["1112"] }
        println(t2)
    }


    @Test
    fun testJob() = runBlocking {
        val kjob = kjob(InMem) {
            extension(KronModule)
        }.start()

        BaseCronJob::class.sealedSubclasses.mapNotNull { it.objectInstance }.onEach { job ->
            kjob(Kron).kron(job) {
                maxRetries = 3
                execute {
                    job.execute()
                    println("${job.name} ${LocalDateTime.now().format(DATE_FORMATTER)}")
                }
            }
        }

//
//
//
//        kjob(Kron).kron(WeiboScheduleJob) {
//            maxRetries = 3
//            execute {
//                it.execute()
//                println("kjob.schedule1 ${LocalDateTime.now().format(DATE_FORMATTER)}")
//            }
//        }
        delay(30000)
        kjob.shutdown()
    }

    @Test
    fun testPackage(){
        val text = "困啊<span class=\"url-icon\"><img alt=[困] src=\"https://h5.sinaimg.cn/m/emoticon/icon/default/d_kun-0f87c3e1f8.png\" style=\"width:1em; height:1em;\" /></span>录音<span class=\"url-icon\"><img alt=[困] src=\"https://h5.sinaimg.cn/m/emoticon/icon/default/d_kun-0f87c3e1f8.png\" style=\"width:1em; height:1em;\" /></span> "
        println( text.replace(WeiBoUtils.WBFacePrefix,"").replace(WeiBoUtils.WBFaceSuffix,""))
    }

    @Test
    fun testRegex2(){
        val text = "2021-12-25 02:47:33.057 [OkHttp https://hw-gz-live-comet-04.chat.bilibili.com/...] INFO  bot.tx.wsure.top.unofficial.UnOfficialBotClient - send text message {\"action\":\"send_guild_channel_msg\",\"params\":{\"guild_id\":6000051636714649,\"channel_id\":1370732,\"message\":\" - \$ \$ \$ - \\n「`七海Nana7mi`收到了`-エクシア-`发送了30块 SC:`海海圣诞快乐！去听了你的圣诞音声，海海好可爱我好喜欢！谢谢你能在圣诞节来陪我们！`」\"},\"echo\":\"5745a020-9c86-4d71-802d-ecf7e4190e13\"}"
        val regex1 = Regex("(?<=:\\d{2}).+?(?=「)")
        val regex2 = Regex("(?<=」).+?$")
        println(text.replace(regex1,"").replace(regex2,""))
    }

    @Test
    fun testWeibo(){
        val text = "派友JeremyMcFake：<br />从第0赛季起，<br />我就在这了，<br />玩了2000小时但还是玩成这样<br /><a  href=\\\"https://m.weibo.cn/search?containerid=231522type%3D1%26t%3D10%26q%3D%23Apex%E7%AC%AC11%E8%B5%9B%E5%AD%A3%23&extparam=%23Apex%E7%AC%AC11%E8%B5%9B%E5%AD%A3%23&luicode=10000011&lfid=1076037198559139\\\" data-hide=\\\"\\\"><span class=\\\"surl-text\\\">#Apex第11赛季#</span></a><a  href=\\\"https://m.weibo.cn/search?containerid=231522type%3D1%26t%3D10%26q%3D%23Apex%E8%8B%B1%E9%9B%84%23&extparam=%23Apex%E8%8B%B1%E9%9B%84%23&luicode=10000011&lfid=1076037198559139\\\" data-hide=\\\"\\\"><span class=\\\"surl-text\\\">#Apex英雄#</span></a> <br />大部分表示，抓钩那里，血压真的上来了<span class=\\\"url-icon\\\"><img alt=[二哈] src=\\\"https://h5.sinaimg.cn/m/emoticon/icon/others/d_erha-139d0e07bd.png\\\" style=\\\"width:1em; height:1em;\\\" /></span><br /><a  href=\\\"https://m.weibo.cn/p/index?extparam=APEX%E8%8B%B1%E9%9B%84&containerid=1008089eedf76d192882bdc668060ccd90621e&luicode=10000011&lfid=1076037198559139\\\" data-hide=\\\"\\\"><span class='url-icon'><img style='width: 1rem;height: 1rem' src='https://n.sinaimg.cn/photo/5213b46e/20180926/timeline_card_small_super_default.png'></span><span class=\\\"surl-text\\\">APEX英雄</span></a> <a data-url=\\\"http://t.cn/A6x8TN4I\\\" href=\\\"https://video.weibo.com/show?fid=1034:4714243665362961\\\" data-hide=\\\"\\\"><span class='url-icon'><img style='width: 1rem;height: 1rem' src='https://h5.sinaimg.cn/upload/2015/09/25/3/timeline_card_small_video_default.png'></span><span class=\\\"surl-text\\\">APEX英雄的微博视频</span></a> "
        val res = text.replace(Regex("\\<br\\s*\\/>"),"\n")
            .replace("</span></a>","")
//            .replace(Regex("\\<a.+?>#"),"#").replace(Regex("#\\</.*?a>"),"# ")
            .replace(WBFacePrefix,"").replace(WBFaceSuffix,"")
        println(res)
        println(text.filterMblogContext())
        val cookie = ""
        println(" ------------------------- -")
        val uidList = listOf("7198559139","2203177060","6377117491","2085108062")
        uidList.onEach { uid ->
            val wbList = WeiBoUtils.getMLogByUid2(uid,cookie)
            wbList.onEach { mblog ->

                println(mblog.toUnofficialMessageText())
                println(" ------------------------- -")
            }
        }
    }

    @Test
    fun testSpace(){
        listOf("434334701","27092536","777656","2123").forEach {
            val space = BiliLiverApi.getSpace(it)
            println(space?.liveRoom?.liveStatus?:0)
        }

    }

    @Test
    fun testTimestamp(){
        val triggerTime = LocalDateTime.ofInstant(
            Instant.ofEpochSecond(1639365571L),
            TimeZone.getDefault().toZoneId()
        )
        println(triggerTime.format(DATE_FORMATTER))
    }

    @Test
    fun testGocq(){
        val client = UnOfficialBotClient(listOf(TestResponse()))

        runBlocking { delay(999999999999) }
    }

    @Test
    fun getSCLogger(){
        val regex1 = Regex("(?<=:\\d{2}).+?(?=「)")
        val regex2 = Regex("(?<=」).+?$")
        val f1 = Path("logs/log_info.log").toAbsolutePath().toFile()
        val f2 = Path("logs/log_info_sc.txt").toAbsolutePath().toFile()
        f2.bufferedWriter().use { output ->
            f1.readLines().forEach {
                val he = "- \$ \$ \$ - \\n「`七海Nana7mi`收到了`"
                if (it.contains(he)) {
                    output.write(it.replace(regex1,"").replace(regex2,""))
                    output.newLine()
                    output.flush()
                }
            }
        }
    }
}