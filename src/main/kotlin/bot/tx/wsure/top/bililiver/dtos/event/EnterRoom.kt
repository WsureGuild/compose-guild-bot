package bot.tx.wsure.top.bililiver.dtos.event
import bot.tx.wsure.top.bililiver.enums.Operation
import bot.tx.wsure.top.bililiver.enums.ProtocolVersion
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName
import kotlinx.serialization.Transient


@Serializable
data class EnterRoom(
    @SerialName("roomid")
    val roomid: Long,
    @SerialName("key")
    val key: String? = null,
    @SerialName("uid")
    val uid: Int = 0,
    @SerialName("protover")
    val protover: Int = 3,
    @SerialName("platform")
    val platform: String = "web",
    @SerialName("type")
    val type: Int = 2,
){
    fun toPackage():ChatPackage{
        val bodyByteArray = this.objectToJson().toByteArray()
        return ChatPackage(ProtocolVersion.INT,Operation.HELLO, bodyByteArray)
    }
}