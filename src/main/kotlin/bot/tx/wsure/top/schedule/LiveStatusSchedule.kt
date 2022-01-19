package bot.tx.wsure.top.schedule

import bot.tx.wsure.top.cache.MapDBManager
import bot.tx.wsure.top.config.ChannelConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.wsure.bililiver.bililiver.api.BiliLiverApi
import top.wsure.bililiver.bililiver.dtos.api.space.LiveRoom
import top.wsure.guild.unofficial.UnOfficialClient
import top.wsure.guild.unofficial.dtos.CQCode.urlToImageCode
import top.wsure.guild.unofficial.dtos.api.SendGuildChannelMsg

object LiveStatusSchedule : BaseCronJob("LiveStatusSchedule", "0 0/1 * * * ?") {
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    override suspend fun execute(params: Map<String, String>, client: UnOfficialClient?) {
        val blConfig = MapDBManager.BL_CONFIG.cache
        logger.info("${this.name} - read BiliLiverConfig :${blConfig.entries.joinToString { "${it.key}:${it.value}" }}")
        blConfig.entries.onEach { entry ->
            if (entry.key == null || entry.value == null) return@onEach
            val space = BiliLiverApi.getSpace(entry.key!!)
            val liveRoom = space?.liveRoom
            liveRoom?.name = space?.name
            if(liveRoom == null || liveRoom.url.isEmpty()){
                return@onEach
            }
            val liveStatus = liveRoom.liveStatus
            if(liveStatus == 1) {
                val cache = MapDBManager.BL_CACHE[entry.key!!]?.value
                if (cache != null && cache.liveStatus == 0){
                    entry.value?.forEach {
                        client?.sender?.sendGuildChannelMsgAsync(liveRoom.toUnofficialGuildMessage(it))
                    }
                }
            }
            MapDBManager.BL_CACHE[entry.key!!] = liveRoom
        }
    }

    fun LiveRoom.toUnofficialGuildMessage(channelConfig: ChannelConfig):SendGuildChannelMsg{
        return SendGuildChannelMsg(channelConfig.guildId,channelConfig.channelId,this.toUnofficialMessageText())
    }

    fun LiveRoom.toUnofficialMessageText():String{
        return "${this.name}正在直播【${this.title}】\n${this.url}\n${this.cover.urlToImageCode()}"
    }
}