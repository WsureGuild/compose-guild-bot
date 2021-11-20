package bot.tx.wsure.top.component.unofficial

import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.official.dtos.api.Role
import bot.tx.wsure.top.official.intf.OfficialBotApi
import bot.tx.wsure.top.official.intf.OfficialBotApi.roles
import bot.tx.wsure.top.unofficial.UnofficialMessageSender
import bot.tx.wsure.top.unofficial.dtos.CQCode.getAt
import bot.tx.wsure.top.unofficial.dtos.CQCode.toAtCC
import bot.tx.wsure.top.unofficial.dtos.api.toSendGuildChannelMsgAction
import bot.tx.wsure.top.unofficial.dtos.event.message.GuildMessage
import bot.tx.wsure.top.unofficial.intf.UnOfficialBotEvent
import bot.tx.wsure.top.utils.ac.AhoCorasickMatcher
import kotlinx.coroutines.runBlocking

class SendRoles :UnOfficialBotEvent(){
    val OFFICIAL_GUILD_ID =  "14543211858034966728"

    val UNOFFICIAL_GUILD_ID =  "6000051636714649"

    val OFFICIAL_BOT_ID = 144115218678097866L


    override suspend fun onGuildMessage(sender: UnofficialMessageSender, message: GuildMessage) {
        val ac = Global.rolesAhoCorasickMatcher
        val res = ac.search(message.message)
        if(res.isNotEmpty()){
            sender.sendMessage(message.toSendGuildChannelMsgAction(OFFICIAL_BOT_ID.toAtCC() + " \n" + message.userId.toAtCC()+ " \n" +res.joinToString(","){ it.id }))
        }
    }



}