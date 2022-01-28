package bot.tx.wsure.top.schedule

import it.justwrote.kjob.KronJob
import top.wsure.guild.common.job.CornJob

sealed class BaseCronJob(name: String,cronExpression: String):KronJob(name, cronExpression), CornJob