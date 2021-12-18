package bot.tx.wsure.top.unofficial.dtos.api

import bot.tx.wsure.top.unofficial.dtos.event.BaseEventInterface
import bot.tx.wsure.top.unofficial.enums.ActionEnums
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendGuildChannelMsg(
    @SerialName("guild_id") val guildId:Long,
    @SerialName("channel_id") val channelId:Long,
    val message:String
){
    @Serializable
    data class Response(
        @SerialName("message_id")
        val messageId : String
    )
}

fun BaseEventInterface.toSendGuildChannelMsgAction(message:String):SendGuildChannelMsg{
    return SendGuildChannelMsg(this.guildId,this.channelId,message)
}