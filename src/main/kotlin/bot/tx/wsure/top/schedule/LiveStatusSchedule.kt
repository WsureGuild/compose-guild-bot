package bot.tx.wsure.top.schedule

import bot.tx.wsure.top.cache.MapDBManager
import bot.tx.wsure.top.config.BotTypeEnum
import bot.tx.wsure.top.config.ChannelConfig
import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.utils.TimeUtils.toEpochMilli
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.wsure.bililiver.bililiver.api.BiliLiverApi
import top.wsure.bililiver.bililiver.dtos.api.room.RoomInfo
import top.wsure.guild.official.intf.OfficialBotApi
import top.wsure.guild.unofficial.dtos.CQCode.urlToImageCode
import top.wsure.guild.unofficial.dtos.api.SendGuildChannelMsg
import top.wsure.guild.unofficial.intf.UnofficialApi
import java.time.LocalDateTime

object LiveStatusSchedule : BaseCronJob("LiveStatusSchedule", "0 0/1 * * * ?") {
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    override suspend fun execute(params: Map<String, String>) {
        val blConfig = MapDBManager.BL_CONFIG.cache
        logger.info("${this.name} - read BiliLiverConfig :${blConfig.entries.joinToString { "${it.key}:${it.value}" }}")
        blConfig.entries.onEach { entry ->
            if (entry.key == null || entry.value == null) return@onEach
            val liveRoom = BiliLiverApi.getRoomInfo(entry.key!!) ?: return@onEach
            val liveStatus = liveRoom.liveStatus
            if(liveStatus == 1) {
                val cache = MapDBManager.BL_ROOM_CACHE[entry.key!!]?.value
                if (cache != null && cache.liveStatus == 0 && LocalDateTime.now().toEpochMilli() - liveRoom.liveTime.toEpochMilli() < 5* 60 * 1000){
                    entry.value?.forEach {
                        when(it.type) {
                            BotTypeEnum.OFFICIAL -> {
                                val sender = Global.botSenderMap[BotTypeEnum.OFFICIAL] as OfficialBotApi
//                                sender.reply(it.channelId,null,)
//                                client.sender.sendGuildChannelMsgAsync(liveRoom.toUnofficialGuildMessage(it))
                            }
                            BotTypeEnum.UNOFFICIAL -> {
                                val sender = Global.botSenderMap[BotTypeEnum.UNOFFICIAL] as UnofficialApi
                                sender.sendGuildChannelMsg(liveRoom.toUnofficialGuildMessage(it))
                            }
                        }

                    }
                }
            }
            MapDBManager.BL_ROOM_CACHE[entry.key!!] = liveRoom
        }
    }

    fun RoomInfo.toUnofficialGuildMessage(channelConfig: ChannelConfig):SendGuildChannelMsg{
        return SendGuildChannelMsg(channelConfig.guildId.toLong(),channelConfig.channelId.toLong(),this.toUnofficialMessageText())
    }

    fun RoomInfo.toUnofficialMessageText():String{
        val room = BiliLiverApi.getRealRoomId(this.roomId.toString())
        return "${room?.uname}正在直播【${this.title}】\nhttps://live.bilibili.com/${this.roomId}\n${this.userCover.urlToImageCode()}"
    }
}