package bot.tx.wsure.top.schedule

import bot.tx.wsure.top.unofficial.UnOfficialBotClient
import it.justwrote.kjob.KronJob

interface CronJob{
    suspend fun execute(params:Map<String,String> = mutableMapOf(), sender: UnOfficialBotClient? = null)
}

sealed class BaseCronJob(name: String,cronExpression: String):KronJob(name, cronExpression),CronJob