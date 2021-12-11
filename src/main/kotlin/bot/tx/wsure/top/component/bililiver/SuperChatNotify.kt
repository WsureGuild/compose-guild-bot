package bot.tx.wsure.top.component.bililiver

import bot.tx.wsure.top.bililiver.BiliLiverEvent
import bot.tx.wsure.top.bililiver.dtos.event.cmd.RoomBlockMsg
import bot.tx.wsure.top.bililiver.dtos.event.cmd.SuperChatMessage
import bot.tx.wsure.top.config.ChannelConfig
import bot.tx.wsure.top.unofficial.UnOfficialBotClient
import bot.tx.wsure.top.unofficial.dtos.api.BaseAction
import bot.tx.wsure.top.unofficial.dtos.api.SendGuildChannelMsgAction
import bot.tx.wsure.top.unofficial.enums.ActionEnums
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SuperChatNotify(val config:List<ChannelConfig>, val sender: UnOfficialBotClient): BiliLiverEvent() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun onSuperChatMessage(superChatMessage: SuperChatMessage){
        logger.info("Room :{} ,SC content:{},",room.objectToJson(),superChatMessage.objectToJson())
        val msg = " - $ $ $ - \n「`${room.uname}`收到了`${superChatMessage.userInfo.uname}`发送了${superChatMessage.price}块 SC:`${superChatMessage.message}`」"

        config.onEach {
            sender.sendMessage(unofficialGuildMessage(msg,it).objectToJson())
        }
    }

    override fun onRoomBlockMsg(roomBlockMsg: RoomBlockMsg){
        logger.info("Room :{} ,有ban ban content:{},",room.objectToJson(),roomBlockMsg.objectToJson())
        val msg = " - # # # - \n「恭喜`${roomBlockMsg.uname}`(uid:${roomBlockMsg.uid})在主播`${room.uname}`的直播间被ban」"
        config.onEach {
            sender.sendMessage(unofficialGuildMessage(msg,it).objectToJson())
        }
    }

    fun unofficialGuildMessage(msg:String,channel: ChannelConfig): BaseAction<SendGuildChannelMsgAction> {
        return BaseAction(ActionEnums.SEND_GUILD_CHANNEL_MSG, SendGuildChannelMsgAction( channel.guildId,channel.channelId,msg ))  //6000051636714649,1370732,msg))
    }
}