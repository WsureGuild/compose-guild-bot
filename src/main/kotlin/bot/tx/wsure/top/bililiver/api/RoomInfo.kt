package bot.tx.wsure.top.bililiver.api
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

abstract class RoomInfo{
    abstract val code: Int
    abstract val message: String
    abstract val msg: String
    abstract val `data`: Any
}
@Serializable
open class SuccessRoomInfo(
    @SerialName("code")
    override val code: Int = 0,
    @SerialName("data")
    override val `data`: Room,
    @SerialName("message")
    override val message: String,
    @SerialName("msg")
    override val msg: String
): RoomInfo()

@Serializable
data class Room(
    @SerialName("content")
    val content: String,
    @SerialName("ctime")
    val ctime: String,
    @SerialName("roomid")
    val roomid: String,
    @SerialName("status")
    val status: String,
    @SerialName("uid")
    val uid: String,
    @SerialName("uname")
    val uname: String
)

@Serializable
open class FailRoomInfo(
    @SerialName("code")
    override val code: Int = 1,
    @SerialName("data")
    override val `data`: List<Room> = emptyList(),
    @SerialName("message")
    override val message: String,
    @SerialName("msg")
    override val msg: String
): RoomInfo()
