package bot.tx.wsure.top.component.unofficial

import bot.tx.wsure.top.cache.MapDBManager
import bot.tx.wsure.top.cache.MapDBWarp
import bot.tx.wsure.top.component.unofficial.YbbTrainMapDB.TopRecord.Companion.addItem
import bot.tx.wsure.top.config.ChannelConfig
import bot.tx.wsure.top.utils.TimeUtils.todayString
import kotlinx.serialization.Serializable
import top.wsure.guild.unofficial.dtos.api.toSendGuildChannelMsgAction
import top.wsure.guild.unofficial.dtos.event.message.GuildMessage
import top.wsure.guild.unofficial.intf.UnOfficialBotEvent

/*
    YBB小火车
 */
class YbbTrainMapDB(val ybbConfig: Map<String, List<ChannelConfig>>) : UnOfficialBotEvent() {

    override suspend fun onGuildMessage(message: GuildMessage) {

        val channel = ybbConfig[message.guildId.toString()]
        if (channel == null || channel.isEmpty() || channel.map { it.channelId }.contains(message.channelId.toString())) {
            if (message.message == "ybb") {

                val speed = (0..1000L).random()
                if (message.addDailyMute(speed)) {
                    val res =
                        message.toSendGuildChannelMsgAction("${message.sender.nickname},你今天的小火车速度为${speed}km/h,看我把你绑在铁轨上")
                    sender.sendGuildChannelMsgAsync(res)
                } else {
                    val res = message.toSendGuildChannelMsgAction("${message.sender.nickname},你今天已经被创过了,明天再来吧")
                    sender.sendGuildChannelMsgAsync(res)
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
                    sender.sendGuildChannelMsgAsync(res)
                } else {
                    val res = message.toSendGuildChannelMsgAction("今日无人召唤小火车")
                    sender.sendGuildChannelMsgAsync(res)
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
                TopRecord(
                    this.sender.nickname,
                    value
                ), comparator = TopRecord.comparator()
            )
            todayTopMap.set { it[this.guildId.toString()] = list }
            true
        } else {
            false
        }
    }
    @Serializable
    data class TopRecord(
        val userName:String,
        val speed:Long
    ) {
        companion object{
            fun comparator():Comparator<TopRecord>{
                return Comparator { t1, t2 ->

                    if(t2.speed < t1.speed) -1
                    else if( t2.speed == t1.speed) 0
                    else 1
                }
            }

            fun <T> List<T>.addItem(
                item:T,
                top:Int = 10,
                comparator: Comparator<T>):List<T>{
                val list = this.toMutableList()
                list.add(item)
                return list.sortedWith(comparator).take(top)
            }
        }

    }

}