package bot.tx.wsure.top.common

import bot.tx.wsure.top.utils.ScheduleUtils
import okhttp3.WebSocketListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.atomic.AtomicLong

abstract class BaseBotListener(private val retryTime:Long = 1000, private val retryWait:Long = 3000) : WebSocketListener() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
     lateinit var reconnect :()->Unit
    private val retryCount = AtomicLong(0)
    var retryTimer: Timer? = null

    private val retryTask:suspend ()-> Unit = suspend {
        retryCount.getAndIncrement()
        if(retryCount.get() > retryTime){
            cancel()
        }  else {
            logger.warn(" try to reconnect ")
            reconnect()
        }
    }

    fun reconnect(func:()->Unit){
        reconnect = func
    }

    fun startRetryReconnect() {
        if(retryTimer == null ){
            logger.info("start retry reconnect")
            retryTimer = ScheduleUtils.loopEvent(retryTask,Date(),retryWait)
        }
    }

    fun stopRetryReconnect() {
        if(retryTimer != null){
            logger.info("stop retry reconnect")
            retryCount.set(0)
            retryTimer?.cancel()
            retryTimer = null
        }

    }

    open fun cancel(){
        logger.error("retry to many times !")
        stopRetryReconnect()
    }


}