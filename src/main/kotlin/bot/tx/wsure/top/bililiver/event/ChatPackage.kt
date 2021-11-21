package bot.tx.wsure.top.bililiver.event

import bot.tx.wsure.top.bililiver.api.BiliLiverChatUtils.brotli
import bot.tx.wsure.top.bililiver.api.BiliLiverChatUtils.readUInt32BE
import bot.tx.wsure.top.bililiver.api.BiliLiverChatUtils.toChatPackage
import bot.tx.wsure.top.bililiver.api.BiliLiverChatUtils.write
import bot.tx.wsure.top.bililiver.api.BiliLiverChatUtils.zlib
import bot.tx.wsure.top.bililiver.enums.Operation
import bot.tx.wsure.top.bililiver.enums.ProtocolVersion
import kotlinx.serialization.Serializable

@Serializable
open class ChatPackage(
    val packetLength: Int,
    val headerLength: Int,
    val protocolVersion: ProtocolVersion,
    val operation: Operation,
    val bodyHeaderLength: Int,
    val body: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChatPackage

        if (packetLength != other.packetLength) return false
        if (headerLength != other.headerLength) return false
        if (protocolVersion != other.protocolVersion) return false
        if (operation != other.operation) return false
        if (bodyHeaderLength != other.bodyHeaderLength) return false
        if (!body.contentEquals(other.body)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = packetLength
        result = 31 * result + headerLength
        result = 31 * result + protocolVersion.hashCode()
        result = 31 * result + operation.hashCode()
        result = 31 * result + bodyHeaderLength
        result = 31 * result + body.contentHashCode()
        return result
    }

    open fun content(): String? {
        return when (protocolVersion) {
            ProtocolVersion.JSON -> {
                return String(body)
            }
            ProtocolVersion.INT -> {
                return body.readUInt32BE().toString()
            }
            ProtocolVersion.BROTLI -> {
                return body.brotli().toChatPackage().content()
            }
            ProtocolVersion.ZLIB_INFLATE -> {
                return body.zlib().toChatPackage().content()
            }
            else -> null
        }
    }

    fun decode(): ByteArray {
        return headerByteArray() + body
    }

    open fun headerByteArray(): ByteArray {
        val header = ByteArray(16)
        header.write(packetLength, 4 - 1)
        header.write(headerLength, 6 - 1)
        header.write(protocolVersion.code, 8 - 1)
        header.write(operation.code, 12 - 1)
        header.write(bodyHeaderLength, 16 - 1)
        return header
    }

}
