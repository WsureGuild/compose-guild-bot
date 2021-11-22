package bot.tx.wsure.top.bililiver

import bot.tx.wsure.top.bililiver.dtos.api.room.Room
import bot.tx.wsure.top.bililiver.dtos.api.token.TokenAndUrl
import bot.tx.wsure.top.common.WsBotClient

class BiliLiverClient(room:Room,
                      tokenAndUrl: TokenAndUrl,
                      biliLiverEvents:List<BiliLiverEvent> = emptyList(),
                      wsUrl :String = "wss://broadcastlv.chat.bilibili.com/sub",
) : WsBotClient<BiliLiverListener>(
    wsUrl = if(tokenAndUrl.hostList.isEmpty()) wsUrl else tokenAndUrl.hostList.map { it.toWssUrl() }.random(),
    BiliLiverListener(room,tokenAndUrl,biliLiverEvents)
)