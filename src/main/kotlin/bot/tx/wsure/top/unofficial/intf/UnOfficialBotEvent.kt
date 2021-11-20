package bot.tx.wsure.top.unofficial.intf

import bot.tx.wsure.top.unofficial.UnofficialMessageSender
import bot.tx.wsure.top.unofficial.dtos.event.message.GuildMessage

abstract class UnOfficialBotEvent {
    open suspend fun onGuildMessage(sender: UnofficialMessageSender, message:GuildMessage){

    }
}