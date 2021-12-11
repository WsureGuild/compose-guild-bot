package bot.tx.wsure.top.component.unofficial

import bot.tx.wsure.top.component.unofficial.YbbTrain.TopRecord.Companion.addItem
import bot.tx.wsure.top.config.YbbTranConfig
import bot.tx.wsure.top.unofficial.UnofficialMessageSender
import bot.tx.wsure.top.unofficial.dtos.api.toSendGuildChannelMsgAction
import bot.tx.wsure.top.unofficial.dtos.event.message.GuildMessage
import bot.tx.wsure.top.unofficial.intf.UnOfficialBotEvent
import bot.tx.wsure.top.utils.EhcacheManager
import bot.tx.wsure.top.utils.TimeUtils.todayString
import kotlinx.serialization.Serializable
import org.ehcache.Cache

/*
    YBB小火车
 */
class YbbTrain(val ybbConfig: Map<Long, List<YbbTranConfig>>) : UnOfficialBotEvent() {

    override suspend fun onGuildMessage(sender: UnofficialMessageSender, message: GuildMessage) {

        val channel = ybbConfig[ message.guildId]
        if(channel == null || channel.isEmpty() || channel.map { it.channelId }.contains(message.channelId)){
            if(message.message == "ybb"){

                val speed = (0 .. 1000L).random()
                if(message.addDailyMute(speed)){
                    val res = message.toSendGuildChannelMsgAction("${message.sender.nickname},你今天的小火车速度为${speed}km/h,看我把你绑在铁轨上")
                    sender.sendMessage(res)
                } else {
                    val res = message.toSendGuildChannelMsgAction("${message.sender.nickname},你今天已经被创过了,明天再来吧")
                    sender.sendMessage(res)
                }
            }
            if(message.message == "竞速榜"){
                val top10 = (EhcacheManager.ybbTop.get(todayString()) ?: mutableMapOf())[message.guildId.toString()] ?: emptyList()
                if(top10.isNotEmpty()){
                    val res = message.toSendGuildChannelMsgAction("本频道前10位：\n"+top10.joinToString("\n") { "${it.userName}\t${String.format("%4d",it.speed)}km/h" })
                    sender.sendMessage(res)
                } else {
                    val res = message.toSendGuildChannelMsgAction("今日无人召唤小火车")
                    sender.sendMessage(res)
                }
            }
        }
    }



    fun Cache<String,*>.cleanDailyMuteCache() {
        this.filter { it.key != todayString() }.onEach { this.remove(it.key) }
    }

    fun GuildMessage.addDailyMute(value: Long): Boolean {
        EhcacheManager.ybb.cleanDailyMuteCache()
        EhcacheManager.ybbTop.cleanDailyMuteCache()

        val todayMap = EhcacheManager.ybb.get(todayString()) ?: mutableMapOf<String,Long>().also {
            EhcacheManager.ybb.put(todayString(),it)
        }
        val todayTopMap = EhcacheManager.ybbTop.get(todayString()) ?: mutableMapOf<String, List<TopRecord>>().also {
            EhcacheManager.ybbTop.put(todayString(),it)
        }
        val guildTopQueue = todayTopMap[this.guildId.toString()] ?: mutableListOf<TopRecord>().also {
            todayTopMap[this.guildId.toString()] = it
        }
        val saveKey = "${this.guildId}::${this.userId}"
        return if (todayMap[saveKey] == null) {
            todayMap[saveKey] = value
            todayTopMap[this.guildId.toString()] = guildTopQueue.addItem(TopRecord(this.sender.nickname,value), comparator = TopRecord.comparator())
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