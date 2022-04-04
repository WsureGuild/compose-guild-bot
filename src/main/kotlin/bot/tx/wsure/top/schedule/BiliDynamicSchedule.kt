package bot.tx.wsure.top.schedule

import bot.tx.wsure.top.cache.C4KManager
import bot.tx.wsure.top.config.BotTypeEnum
import bot.tx.wsure.top.config.ChannelConfig
import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.utils.SeleniumUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.wsure.bililiver.bililiver.api.BiliLiverApi
import top.wsure.bililiver.bililiver.dtos.api.dynamic.DynamicCard
import top.wsure.guild.common.utils.JsonUtils.objectToJson
import top.wsure.guild.common.utils.TimeUtils.toEpochSecond
import top.wsure.guild.unofficial.dtos.CQCode.fileToImageCode
import top.wsure.guild.unofficial.dtos.api.SendGuildChannelMsg
import top.wsure.guild.unofficial.intf.UnofficialApi
import java.io.File
import java.time.LocalDateTime

object BiliDynamicSchedule: BaseCronJob("BiliDynamicSchedule","30 0/5 * * * ?"){
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    override suspend fun execute(params: Map<String, String>) {

        WeiboScheduleJob.logger.info("${this.name} - params：${params.objectToJson()}")
        logger.info("${this.name} - params：${params.objectToJson()}")
        val dynamicConfig = C4KManager.BL_DYNAMIC_CONFIG.asMap()
        logger.info("${this.name} - read BiliDynamicConfig :${dynamicConfig.entries.joinToString { "${it.key}:${it.value}" }}")
        dynamicConfig.entries.onEach { config ->
            if(config.key == null) return@onEach
            val dynamicList = BiliLiverApi.getDynamicTopList(config.key.toString())
            if(dynamicList.isNotEmpty()){
                val oldDynamicList = C4KManager.BILI_DYNAMIC_CACHE.get(config.key.toString()) ?: emptyList()
                val addedDynamicList = addedWebList(oldDynamicList,dynamicList)
                val newDynamicList = (addedDynamicList + oldDynamicList).sortedByDescending { it.desc.dynamicId }.takeLast(30)
                //save
                C4KManager.BILI_DYNAMIC_CACHE.put(config.key.toString(),newDynamicList)

                if(addedDynamicList.isNotEmpty()){
                    val imageList = addedDynamicList.mapNotNull { kotlin.runCatching {
                        SeleniumUtils.getBiliDynamicImage(it.desc.dynamicIdStr)
                    }.getOrNull() }
                    config.value.onEach { channel ->
                        imageList.onEach { img ->
                            sendMessage(channel,img)
                        }
                    }
                }

            }
        }
    }

    private fun sendMessage(channel: ChannelConfig, img: File) {
        when(channel.type){
            BotTypeEnum.OFFICIAL ->{

            }
            BotTypeEnum.UNOFFICIAL -> {
                val sender = Global.botSenderMap[BotTypeEnum.UNOFFICIAL] as UnofficialApi
                sender.sendGuildChannelMsg(SendGuildChannelMsg(channel.guildId,channel.channelId.toLong(),img.fileToImageCode()))
            }
        }
    }

    fun addedWebList(oldWbList: List<DynamicCard>, newWbList:List<DynamicCard>):List<DynamicCard>{
        return newWbList.filter {
                nwb -> ! oldWbList.map { it.desc.dynamicId}.contains(nwb.desc.dynamicId )
                && jobStartTime.plusMinutes(-5).isBefore(nwb.desc.timestamp)
        }
    }
}