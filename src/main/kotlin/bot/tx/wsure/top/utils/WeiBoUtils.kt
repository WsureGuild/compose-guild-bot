package bot.tx.wsure.top.utils

import bot.tx.wsure.top.spider.dtos.weibo.Mblog
import bot.tx.wsure.top.spider.dtos.weibo.WeiBo
import top.wsure.guild.unofficial.dtos.CQCode.urlToImageCode
import it.skrape.core.htmlDocument
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.wsure.guild.common.utils.JsonUtils.jsonToObjectOrNull
import top.wsure.guild.common.utils.OkHttpUtils
import top.wsure.guild.common.utils.UA

object WeiBoUtils {
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    const val M_LOG_URL = "https://m.weibo.cn/api/container/getIndex?uid={{uid}}&t=0&luicode=10000011&containerid=107603{{uid}}"

    fun getMLogByUid(uid: String, cookie: String):List<Mblog>{
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
        val text = this.text.filterMblogContext()
        val images = this.picIds.joinToString("") { "$WBImagePrefix$it".urlToImageCode() }
        val video = if(this.pageInfo != null && this.pageInfo.type == "video") "${this.pageInfo.pagePic.url.urlToImageCode()}\n视频地址:${this.pageInfo.urlOri}" else ""
        return "${this.user.profileImageUrl.urlToImageCode()}[$user]${if (this.isTop != null && this.isTop == 1)"[顶置↑]" else ""}\n" +
                "[$time by ${this.source}]\n\n" +
                "$text\n" +
                "${if(this.retweetedStatus == null)"" else "<转发：" +this.retweetedStatus.retweetedMblogToUnofficialMessageText() + ">"}\n" +
                "$images$video\n" +
                "${WBDetailPrefix+this.id} "
    }
    fun Mblog.retweetedMblogToUnofficialMessageText():String{
        val user = this.user.screenName
        val time = this.createdAt.format(TimeUtils.DATE_FORMATTER)
        val text = this.text.filterMblogContext()
        val images = this.picIds.joinToString("") { "$WBImagePrefix$it".urlToImageCode() }
        val video = if(this.pageInfo != null && this.pageInfo.type == "video") "${this.pageInfo.pagePic.url.urlToImageCode()}\n${this.pageInfo.urlOri}" else ""
        return "@${user}:${text}\n$images$video"
    }

    fun String.filterMblogContext():String {
        return htmlDocument(
            this
                .replace(Regex("\\<br\\s*\\/>"), "\n")
                .replace(WBFacePrefix, "")
                .replace(WBFaceSuffix, "")
        ).wholeText
    }

}