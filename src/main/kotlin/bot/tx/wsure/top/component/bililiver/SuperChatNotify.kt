package bot.tx.wsure.top.component.bililiver

import bot.tx.wsure.top.config.ChannelConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.wsure.bililiver.bililiver.BiliLiverEvent
import top.wsure.bililiver.bililiver.dtos.event.cmd.GuardBuy
import top.wsure.bililiver.bililiver.dtos.event.cmd.RoomBlockMsg
import top.wsure.bililiver.bililiver.dtos.event.cmd.SuperChatMessage
import top.wsure.guild.common.utils.JsonUtils.objectToJson
import top.wsure.guild.unofficial.UnOfficialClient
import top.wsure.guild.unofficial.dtos.api.SendGuildChannelMsg

class SuperChatNotify(val config:List<ChannelConfig>, val client: UnOfficialClient): BiliLiverEvent() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override suspend fun onSuperChatMessage(superChatMessage: SuperChatMessage){
        logger.info("Room :{} ,SC content:{},",room.objectToJson(),superChatMessage.objectToJson())
        val msg = " - $ $ $ - \n「`${room.uname}`收到了`${superChatMessage.userInfo.uname}`发送了${superChatMessage.price}块 SC:`${superChatMessage.message}`」"

        config.onEach {
            client.sender.sendGuildChannelMsgAsync(unofficialGuildMessage(msg,it))
        }
    }

    override suspend fun onRoomBlockMsg(roomBlockMsg: RoomBlockMsg){
        logger.info("Room :{} ,有ban ban content:{},",room.objectToJson(),roomBlockMsg.objectToJson())
        val msg = " - # # # - \n「恭喜`${roomBlockMsg.uname}`(uid:${roomBlockMsg.uid})在主播`${room.uname}`的直播间被ban」"
        config.onEach {
            client.sender.sendGuildChannelMsgAsync(unofficialGuildMessage(msg,it))
        }
    }

    override suspend fun onGuardBuy(guardBuy: GuardBuy) {

    }

    fun unofficialGuildMessage(msg:String,channel: ChannelConfig): SendGuildChannelMsg {
        return SendGuildChannelMsg( channel.guildId.toLong(),channel.channelId.toLong(),msg )  //6000051636714649,1370732,msg))
    }
}