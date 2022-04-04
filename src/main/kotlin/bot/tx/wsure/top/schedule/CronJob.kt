package bot.tx.wsure.top.schedule

import it.justwrote.kjob.KronJob
import top.wsure.guild.common.job.CornJob
import java.time.LocalDateTime

sealed class BaseCronJob(name: String,cronExpression: String):KronJob(name, cronExpression), CornJob{
    var jobStartTime: LocalDateTime = LocalDateTime.now()

    override suspend fun execute(params: Map<String, String>) {
        jobStartTime = LocalDateTime.now().withNano(0).withSecond(0)
    }
}