package bot.tx.wsure.top.utils

import io.ktor.serialization.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.mapdb.DataInput2
import org.mapdb.DataOutput2
import org.mapdb.Serializer

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