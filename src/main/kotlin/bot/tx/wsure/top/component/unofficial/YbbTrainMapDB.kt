package bot.tx.wsure.top.component.unofficial

import bot.tx.wsure.top.component.unofficial.YbbTrain.TopRecord.Companion.addItem
import bot.tx.wsure.top.config.YbbTranConfig
import bot.tx.wsure.top.unofficial.UnofficialMessageSender
import bot.tx.wsure.top.unofficial.dtos.api.toSendGuildChannelMsgAction
import bot.tx.wsure.top.unofficial.dtos.event.message.GuildMessage
import bot.tx.wsure.top.unofficial.intf.UnOfficialBotEvent
import bot.tx.wsure.top.utils.MapDBManager
import bot.tx.wsure.top.utils.MapDBWarp
import bot.tx.wsure.top.utils.TimeUtils.todayString

/*
    YBB小火车
 */
class YbbTrainMapDB(val ybbConfig: Map<Long, List<YbbTranConfig>>) : UnOfficialBotEvent() {

    override suspend fun onGuildMessage(sender: UnofficialMessageSender, message: GuildMessage) {

        val channel = ybbConfig[message.guildId]
        if (channel == null || channel.isEmpty() || channel.map { it.channelId }.contains(message.channelId)) {
            if (message.message == "ybb") {

                val speed = (0..1000L).random()
                if (message.addDailyMute(speed)) {
                    val res =
                        message.toSendGuildChannelMsgAction("${message.sender.nickname},你今天的小火车速度为${speed}km/h,看我把你绑在铁轨上")
                    sender.sendMessage(res)
                } else {
                    val res = message.toSendGuildChannelMsgAction("${message.sender.nickname},你今天已经被创过了,明天再来吧")
                    sender.sendMessage(res)
                }
            }
            if (message.message == "竞速榜") {
                val top10 =
                    MapDBManager.YBB_TOP[todayString(), { mutableMapOf() }].get { it[message.guildId.toString()] }
                        ?: emptyList()
                if (top10.isNotEmpty()) {
                    val res = message.toSendGuildChannelMsgAction("本频道前10位：\n" + top10.joinToString("\n") {
                        "${it.userName}\t${
                            String.format(
                                "%4d",
                                it.speed
                            )
                        }km/h"
                    })
                    sender.sendMessage(res)
                } else {
                    val res = message.toSendGuildChannelMsgAction("今日无人召唤小火车")
                    sender.sendMessage(res)
                }
            }
        }
    }

    fun <K, V> MapDBWarp<K, V>.cleanDailyMuteCache() {
        this.cache.filter { it.key != todayString() }.onEach { this.remove(it.key) }
    }

    fun GuildMessage.addDailyMute(value: Long): Boolean {
        MapDBManager.YBB.cleanDailyMuteCache()
        MapDBManager.YBB_TOP.cleanDailyMuteCache()

        val todayMap = MapDBManager.YBB[todayString(), { mutableMapOf() }]
        val todayTopMap = MapDBManager.YBB_TOP[todayString(), { mutableMapOf() }]

        val guildTopQueue = todayTopMap.get { it[this.guildId.toString()] } ?: mutableListOf()
        /*
        .also {
        todayTopMap[this.guildId.toString()] = it
    }
         */
        val saveKey = "${this.guildId}::${this.userId}"
        return if (todayMap.get { it[saveKey] } == null) {
            todayMap.set { it[saveKey] = value }
            val list = guildTopQueue.addItem(
                YbbTrain.TopRecord(
                    this.sender.nickname,
                    value
                ), comparator = YbbTrain.TopRecord.comparator()
            )
            todayTopMap.set { it[this.guildId.toString()] = list }
            true
        } else {
            false
        }
    }

}