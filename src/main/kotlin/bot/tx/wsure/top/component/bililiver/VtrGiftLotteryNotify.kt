package bot.tx.wsure.top.component.bililiver

import bot.tx.wsure.top.config.ChannelConfig
import top.wsure.guild.unofficial.dtos.api.BaseAction
import top.wsure.guild.unofficial.dtos.api.SendGuildChannelMsg
import top.wsure.guild.unofficial.enums.ActionEnums
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.wsure.bililiver.bililiver.BiliLiverEvent
import top.wsure.bililiver.bililiver.dtos.event.cmd.VtrGiftLottery
import top.wsure.guild.common.utils.JsonUtils.objectToJson
import top.wsure.guild.unofficial.UnOfficialClient

class VtrGiftLotteryNotify(val config:List<ChannelConfig>, val client: UnOfficialClient): BiliLiverEvent() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    val liverMapGuild = mutableMapOf<String,Long>(
        "阿梓从小就很可爱" to 36667731636792997 ,
        "七海Nana7mi" to 6000051636714649 ,
        "小可学妹" to 19995411637241900 ,
    )
    override suspend fun onVtrGiftLottery(vtrGiftLottery: VtrGiftLottery) {
        logger.info("Room :{} ,谷 content:{},",room.objectToJson(),vtrGiftLottery.objectToJson())
        val rale = vtrGiftLottery.interactMsg.contains("虚拟主播专属超稀有奖品")
        val msg = " - ${if (rale) "⭐ ⭐ ⭐" else "@ @ @"} - \n「`${room.uname}`房间:${vtrGiftLottery.interactMsg}」"

        val guilds = liverMapGuild.entries.filter { e -> vtrGiftLottery.interactMsg.contains("-${e.key}") }.map {
            it.value
        }
        config.onEach {
            if(rale || guilds.contains(it.guildId.toLong())){
                client.sender.sendGuildChannelMsgAsync(unofficialGuildMessage(msg,it))
            }
        }
    }

    fun unofficialGuildMessage(msg:String,channel: ChannelConfig): SendGuildChannelMsg {
        return SendGuildChannelMsg( channel.guildId,channel.channelId.toLong(),msg )  //6000051636714649,1370732,msg))
    }
}