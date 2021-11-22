package bot.tx.wsure.top.bililiver

import bot.tx.wsure.top.bililiver.api.BiliLiverApi
import bot.tx.wsure.top.bililiver.dtos.api.room.Room

class BiliLiverConsole(roomId:String,eventList:(Room) ->List<BiliLiverEvent> = { emptyList() }) {
    init {
        BiliLiverApi.getRealRoomId(roomId)?.also { room ->
            BiliLiverApi.getTokenAndUrl(room.roomid)?.also { tokenAndUrl ->

                BiliLiverClient(room, tokenAndUrl,eventList(room) )
            }
        }
    }

}