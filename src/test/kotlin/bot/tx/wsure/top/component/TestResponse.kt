package bot.tx.wsure.top.component

import bot.tx.wsure.top.unofficial.UnofficialMessageSender
import bot.tx.wsure.top.unofficial.dtos.api.SendGroupMsg
import bot.tx.wsure.top.unofficial.dtos.api.SendGuildChannelMsg
import bot.tx.wsure.top.unofficial.dtos.event.message.GroupMessage
import bot.tx.wsure.top.unofficial.dtos.event.message.GuildMessage
import bot.tx.wsure.top.unofficial.intf.UnOfficialBotEvent
import bot.tx.wsure.top.utils.JsonUtils.objectToJson

class TestResponse :UnOfficialBotEvent() {

    override suspend fun onGroupMessage(sender: UnofficialMessageSender, message: GroupMessage) {
        if(message.sender.userId == 3401442003L){
            val res :SendGroupMsg.Response = sender.sendGroupMsgAsync(SendGroupMsg(435021808L,"test"))
            println(res.objectToJson())
        }
    }

    override suspend fun onGuildMessage(sender: UnofficialMessageSender, message: GuildMessage) {
        if(message.sender.nickname.contains("Wsure")){
            val start = System.currentTimeMillis()
            val res :SendGuildChannelMsg.Response = sender.sendGuildChannelMsgAsync(SendGuildChannelMsg(6000051636714649L,1560174L,"res:${message.message}"))
            println("use ${System.currentTimeMillis() - start}ms res:"+res.objectToJson())
        }
    }
}