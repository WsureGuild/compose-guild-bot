package bot.tx.wsure.top.bililiver

import bot.tx.wsure.top.bililiver.dtos.api.room.Room
import bot.tx.wsure.top.bililiver.dtos.event.cmd.*

abstract class BiliLiverEvent {

    lateinit var room: Room

    open fun onSuperChatMessage(superChatMessage: SuperChatMessage){

    }
    open fun onSendGift(sendGift: SendGift){

    }

    open fun onComboSend(comboSend: ComboSend){

    }

    open fun onOnlineRankTop3(onlineRankTop3: OnlineRankTop3){

    }

    open fun onRoomRealTimeMessageUpdate(roomRealTimeMessageUpdate: RoomRealTimeMessageUpdate){

    }

    open fun onRoomBlockMsg(roomBlockMsg:RoomBlockMsg){

    }

    open fun onSuperChatMessageDelete(superChatMessageDelete:SuperChatMessageDelete){

    }

    open fun onGuardBuy(guardBuy:GuardBuy){

    }

    open fun onHotRankSettlement(hotRankSettlement: HotRankSettlement) {

    }

    open fun onDanmuMsg(danmuMsg: DanmuMsg) {

    }

    open fun onInteractWord(interactWord: InteractWord) {

    }

    open fun onEntryEffect(entryEffect: EntryEffect) {

    }
}
