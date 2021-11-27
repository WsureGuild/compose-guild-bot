package bot.tx.wsure.top.official.intf

import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.official.dtos.api.Role
import bot.tx.wsure.top.official.dtos.api.RolesApiRes
import bot.tx.wsure.top.official.dtos.api.TextMessage
import bot.tx.wsure.top.official.dtos.event.AtMessageCreateEvent
import bot.tx.wsure.top.utils.JsonUtils.jsonToObjectOrNull
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
import bot.tx.wsure.top.utils.OkHttpUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object OfficialBotApi {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    const val roles = "https://api.sgroup.qq.com/guilds/{{guild_id}}/roles"
    const val editRole = "https://api.sgroup.qq.com/guilds/{{guild_id}}/members/{{user_id}}/roles/{{role_id}}"
    const val sendMessage = "https://api.sgroup.qq.com/channels/{{channel_id}}/messages"

    fun officeApiHeader(botName:String):MutableMap<String,String> {
        return mutableMapOf(
            "Authorization" to Global.CONFIG.officialBots.first { it.name == botName }.botToken()
        )
    }
    fun AtMessageCreateEvent.reply(botName: String,msg:String):Boolean{
        val url = sendMessage.replace("{{channel_id}}",this.d.channelId)
        val json = TextMessage( msg, this.d.id).objectToJson()
        val header = officeApiHeader(botName)
        val res = OkHttpUtils.postJson(url,OkHttpUtils.addJson(json),header)
        logger.debug("url: $url json: $json header: $header res: $res")
        return true
    }
    fun getRolesOkhttp(botName:String,guildId:String):List<Role>{
        val url = roles.replace("{{guild_id}}", guildId)
        val rolesApiRes = OkHttpUtils.getJson(url,officeApiHeader(botName)).jsonToObjectOrNull<RolesApiRes>()
        logger.info("roles :{}",rolesApiRes)
        return rolesApiRes?.roles ?: emptyList()
    }
    fun addRoles(botName:String,guildId:String,userId:String,roleId:String):Boolean{
        val url = editRole.replace("{{guild_id}}", guildId)
            .replace("{{user_id}}", userId)
            .replace("{{role_id}}",roleId)
        val res = OkHttpUtils.put(url, emptyMap(), officeApiHeader(botName))
        logger.info("addRoles $url res:${res.isSuccessful}")
        return res.isSuccessful
    }
    fun delRoles(botName:String,guildId:String,userId:String,roleId:String):Boolean{
        val url = editRole.replace("{{guild_id}}", guildId)
            .replace("{{user_id}}", userId)
            .replace("{{role_id}}",roleId)
        val res = OkHttpUtils.delete(url, emptyMap(), officeApiHeader(botName))
        logger.info("delRoles res:${res.isSuccessful}")
        return res.isSuccessful
    }
}