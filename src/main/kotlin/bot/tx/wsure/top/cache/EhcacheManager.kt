package bot.tx.wsure.top.cache

import bot.tx.wsure.top.component.unofficial.YbbTrainMapDB
import kotlinx.serialization.Serializable
import org.ehcache.Cache
import org.ehcache.PersistentCacheManager
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.config.units.EntryUnit
import org.ehcache.config.units.MemoryUnit
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.Closeable
import java.io.File
import kotlin.io.path.Path

object EhcacheManager : Closeable {
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    val persistentCacheManager: PersistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        .with(CacheManagerBuilder.persistence(File(Path("").toAbsolutePath().toString(), "ehcache")))
        // CACHE YBB
        .withCache(
            EhcacheItem.YBB.name, cacheConfigBuilder<String, MutableMap<String, Long>>(
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(10, EntryUnit.ENTRIES)
                    .offheap(1, MemoryUnit.MB)
                    .disk(20, MemoryUnit.MB, true)
            ).withValueSerializer(
                ehcacheCborSerializer()
            )
        )
        .withCache(
            EhcacheItem.YBB_TOP.name, cacheConfigBuilder<String,MutableMap<String, List<YbbTrainMapDB.TopRecord>>>(
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(10, EntryUnit.ENTRIES)
                    .offheap(1, MemoryUnit.MB)
                    .disk(2, MemoryUnit.MB, true)
            ).withValueSerializer(ehcacheCborSerializer())
        )
        // CACHE YBB
        //TODO
        .build(true)

    val ybb = EhcacheItem.YBB.getCache<String,MutableMap<String, Long>>()
    val ybbTop = EhcacheItem.YBB_TOP.getCache<String,MutableMap<String, List<YbbTrainMapDB.TopRecord>>>()

    init {
        persistentCacheManager.close()
    }

    override fun close() {
        logger.info("persistentCacheManager.close() !!!")
        persistentCacheManager.close()
    }

    @Serializable
    enum class EhcacheItem {
        YBB,
        YBB_TOP,

        ;

        inline fun <reified K, reified V> getCache(): Cache<K, V> {
            return persistentCacheManager.getCache(this.name)
        }
    }
}