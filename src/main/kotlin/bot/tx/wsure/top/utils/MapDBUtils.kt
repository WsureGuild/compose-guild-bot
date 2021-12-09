package bot.tx.wsure.top.utils

import io.ktor.serialization.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.mapdb.*

object MapDBUtils {
    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> mapDBCborSerializer(): Serializer<T> {
        return object :Serializer<T>{
            override fun serialize(out: DataOutput2, value: T) {
                out.writeUTF(DefaultJson.encodeToString(Json.serializersModule.serializer(), value))
            }

            override fun deserialize(input: DataInput2, available: Int): T {
                return DefaultJson.decodeFromString(Json.serializersModule.serializer(),input.readUTF())

            }

        }
    }
}
class MapDBWarp<K,V>(val db: DB, val cache: HTreeMap<K, V>){
    operator fun get(key: K,apply:()->V? = { null }): ElementWarp<V>? {
        val value = cache[key] ?: runBlocking { val initValue = apply()
            if(initValue != null) set(key,initValue)
            return@runBlocking initValue
        }
        return if(value == null) null
        else ElementWarp(db, value) { set(key, it) }
    }

    operator fun set(key: K, value: V?) {
        cache[key] = value
        db.commit()
    }
    fun remove(key: K) {
        cache.remove(key)
        db.commit()
    }

    operator fun set(key: K, value: ElementWarp<V>?) {
        cache[key] = value?.value
        db.commit()
    }
}

class ElementWarp<V>(val db: DB,var value: V,val setParent:(V)->Unit){

    fun set(apply: (V) -> Unit){
        apply(value)
        setParent(value)
        db.commit()
    }

    fun <T> get(apply: (V) -> T?): T? {
        return apply(value)
    }
}