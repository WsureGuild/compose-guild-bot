package bot.tx.wsure.top.cache

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.ByteBufferInput
import com.esotericsoftware.kryo.io.ByteBufferOutput
import io.ktor.serialization.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.serializer
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.spi.serialization.Serializer
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

inline fun <reified K, reified T> CacheManager.getCache(alias: String): Cache<K, T> =
    getCache(alias, K::class.java, T::class.java)

inline fun <T, reified V : Any> CacheConfigurationBuilder<T, V>.withKotlinValueSerializer(): CacheConfigurationBuilder<T, V> {
    return withValueSerializer(getKotlinSerializer())
}

inline fun <reified K, reified V> cacheConfigBuilder(resourcePoolsBuilder: ResourcePoolsBuilder): CacheConfigurationBuilder<K, V> {
    return CacheConfigurationBuilder.newCacheConfigurationBuilder(K::class.java, V::class.java, resourcePoolsBuilder)
}

inline fun <reified T> kotlinxSerializer() :Serializer<T>{
    return object : Serializer<T>{

        override fun equals(obj: T, binary: ByteBuffer) = obj == read(binary)

        override fun serialize(`object`: T): ByteBuffer {
            val be :String = DefaultJson.encodeToString(serializersModule.serializer(), `object`)
            return ByteBuffer.wrap(be.toByteArray())
        }

        override fun read(binary: ByteBuffer): T {
            val byteArray = binary.array()
            return DefaultJson.decodeFromString(serializersModule.serializer(),String(byteArray))
        }

    }
}
@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> ehcacheCborSerializer() :Serializer<T>{
    return object : Serializer<T>{

        override fun equals(obj: T, binary: ByteBuffer): Boolean = obj == read(binary)

        override fun serialize(obj: T): ByteBuffer = ByteBuffer.wrap(Cbor.encodeToByteArray(serializersModule.serializer(),obj))

        override fun read(binary: ByteBuffer): T = Cbor.decodeFromByteArray(serializersModule.serializer(),toByteArray(binary))

        private fun toByteArray(buf: ByteBuffer): ByteArray {
            val arr = ByteArray(buf.remaining())
            buf.get(arr)
            return arr
        }
    }
}

inline fun <reified T> getKotlinSerializer(): Serializer<T> {
    return object : Serializer<T> {

        val kryo = Kryo().apply {
            isRegistrationRequired = false
            register(T::class.java)
        }

        override fun equals(obj: T, binary: ByteBuffer) = obj == read(binary)

        override fun serialize(obj: T): ByteBuffer {
            val byteOutputStream = ByteArrayOutputStream()
            val output = ByteBufferOutput(byteOutputStream)
            kryo.writeClassAndObject(output, obj)
            output.close()
            return output.byteBuffer
        }

        override fun read(binary: ByteBuffer): T {
            val input = ByteBufferInput(binary)
            val t = kryo.readClassAndObject(input) as T
            input.close()
            return t
        }
    }
}

operator fun <K, V> Cache<K, V>.set(id: K, value: V) {
    put(id, value)
}