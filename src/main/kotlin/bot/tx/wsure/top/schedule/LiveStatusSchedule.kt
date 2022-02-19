package bot.tx.wsure.top.schedule

import bot.tx.wsure.top.cache.MapDBManager
import bot.tx.wsure.top.config.BotTypeEnum
import bot.tx.wsure.top.config.ChannelConfig
import bot.tx.wsure.top.config.Global
import top.wsure.guild.common.utils.TimeUtils.toEpochMilli
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.wsure.bililiver.bililiver.api.BiliLiverApi
import top.wsure.bililiver.bililiver.dtos.api.room.RoomInfo
import top.wsure.guild.common.utils.JsonUtils.objectToJson
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
                logger.info("${liveRoom.roomId} - is living !!!")
                val cache = MapDBManager.BL_ROOM_CACHE[entry.key!!]?.value
                val nowTimestamp = LocalDateTime.now().toEpochMilli()
                if (cache != null && cache.liveStatus != 1 && nowTimestamp - liveRoom.liveTime.toEpochMilli() < 5* 60 * 1000){
                    logger.info("${liveRoom.roomId} - need notify !")
                    entry.value?.forEach {
                        when(it.type) {
                            BotTypeEnum.OFFICIAL -> {
                                logger.info("${liveRoom.roomId} - OFFICIAL //todo")
                                val sender = Global.botSenderMap[BotTypeEnum.OFFICIAL] as OfficialBotApi
//                                sender.reply(it.channelId,null,)
//                                client.sender.sendGuildChannelMsgAsync(liveRoom.toUnofficialGuildMessage(it))
                            }
                            BotTypeEnum.UNOFFICIAL -> {
                                logger.info("${liveRoom.roomId} - UNOFFICIAL config:${it.objectToJson()}")
                                val sender = Global.botSenderMap[BotTypeEnum.UNOFFICIAL] as UnofficialApi
                                val res = sender.sendGuildChannelMsg(liveRoom.toUnofficialGuildMessage(it))
                                logger.info("${liveRoom.roomId} - UNOFFICIAL result:${res?.objectToJson()} ")
                            }
                        }

                    }
                } else {
                    logger.debug("${liveRoom.roomId} - don't notify ! because : cache != null :${cache != null}," +
                            " cache.liveStatus == 0 :${cache?.liveStatus == 0}," +
                            " nowTimestamp:${nowTimestamp}," +
                            " liveRoom.liveTime.toEpochMilli():${liveRoom.liveTime.toEpochMilli()}," +
                            "${nowTimestamp - liveRoom.liveTime.toEpochMilli() < 5* 60 * 1000}")
                }
            }
            MapDBManager.BL_ROOM_CACHE[entry.key!!] = liveRoom
        }
    }

    fun RoomInfo.toUnofficialGuildMessage(channelConfig: ChannelConfig):SendGuildChannelMsg{
        return SendGuildChannelMsg(channelConfig.guildId,channelConfig.channelId.toLong(),this.toUnofficialMessageText())
    }

    fun RoomInfo.toUnofficialMessageText():String{
        val room = BiliLiverApi.getRealRoomId(this.roomId.toString())
        return "${room?.uname}正在直播【${this.title}】\nhttps://live.bilibili.com/${this.roomId}\n${this.userCover.urlToImageCode()}"
    }
}