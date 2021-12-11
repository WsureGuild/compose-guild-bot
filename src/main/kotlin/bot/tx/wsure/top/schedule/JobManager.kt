package bot.tx.wsure.top.schedule

import bot.tx.wsure.top.unofficial.UnOfficialBotClient
import it.justwrote.kjob.InMem
import it.justwrote.kjob.kjob
import it.justwrote.kjob.kron.Kron
import it.justwrote.kjob.kron.KronModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JobManager(val config:Map<String,Map<String,String>>,val sender: UnOfficialBotClient? = null) {
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    val kjob by lazy {
        kjob(InMem) {
            extension(KronModule)
        }.start()
    }

    fun start(){
        logger.info("JobManager - start")
        BaseCronJob::class.sealedSubclasses.mapNotNull { it.objectInstance }.onEach { job ->
            kjob(Kron).kron(job) {
                maxRetries = 3
                execute {
                    val start = System.currentTimeMillis()
                    logger.info("[task: ${it.name}] - start")
                    job.execute(config[it.name]?: mutableMapOf(),sender)
                    logger.info("[task: ${it.name}] - end , use ${System.currentTimeMillis() - start} ms")
                }
            }
        }
    }

    fun stop() = kjob.shutdown()

}