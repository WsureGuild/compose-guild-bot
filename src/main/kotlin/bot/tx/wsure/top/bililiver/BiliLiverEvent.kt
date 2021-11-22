package bot.tx.wsure.top.bililiver

import bot.tx.wsure.top.bililiver.dtos.api.room.Room
import bot.tx.wsure.top.bililiver.dtos.event.cmd.*

abstract class BiliLiverEvent(var room: Room) {

    open fun onSuperChatMessage(superChatMessage: SuperChatMessage){

    }
    fun onSendGift(sendGift: SendGift){

    }

    fun onComboSend(comboSend: ComboSend){

    }

    fun onOnlineRankTop3(onlineRankTop3: OnlineRankTop3){

    }

    fun onRoomRealTimeMessageUpdate(roomRealTimeMessageUpdate: RoomRealTimeMessageUpdate){

    }
}
