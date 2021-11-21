package bot.tx.wsure.top.bililiver

import bot.tx.wsure.top.common.WsBotClient

class BiliLiverClient(roomId:String,
                      biliLiverEvents:List<BiliLiverEvent>,
                      wsUrl :String = "wss://broadcastlv.chat.bilibili.com/sub",
) : WsBotClient<BiliLiverListener>(
    wsUrl = wsUrl,
    BiliLiverListener(roomId,biliLiverEvents)
)