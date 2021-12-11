package bot.tx.wsure.top.bililiver

import bot.tx.wsure.top.bililiver.api.BiliLiverApi

class BiliLiverConsole(roomId:String,eventList:List<BiliLiverEvent> = emptyList() ) {
    init {
        BiliLiverApi.getRealRoomId(roomId)?.also { room ->
            BiliLiverApi.getTokenAndUrl(room.roomid)?.also { tokenAndUrl ->
                val host = if(tokenAndUrl.hostList.isEmpty()) null else tokenAndUrl.hostList.map { it.toWssUrl() }.random()
                BiliLiverClient(room, tokenAndUrl.token ,eventList,host)
            }
        }
    }

}