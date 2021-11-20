package bot.tx.wsure.top.unofficial.dtos.api

import bot.tx.wsure.top.unofficial.dtos.event.BaseEventInterface
import bot.tx.wsure.top.unofficial.enums.ActionEnums
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendGuildChannelMsgAction(
    @SerialName("guild_id") val guildId:Long,
    @SerialName("channel_id") val channelId:Long,
    val message:String
)

fun BaseEventInterface.toSendGuildChannelMsgAction(message:String):BaseAction<SendGuildChannelMsgAction>{
    return BaseAction(
        ActionEnums.SEND_GUILD_CHANNEL_MSG,
        SendGuildChannelMsgAction(this.guildId,this.channelId,message)
    )
}