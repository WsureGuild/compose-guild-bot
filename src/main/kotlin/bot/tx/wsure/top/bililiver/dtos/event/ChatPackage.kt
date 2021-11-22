package bot.tx.wsure.top.bililiver.dtos.event

import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.brotli
import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.readUInt32BE
import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.toChatPackageList
import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.write
import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.writeInt32BE
import bot.tx.wsure.top.bililiver.BiliLiverChatUtils.zlib
import bot.tx.wsure.top.bililiver.enums.Operation
import bot.tx.wsure.top.bililiver.enums.ProtocolVersion
import kotlinx.serialization.Serializable
import okio.ByteString
import okio.ByteString.Companion.toByteString

@Serializable
open class ChatPackage(
    val packetLength: Int,
    val headerLength: Int = 16,
    val protocolVersion: ProtocolVersion,
    val operation: Operation,
    val bodyHeaderLength: Int = 1,
    val body: ByteArray
) {

    constructor(protocolVersion: ProtocolVersion,operation: Operation,body: ByteArray):
            this(body.size+16,16,protocolVersion,operation,1,body)
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

    open fun content(): String {
        return when (protocolVersion) {
            ProtocolVersion.JSON -> {
                return String(body)
            }
            ProtocolVersion.INT -> {
                return String(body) //if() body.readUInt32BE().toString()
            }
            ProtocolVersion.BROTLI -> {
                return body.brotli().toChatPackageList().joinToString("\n"){ it.content() }
            }
            ProtocolVersion.ZLIB_INFLATE -> {
                return body.zlib().toChatPackageList().joinToString("\n"){ it.content() }
            }
            else -> ""
        }
    }

    fun encode(): ByteString {
        return (headerByteArray() + body).toByteString()
    }

    open fun headerByteArray(): ByteArray {
        val header = ByteArray(16)
        header.writeInt32BE(packetLength.toLong(), 0)
        header.write(headerLength, 6 - 1)
        header.write(protocolVersion.code, 8 - 1)
        header.write(operation.code, 12 - 1)
        header.write(bodyHeaderLength, 16 - 1)
        return header
    }

}
