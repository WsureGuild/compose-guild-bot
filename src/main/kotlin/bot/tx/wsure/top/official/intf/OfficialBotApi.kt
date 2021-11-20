package bot.tx.wsure.top.official.intf

import bot.tx.wsure.top.config.Global
import bot.tx.wsure.top.official.dtos.api.Role
import bot.tx.wsure.top.official.dtos.api.RolesApiRes
import bot.tx.wsure.top.utils.HttpUtils.officialApiDelete
import bot.tx.wsure.top.utils.HttpUtils.officialApiGet
import bot.tx.wsure.top.utils.HttpUtils.officialApiPut
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

    suspend fun getRoles(guildId:String):List<Role>{
        val rolesApiRes = roles.replace("{{guild_id}}", guildId)
            .officialApiGet<RolesApiRes>()
        logger.info("roles :{}",rolesApiRes)
        return rolesApiRes?.roles ?: emptyList()
    }
    fun getRolesOkhttp(guildId:String):List<Role>{
        val url = roles.replace("{{guild_id}}", guildId)
        val rolesApiRes = OkHttpUtils.getJson(url,officeApiHeader).jsonToObjectOrNull<RolesApiRes>()
        logger.info("roles :{}",rolesApiRes)
        return rolesApiRes?.roles ?: emptyList()
    }
    suspend fun addRoles(guildId:String,userId:Long,roleId:String):Boolean{
        editRole.replace("{{guild_id}}", guildId)
            .replace("{{user_id}}",userId.toString())
            .replace("{{role_id}}",roleId)
            .officialApiPut<Any>()
        return true
    }
    suspend fun delRoles(guildId:String,userId:Long,roleId:String):Boolean{
        editRole.replace("{{guild_id}}", guildId)
            .replace("{{user_id}}",userId.toString())
            .replace("{{role_id}}",roleId)
            .officialApiDelete<Any>()
        return true
    }
}