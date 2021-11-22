package bot.tx.wsure.top.bililiver

import bot.tx.wsure.top.bililiver.api.BiliLiverApi

class BiliLiverConsole(roomId:String) {
    init {
        BiliLiverApi.getRealRoomId(roomId)?.also { room ->
            BiliLiverApi.getTokenAndUrl(room.roomid)?.also { tokenAndUrl ->
                BiliLiverClient(room.roomid, tokenAndUrl.token)
            }
        }
    }

}