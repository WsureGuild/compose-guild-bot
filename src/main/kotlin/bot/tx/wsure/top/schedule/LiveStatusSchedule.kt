package bot.tx.wsure.top.schedule

import bot.tx.wsure.top.cache.C4KManager
import bot.tx.wsure.top.config.BotTypeEnum
import bot.tx.wsure.top.config.ChannelConfig
import bot.tx.wsure.top.config.Global
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.wsure.bililiver.bililiver.api.BiliLiverApi
import top.wsure.bililiver.bililiver.dtos.api.room.RoomInfo
import top.wsure.guild.common.utils.JsonUtils.objectToJson
import top.wsure.guild.common.utils.TimeUtils.DATE_FORMATTER
import top.wsure.guild.unofficial.dtos.CQCode.urlToImageCode
import top.wsure.guild.unofficial.dtos.api.SendGuildChannelMsg
import top.wsure.guild.unofficial.intf.UnofficialApi

object LiveStatusSchedule : BaseCronJob("LiveStatusSchedule", "0 0/1 * * * ?") {
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    override suspend fun execute(params: Map<String, String>) {
        val blConfig = C4KManager.BL_CONFIG.asMap()
        logger.info("${this.name} - read BiliLiverConfig :${blConfig.entries.joinToString { "${it.key}:${it.value}" }}")
        blConfig.entries.onEach { entry ->
            if (entry.key == null) return@onEach
            val liveRoom = BiliLiverApi.getRoomInfo(entry.key.toString()) ?: return@onEach
            val liveStatus = liveRoom.liveStatus
            if(liveStatus == 1) {
                logger.info("${liveRoom.roomId} - is living !!!")
                val cache = C4KManager.BL_ROOM_CACHE.get(entry.key.toString())
                if (cache != null && cache.liveStatus != 1 && jobStartTime.plusMinutes(-5).isBefore(liveRoom.liveTime)){
                    logger.info("${liveRoom.roomId} - need notify !")
                    entry.value.forEach {
                        when(it.type) {
                            BotTypeEnum.OFFICIAL -> {
                                logger.info("${liveRoom.roomId} - OFFICIAL //todo")
//                                val sender = Global.botSenderMap[BotTypeEnum.OFFICIAL] as OfficialBotApi
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
                            " jobStartTime:${jobStartTime}," +
                            " liveRoom.liveTime:${liveRoom.liveTime.format(DATE_FORMATTER)}," +
                            "${jobStartTime.plusMinutes(-5).isBefore(liveRoom.liveTime)}")
                }
            }
            C4KManager.BL_ROOM_CACHE.put(entry.key.toString(),liveRoom)
        }
    }

    private fun RoomInfo.toUnofficialGuildMessage(channelConfig: ChannelConfig):SendGuildChannelMsg{
        return SendGuildChannelMsg(channelConfig.guildId,channelConfig.channelId.toLong(),this.toUnofficialMessageText())
    }

    private fun RoomInfo.toUnofficialMessageText():String{
        val room = BiliLiverApi.getRealRoomId(this.roomId.toString())
        return "${room?.uname}正在直播【${this.title}】\nhttps://live.bilibili.com/${this.roomId}\n${this.userCover.urlToImageCode()}"
    }
}