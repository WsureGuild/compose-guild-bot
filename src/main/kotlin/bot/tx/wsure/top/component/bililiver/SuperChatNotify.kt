package bot.tx.wsure.top.component.bililiver

import bot.tx.wsure.top.bililiver.BiliLiverEvent
import bot.tx.wsure.top.bililiver.dtos.api.room.Room
import bot.tx.wsure.top.bililiver.dtos.event.cmd.SuperChatMessage
import bot.tx.wsure.top.unofficial.UnOfficialBotClient
import bot.tx.wsure.top.unofficial.dtos.api.BaseAction
import bot.tx.wsure.top.unofficial.dtos.api.SendGuildChannelMsgAction
import bot.tx.wsure.top.unofficial.enums.ActionEnums
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SuperChatNotify(val config:List<Pair<Long,Long>>, room: Room, val sender: UnOfficialBotClient): BiliLiverEvent(room) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun onSuperChatMessage(superChatMessage: SuperChatMessage){
        logger.info("Room :{} ,SC content:{},",room,superChatMessage)
        val msg = "主播`${room.uname}`收到了${superChatMessage.userInfo.uname} 发送了${superChatMessage.price}CNY SC： `${superChatMessage.message}`"
        config.onEach {
            sender.sendMessage(unofficialGuildMessage(msg,it).objectToJson())
        }


    }

    fun unofficialGuildMessage(msg:String,pair: Pair<Long,Long>): BaseAction<SendGuildChannelMsgAction> {
        return BaseAction(ActionEnums.SEND_GUILD_CHANNEL_MSG, SendGuildChannelMsgAction( pair.first,pair.second,msg ))  //6000051636714649,1370732,msg))
    }
}