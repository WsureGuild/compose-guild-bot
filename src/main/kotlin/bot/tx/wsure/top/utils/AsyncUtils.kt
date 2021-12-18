package bot.tx.wsure.top.utils

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.withTimeout

class AsyncUtils<K>(val defaultTimeout: Long = 60 * 1000L) {
    val listeners = mutableMapOf<K, CompletableDeferred<Any>>()

    suspend inline fun <reified R> sendAndWaitResult(key: K, timeout: Long = defaultTimeout): R {
        val listener = CompletableDeferred<Any>()
        listeners[key] = listener
        try {
            return withTimeout(timeout) { listener.await() as R }
        } finally {
            listeners.remove(key)
        }
    }

    fun onReceive(key: K, value: Any) {
        listeners[key]?.complete(value)
    }

}