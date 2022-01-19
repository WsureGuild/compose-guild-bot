package bot.tx.wsure.top.schedule

import top.wsure.guild.unofficial.UnofficialMessageSender
import it.justwrote.kjob.InMem
import it.justwrote.kjob.kjob
import it.justwrote.kjob.kron.Kron
import it.justwrote.kjob.kron.KronModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.wsure.guild.unofficial.UnOfficialClient

class JobManager(val config:Map<String,Map<String,String>>,val client: UnOfficialClient? = null) {
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
                    job.execute(config[it.name]?: mutableMapOf(),client)
                    logger.info("[task: ${it.name}] - end , use ${System.currentTimeMillis() - start} ms")
                }
            }
        }
    }

    fun stop() = kjob.shutdown()

}