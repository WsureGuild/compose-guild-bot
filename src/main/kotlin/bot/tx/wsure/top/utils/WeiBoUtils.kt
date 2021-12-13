package bot.tx.wsure.top.utils

import bot.tx.wsure.top.spider.dtos.Mblog
import bot.tx.wsure.top.spider.dtos.WeiBo
import bot.tx.wsure.top.unofficial.dtos.CQCode.urlToImageCode
import bot.tx.wsure.top.utils.HttpUtils.getWithHeaderAndQuery
import bot.tx.wsure.top.utils.JsonUtils.jsonToObjectOrNull
import bot.tx.wsure.top.utils.WeiBoUtils.toUnofficialMessageText
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object WeiBoUtils {
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    val M_LOG_URL = "https://m.weibo.cn/api/container/getIndex?uid={{uid}}&t=0&luicode=10000011&containerid=107603{{uid}}"
    suspend fun getMLogByUid(uid: Long,cookie:String):List<Mblog>{
        val weiboPage = M_LOG_URL.replace("{{uid}}",uid.toString())
                .getWithHeaderAndQuery<WeiBo>(mutableMapOf(
                    "Cookie" to cookie,
                    "User-Agent" to UA.PC
                ))?.data?.cards?.mapNotNull { it.mblog }
        return weiboPage?: emptyList()
    }

    fun getMLogByUid2(uid: String,cookie: String):List<Mblog>{
        val url = M_LOG_URL.replace("{{uid}}",uid)
        val page = OkHttpUtils.getStr(url, mutableMapOf(
            "Cookie" to cookie,
            "User-Agent" to UA.PC.getValue()
        )).jsonToObjectOrNull<WeiBo>()?.data?.cards?.mapNotNull { it.mblog }
        return page?: emptyList()
    }

    val WBFacePrefix = Regex("<span.+?(?=\\[)")
    val WBFaceSuffix = Regex("(?<=]).+?span>")
    val WBImagePrefix = "https://wx3.sinaimg.cn/large/"
    val WBDetailPrefix = "https://m.weibo.cn/detail/"
    fun Mblog.toUnofficialMessageText():String{
        val user = this.user.screenName
        val time = this.createdAt.format(TimeUtils.DATE_FORMATTER)
        val text = this.text.replace(WBFacePrefix,"").replace(WBFaceSuffix,"")
        val images = this.picIds.joinToString("") { "$WBImagePrefix$it".urlToImageCode() }
        return "${this.user.profileImageUrl.urlToImageCode()}[$user]\n" +
                "[$time by ${this.source}]\n\n" +
                "$text\n" +
                "${if(this.retweetedStatus == null)"" else "<转发：" +this.retweetedStatus.retweetedMblogToUnofficialMessageText()}>\n" +
                "$images\n" +
                "${WBDetailPrefix+this.id} "
    }
    fun Mblog.retweetedMblogToUnofficialMessageText():String{
        val user = this.user.screenName
        val time = this.createdAt.format(TimeUtils.DATE_FORMATTER)
        val text = this.text.replace(WBFacePrefix,"").replace(WBFaceSuffix,"")
        val images = this.picIds.joinToString("") { "$WBImagePrefix$it".urlToImageCode() }
        return "@${user}:${text}\n$images"
    }

}