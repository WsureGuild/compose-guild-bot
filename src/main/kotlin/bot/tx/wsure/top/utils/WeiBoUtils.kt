package bot.tx.wsure.top.utils

import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.spider.dtos.Mblog
import bot.tx.wsure.top.spider.dtos.WeiBo
import bot.tx.wsure.top.utils.HttpUtils.getWithHeaderAndQuery
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
}