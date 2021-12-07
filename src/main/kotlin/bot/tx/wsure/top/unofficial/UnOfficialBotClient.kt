package bot.tx.wsure.top.unofficial

import bot.tx.wsure.top.common.WsBotClient
import bot.tx.wsure.top.unofficial.intf.UnOfficialBotEvent

class UnOfficialBotClient(
    officialEvents:List<UnOfficialBotEvent> = emptyList(),
    wsUrl :String = "ws://127.0.0.1:6700",
) : WsBotClient<UnOfficialBotListener>(
    wsUrl = wsUrl,
    UnOfficialBotListener(officialEvents)
)