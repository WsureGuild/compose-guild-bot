package bot.tx.wsure.top.official.intf

import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.official.dtos.api.Role
import bot.tx.wsure.top.official.dtos.api.RolesApiRes
import bot.tx.wsure.top.utils.JsonUtils.jsonToObjectOrNull
import bot.tx.wsure.top.utils.OkHttpUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object OfficialBotApi {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    const val roles = "https://api.sgroup.qq.com/guilds/{{guild_id}}/roles"
    const val editRole = "https://api.sgroup.qq.com/guilds/{{guild_id}}/members/{{user_id}}/roles/{{role_id}}"

    val officeApiHeader = mutableMapOf(
        "Authorization" to Global.token
    )

    fun getRolesOkhttp(guildId:String):List<Role>{
        val url = roles.replace("{{guild_id}}", guildId)
        val rolesApiRes = OkHttpUtils.getJson(url,officeApiHeader).jsonToObjectOrNull<RolesApiRes>()
        logger.info("roles :{}",rolesApiRes)
        return rolesApiRes?.roles ?: emptyList()
    }
    fun addRoles(guildId:String,userId:String,roleId:String):Boolean{
        val url = editRole.replace("{{guild_id}}", guildId)
            .replace("{{user_id}}",userId.toString())
            .replace("{{role_id}}",roleId)
        val res = OkHttpUtils.put(url, emptyMap(), officeApiHeader)
        logger.info("addRoles $url res:${res.isSuccessful}")
        return res.isSuccessful
    }
    fun delRoles(guildId:String,userId:String,roleId:String):Boolean{
        val url = editRole.replace("{{guild_id}}", guildId)
            .replace("{{user_id}}",userId.toString())
            .replace("{{role_id}}",roleId)
        val res = OkHttpUtils.delete(url, emptyMap(), officeApiHeader)
        logger.info("delRoles res:${res.isSuccessful}")
        return res.isSuccessful
    }
}