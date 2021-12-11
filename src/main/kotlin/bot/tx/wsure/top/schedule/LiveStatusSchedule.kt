package bot.tx.wsure.top.schedule

import bot.tx.wsure.top.unofficial.UnOfficialBotClient

object LiveStatusSchedule:BaseCronJob("LiveStatusSchedule","0 0/1 * * * ?") {
    override suspend fun execute(params: Map<String, String>, sender: UnOfficialBotClient?) {

    }
}