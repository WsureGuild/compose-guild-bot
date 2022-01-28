package bot.tx.wsure.top.component.official


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.wsure.guild.common.utils.JsonUtils.objectToJson
import top.wsure.guild.official.dtos.OfficialCode.messageContent
import top.wsure.guild.official.intf.OfficialBotEvent
import top.wsure.guild.common.utils.ac.AhoCorasickMatcher
import top.wsure.guild.official.dtos.Message
import top.wsure.guild.official.dtos.event.reactions.MessageReactionEvent

class EditRoles: OfficialBotEvent() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override suspend fun onAtMessageCreate(data: Message) {
        logger.info("onAtMessageCreate :${data.objectToJson()}" )
        editRoles(data)
    }

    override suspend fun onMessageCreate(data: Message) {
        logger.info("MessageCreate :${data.objectToJson()}" )
        editRoles(data)
    }

    fun editRoles(data: Message){val message = data.content
        val guildRoles = sender.getRoles(data.guildId)
        //一个自己做的ac自动机，用作字符串匹配
        val acMatcher = AhoCorasickMatcher(guildRoles){it.name}
        //在 剔除了at 和 频道连接之后的消息中搜索角色名
        if(message?.messageContent().isNullOrEmpty()){
            return
        }
        val roles = acMatcher.search(message!!.messageContent())
        if(roles.isNotEmpty())
        {
            val isDelete = message.contains("删除")
            logger.info("{} ${if(isDelete) "del" else "add"} roles :{}",data.author,roles)
            val successRoles = roles.filter { if(isDelete) sender.delRoles(data.guildId,data.author.id,it.id)
            else sender.addRoles(data.guildId,data.author.id,it.id) }
            val msg = "已经为${data.author.username}${if(isDelete) "删除" else "设置"}了身份${successRoles.joinToString { it.name }}"
            logger.info(" EditRoles success ,msg:$msg atMsg:$data")

            kotlin.runCatching { sender.reply(data.channelId,data.id,msg) }.onFailure {
                it.printStackTrace()
            }
        }
    }
}