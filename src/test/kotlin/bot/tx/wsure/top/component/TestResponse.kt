package bot.tx.wsure.top.component

import top.wsure.guild.unofficial.UnofficialMessageSender
import top.wsure.guild.unofficial.dtos.api.SendGroupMsg
import top.wsure.guild.unofficial.dtos.api.SendGuildChannelMsg
import top.wsure.guild.unofficial.dtos.event.message.GroupMessage
import top.wsure.guild.unofficial.dtos.event.message.GuildMessage
import top.wsure.guild.unofficial.intf.UnOfficialBotEvent
import top.wsure.guild.common.utils.JsonUtils.objectToJson

class TestResponse :UnOfficialBotEvent() {

    override suspend fun onGroupMessage(message: GroupMessage) {
        if(message.sender.userId == 3401442003L){
            val res :SendGroupMsg.Response = sender.sendGroupMsgAsync(SendGroupMsg(435021808L,"test"))
            println(res.objectToJson())
        }
    }

    override suspend fun onGuildMessage(message: GuildMessage) {
        if(message.sender.nickname.contains("Wsure")){
            val start = System.currentTimeMillis()
            val res :SendGuildChannelMsg.Response = sender.sendGuildChannelMsgAsync(SendGuildChannelMsg(6000051636714649L,1560174L,"res:${message.message}"))
            println("use ${System.currentTimeMillis() - start}ms res:"+res.objectToJson())
        }
    }
}