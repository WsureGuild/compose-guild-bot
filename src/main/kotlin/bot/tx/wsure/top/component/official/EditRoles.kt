package bot.tx.wsure.top.component.official

import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.official.dtos.event.AtMessageCreateEvent
import bot.tx.wsure.top.official.intf.OfficialBotEvent
import bot.tx.wsure.top.unofficial.UnOfficialBotClient
import bot.tx.wsure.top.unofficial.dtos.api.BaseAction
import bot.tx.wsure.top.unofficial.dtos.api.SendGuildChannelMsgAction
import bot.tx.wsure.top.unofficial.enums.ActionEnums
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EditRoles(val sender:UnOfficialBotClient): OfficialBotEvent() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override suspend fun onAtMessageCreate(data: AtMessageCreateEvent) {
        if(data.d.guildId == Global.CONFIG.devGuild.id && data.d.channelId == Global.CONFIG.devGuild.channels["sendRoles"]){
            val message = data.d.content
            val roles = Global.rolesAhoCorasickMatcher.search(message)
            if(roles.isNotEmpty())
            {
                logger.info(" roles :{}",roles)
                //  todo

                sender.sendMessage(unofficialGuildMessage("已经为你设置了身份${roles.joinToString { it.name }}").objectToJson())
            }
        }

    }

    fun unofficialGuildMessage(msg:String): BaseAction<SendGuildChannelMsgAction>{
        return BaseAction(ActionEnums.SEND_GUILD_CHANNEL_MSG,SendGuildChannelMsgAction(6000051636714649,1330594,msg))
    }
}