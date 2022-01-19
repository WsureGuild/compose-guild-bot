package bot.tx.wsure.top.schedule

import it.justwrote.kjob.KronJob
import top.wsure.guild.unofficial.UnOfficialClient
import top.wsure.guild.unofficial.UnofficialMessageSender

interface CronJob{
    suspend fun execute(params:Map<String,String> = mutableMapOf(), client: UnOfficialClient? = null)
}

sealed class BaseCronJob(name: String,cronExpression: String):KronJob(name, cronExpression),CronJob