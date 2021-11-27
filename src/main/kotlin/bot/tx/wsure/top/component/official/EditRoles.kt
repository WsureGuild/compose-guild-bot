package bot.tx.wsure.top.component.official

import bot.tx.wsure.top.config.DevGuild
import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.official.dtos.event.AtMessageCreateEvent
import bot.tx.wsure.top.official.intf.OfficialBotApi
import bot.tx.wsure.top.official.intf.OfficialBotApi.reply
import bot.tx.wsure.top.official.intf.OfficialBotEvent
import bot.tx.wsure.top.unofficial.UnOfficialBotClient
import bot.tx.wsure.top.unofficial.dtos.api.BaseAction
import bot.tx.wsure.top.unofficial.dtos.api.SendGuildChannelMsgAction
import bot.tx.wsure.top.unofficial.enums.ActionEnums
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EditRoles(val guilds:List<DevGuild>): OfficialBotEvent() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    val guildChannelSet = guilds.map { "${it.id}::${it.channels["sendRoles"]}" }.toSet()

    override suspend fun onAtMessageCreate(data: AtMessageCreateEvent) {
        val guild = guilds.firstOrNull { it.id == data.d.guildId }
        if(guild != null){
            val message = data.d.content
            val roles = Global.rolesAhoCorasickMatcherMap[data.d.guildId]?.search(message) ?: emptyList()
            if(roles.isNotEmpty())
            {
                val isDelete = message.contains("删除")
                logger.info("{} ${if(isDelete) "del" else "add"} roles :{}",data.d.author,roles)
                val successRoles = roles.filter { if(isDelete) OfficialBotApi.delRoles(guild.botName,data.d.guildId,data.d.author.id,it.id)
                else OfficialBotApi.addRoles(guild.botName,data.d.guildId,data.d.author.id,it.id) }
                val msg = "已经为${data.d.author.username}${if(isDelete) "删除" else "设置"}了身份${successRoles.joinToString { it.name }}"
                logger.info(" EditRoles success ,msg:$msg atMsg:$data")

                kotlin.runCatching { data.reply(guild.botName,msg) }.onFailure {
                    it.printStackTrace()
                }
            }
        }

    }
}