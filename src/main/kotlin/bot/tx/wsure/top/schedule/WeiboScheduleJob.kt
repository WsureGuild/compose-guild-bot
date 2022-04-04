package bot.tx.wsure.top.schedule

import bot.tx.wsure.top.cache.C4KManager
import bot.tx.wsure.top.config.BotTypeEnum
import bot.tx.wsure.top.config.ChannelConfig
import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.spider.dtos.weibo.Mblog
import bot.tx.wsure.top.utils.WeiBoUtils
import bot.tx.wsure.top.utils.WeiBoUtils.toUnofficialMessageText
import kotlinx.coroutines.delay
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.wsure.guild.common.utils.JsonUtils.objectToJson
import top.wsure.guild.unofficial.dtos.api.SendGuildChannelMsg
import top.wsure.guild.unofficial.intf.UnofficialApi

object WeiboScheduleJob: BaseCronJob("WeiboScheduleJob","0 0/5 * * * ?"){
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    override suspend fun execute(params: Map<String, String>) {
        logger.info("${this.name} - params：${params.objectToJson()}")
        params["cookie"]?.also { cookie ->
            logger.info("${this.name} - read cookie success")
            val wbConfig = C4KManager.WB_CONFIG.asMap()
            logger.info("${this.name} - read wbConfig :${wbConfig.entries.joinToString { "${it.key}:${it.value}" }}")
            wbConfig.entries.onEach { entry ->
                if(entry.key == null) return@onEach

                logger.info("${this.name} - start load :${entry.key}")
                val wbList = WeiBoUtils.getMLogByUid(entry.key.toString(),cookie)
                if(wbList.isNotEmpty()){
                    val savedList = C4KManager.WB_CACHE.get(entry.key.toString()){ mutableListOf() }

                    val newList = addedWebList(savedList,wbList)
                    //save
                    C4KManager.WB_CACHE.put(entry.key.toString(), mergeWebList(savedList , newList))

                    entry.value.sendMblogMessage(newList)
                }
                delay(3000)
            }
        }
    }

    fun List<ChannelConfig>.sendMblogMessage(mblogs: List<Mblog>){
        this.forEach { guild ->
            when(guild.type) {
                BotTypeEnum.OFFICIAL -> {
//                                    client.sender.also { sender ->
//                                        entry.value?.sendMblogMessage(sender,newTopList)
//                                    }
                }
                BotTypeEnum.UNOFFICIAL -> {
                    val msg = mblogs.joinToString("\n") { it.toUnofficialMessageText() }
                    logger.info("send to unofficial guild:{}",msg.objectToJson())
                    val sender = Global.botSenderMap[BotTypeEnum.UNOFFICIAL] as UnofficialApi
                    if(msg.isNotBlank()){
                        sender.sendGuildChannelMsg(SendGuildChannelMsg(guild.guildId,guild.channelId.toLong(),msg))
                    }
                }
            }
        }
    }

    private fun mergeWebList(oldWbList: List<Mblog>, newWbList:List<Mblog>, cachedSize :Int = 30 ):List<Mblog>{
        val mergeList = oldWbList + newWbList
        return mergeList.sortedBy { it.id }.reversed().take(cachedSize)
    }
    private fun addedWebList(oldWbList: List<Mblog>, newWbList:List<Mblog>):List<Mblog>{
        return newWbList.filter {
                nwb -> ! oldWbList.map { it.id}.contains(nwb.id)
                && jobStartTime.plusMinutes(-5).isBefore(nwb.createdAt.toLocalDateTime())
        }
    }
}

