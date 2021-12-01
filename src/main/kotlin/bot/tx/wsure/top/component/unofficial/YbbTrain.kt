package bot.tx.wsure.top.component.unofficial

import bot.tx.wsure.top.config.YbbTranConfig
import bot.tx.wsure.top.unofficial.UnofficialMessageSender
import bot.tx.wsure.top.unofficial.dtos.api.toSendGuildChannelMsgAction
import bot.tx.wsure.top.unofficial.dtos.event.message.GuildMessage
import bot.tx.wsure.top.unofficial.intf.UnOfficialBotEvent

/*
    YBB小火车
 */
class YbbTrain(val ybbConfig: Map<Long, List<YbbTranConfig>>) : UnOfficialBotEvent() {

    // todo 持久化
    val catchMap:MutableMap<Long,Long> = mutableMapOf()

    override suspend fun onGuildMessage(sender: UnofficialMessageSender, message: GuildMessage) {
        val channel = ybbConfig[ message.guildId]
        if(channel == null || channel.isEmpty() || channel.map { it.channelId }.contains(message.channelId)){
            if(message.message == "ybb"){
                if(catchMap[message.sender.userId] ==null){
                    val speed = (0 .. 1000L).random()
                    catchMap[message.sender.userId] = speed
                    val res = message.toSendGuildChannelMsgAction("${message.sender.nickname},你今天的小火车速度为${speed}km/h,看我把你绑在铁轨上")
                    sender.sendMessage(res)
                } else {
                    val res = message.toSendGuildChannelMsgAction("${message.sender.nickname},你今天已经被创过了,明天再来吧")
                    sender.sendMessage(res)
                }
            }
        }
    }
}