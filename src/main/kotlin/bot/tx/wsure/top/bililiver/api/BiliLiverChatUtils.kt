package bot.tx.wsure.top.bililiver.api

import bot.tx.wsure.top.bililiver.enums.Operation
import bot.tx.wsure.top.bililiver.enums.ProtocolVersion
import bot.tx.wsure.top.bililiver.dtos.event.ChatPackage
import com.aayushatharva.brotli4j.Brotli4jLoader
import com.aayushatharva.brotli4j.decoder.BrotliInputStream
import okio.ByteString
import okio.ByteString.Companion.toByteString
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.InflaterOutputStream
import kotlin.math.pow

object BiliLiverChatUtils {

    fun ByteString.toChatPackage(): ChatPackage {
        if(this.size < 16) throw RuntimeException(" unknown package ")
        val headerByteArray = this.substring(0,16).hex().toUint8Array()
        val bodyByteArray = this.substring(16).toByteArray()
        return ChatPackage(
            readInt(headerByteArray,0,4),
            readInt(headerByteArray,4,2),
            ProtocolVersion.getByCode(readInt(headerByteArray,6,2)),
            Operation.getByCode(readInt(headerByteArray,8,4)),
            readInt(headerByteArray,12,4),
            bodyByteArray
        )
    }

    fun ByteArray.toChatPackage(): ChatPackage {
        return this.toByteString().toChatPackage()
    }

    fun ByteArray.zlib(): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val inflaterOutputStream = InflaterOutputStream(outputStream)
        inflaterOutputStream.write(this)
        inflaterOutputStream.close()
        return outputStream.toByteArray()
    }

    fun ByteArray.brotli():ByteArray{
        val inputStream = ByteArrayInputStream(this)
        val outputStream = ByteArrayOutputStream()
        Brotli4jLoader.ensureAvailability()
        val brotliInputStream = BrotliInputStream(inputStream)
        var read = brotliInputStream.read()
        while (read > -1) {
            outputStream.write(read)
            read = brotliInputStream.read()
        }
        return outputStream.toByteArray()
    }

    fun String.toUint8Array(): Array<Int> {
        check(length % 2 == 0) { "Must have an even length" }

        return chunked(2)
            .map { it.toInt(16) }
            .toTypedArray()
    }

    fun readInt(rawMessage: Array<Int>,start:Int,length:Int):Int{
        var result = 0
        val now = rawMessage.drop(start).take(length)
        for ((i , _) in now.withIndex()){
            result += (256.toDouble().pow(i).toInt() * now[length-i-1])
        }
        return result
    }

    fun ByteArray.readUInt32BE(offset: Int = 0): Long {
        throwOffsetError(this, offset, 4)
        return (((this[offset].toInt() and 0xFF).toLong() shl 24) or
                ((this[offset + 1].toInt() and 0xFF).toLong() shl 16) or
                ((this[offset + 2].toInt() and 0xFF).toLong() shl 8) or
                (this[offset + 3].toInt() and 0xFF).toLong())
    }
    private fun throwOffsetError(byteArray: ByteArray, offset: Int, length: Int = 1, byteLength: Int = 0) {
        if (offset > byteArray.size - length - byteLength) throw IllegalArgumentException("The value of \"offset\" is out of range. It must be >= 0 and <= ${byteArray.size - length - byteLength}. Received ${offset}")
    }

    fun ByteArray.write(value:Int,offset: Int = 0){
        this[offset] = value.toByte()
    }
}