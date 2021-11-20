package bot.tx.wsure.top.official

import bot.tx.wsure.top.common.WsBotClient
import bot.tx.wsure.top.official.intf.OfficialBotEvent

class OfficialBotClient(
    token:String,
    officialEvents:List<OfficialBotEvent>,
    wsUrl :String = "wss://api.sgroup.qq.com/websocket",
) : WsBotClient<OfficialBotListener>(
    wsUrl = wsUrl,
    OfficialBotListener(token,officialEvents)
)