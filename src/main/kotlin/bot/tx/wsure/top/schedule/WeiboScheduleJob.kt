package bot.tx.wsure.top.schedule

import bot.tx.wsure.top.config.ChannelConfig
import bot.tx.wsure.top.spider.dtos.weibo.Mblog
import bot.tx.wsure.top.unofficial.UnOfficialBotClient
import bot.tx.wsure.top.unofficial.dtos.api.BaseAction
import bot.tx.wsure.top.unofficial.dtos.api.SendGuildChannelMsgAction
import bot.tx.wsure.top.unofficial.enums.ActionEnums
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
import bot.tx.wsure.top.utils.MapDBManager
import bot.tx.wsure.top.utils.WeiBoUtils
import bot.tx.wsure.top.utils.WeiBoUtils.toUnofficialMessageText
import kotlinx.coroutines.delay
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object WeiboScheduleJob: BaseCronJob("WeiboScheduleJob","0 0/5 * * * ?"){
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    override suspend fun execute(params: Map<String, String>, sender: UnOfficialBotClient?) {
        logger.info("${this.name} - params：${params.objectToJson()}")
        params["cookie"]?.also { cookie ->
            logger.info("${this.name} - read cookie success")
            val wbConfig = MapDBManager.WB_CONFIG.cache
            logger.info("${this.name} - read wbConfig :${wbConfig.entries.joinToString { "${it.key}:${it.value}" }}")
            wbConfig.entries.onEach { entry ->
                if( entry.key == null || entry.value == null) return@onEach

                logger.info("${this.name} - start load :${entry.key}")
                val wbList = WeiBoUtils.getMLogByUid2(entry.key!!,cookie)
                if(wbList.isNotEmpty()){
                    val topList = wbList.filter { it.isTop != null && it.isTop == 1 }
                    val oldTopList =  MapDBManager.WB_TOP[entry.key!!, { mutableListOf() }].value
                    val newTopList = topList.filter { ! oldTopList.map { o -> o.id }.contains(it.id) }
                    if(newTopList.isNotEmpty()){
                        MapDBManager.WB_TOP[entry.key!!] = newTopList
                        sender?.also { sender ->
                            entry.value?.sendMblogMessage(sender,newTopList)
                        }
                    }
                    val oldList = MapDBManager.WB_CACHE[entry.key!!, { mutableListOf() }].value
                    val newList = addedWebList(oldList,wbList.filter { it.isTop == null || it.isTop != 1 })
                    //save
                    MapDBManager.WB_CACHE[entry.key!!] = mergeWebList(oldList , newList)
                    //send
                    if(oldList.isEmpty()){
                        // if oldList is null,it maybe init
                        return@onEach
                    }
                    sender?.also { sender ->
                        entry.value?.sendMblogMessage(sender,newList)
                    }
                }
                delay(3000)
            }
        }
    }

    fun List<ChannelConfig>.sendMblogMessage(sender: UnOfficialBotClient, mblogs: List<Mblog>){
        this.forEach { guild ->
            val msg = mblogs.joinToString("\n") { mblog ->
                BaseAction(ActionEnums.SEND_GUILD_CHANNEL_MSG,
                    SendGuildChannelMsgAction(guild.guildId,guild.channelId,mblog.toUnofficialMessageText())
                ).objectToJson()
            }
            sender.sendMessage(msg)
        }
    }

    fun mergeWebList(oldWbList: List<Mblog>, newWbList:List<Mblog>, cachedSize :Int = 30 ):List<Mblog>{
        val mergeList = oldWbList + newWbList
        return mergeList.sortedBy { it.id }.reversed().take(cachedSize)
    }
    fun addedWebList(oldWbList: List<Mblog>, newWbList:List<Mblog>):List<Mblog>{
        return newWbList.filter { nwb -> ! oldWbList.map { it.id}.contains(nwb.id ) }
    }



}

