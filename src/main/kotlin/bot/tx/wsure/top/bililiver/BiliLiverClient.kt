package bot.tx.wsure.top.bililiver

import bot.tx.wsure.top.common.WsBotClient

class BiliLiverClient(roomId:String,
                      token:String,
                      biliLiverEvents:List<BiliLiverEvent> = emptyList(),
                      wsUrl :String = "wss://broadcastlv.chat.bilibili.com/sub",
) : WsBotClient<BiliLiverListener>(
    wsUrl = wsUrl,
    BiliLiverListener(roomId,token,biliLiverEvents)
)