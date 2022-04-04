package bot.tx.wsure.top.component.unofficial

import bot.tx.wsure.top.cache.C4KManager
import bot.tx.wsure.top.component.unofficial.YbbTrainMapDB.TopRecord.Companion.addItem
import bot.tx.wsure.top.config.ChannelConfig
import io.github.reactivecircus.cache4k.Cache
import kotlinx.serialization.Serializable
import top.wsure.guild.common.utils.TimeUtils.todayString
import top.wsure.guild.unofficial.dtos.api.toSendGuildChannelMsgAction
import top.wsure.guild.unofficial.dtos.event.message.GuildMessage
import top.wsure.guild.unofficial.intf.UnOfficialBotEvent

/*
    YBB小火车
 */
class YbbTrainMapDB(val ybbConfig: Map<String, List<ChannelConfig>>) : UnOfficialBotEvent() {

    override suspend fun onGuildMessage(message: GuildMessage) {

        val channel = ybbConfig[message.guildId]
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
                    C4KManager.YBB_TOP.get(todayString()) { mutableMapOf() }.getOrDefault(message.guildId,emptyList())
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

    suspend fun GuildMessage.addDailyMute(value: Long): Boolean {
        C4KManager.YBB.cleanDailyMuteCache()
        C4KManager.YBB_TOP.cleanDailyMuteCache()

        val todayMap = C4KManager.YBB.get(todayString()){ mutableMapOf() }
        val todayTopMap = C4KManager.YBB_TOP.get(todayString()){ mutableMapOf() }

        val guildTopQueue = todayTopMap.getOrPut(this.guildId){ mutableListOf()}
        /*
        .also {
        todayTopMap[this.guildId.toString()] = it
    }
         */
        val saveKey = "${this.guildId}::${this.userId}"
        return if (todayMap[saveKey] == null) {
            todayMap[saveKey] = value
            val list = guildTopQueue.addItem(
                TopRecord(
                    this.sender.nickname,
                    value
                ), comparator = TopRecord.comparator()
            )
            todayTopMap[this.guildId] = list
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

inline fun <reified K:Any,reified V:Any> Cache<K,V>.cleanDailyMuteCache() {
    this.asMap().filter { it.key != todayString() }.mapNotNull { it }.onEach { this.invalidate(it.key!! as K) }
}
