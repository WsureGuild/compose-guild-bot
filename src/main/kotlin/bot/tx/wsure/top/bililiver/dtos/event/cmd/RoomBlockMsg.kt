package bot.tx.wsure.top.bililiver.dtos.event.cmd

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RoomBlockMsg(
    @SerialName("dmscore")
    val dmscore: Int,
    @SerialName("operator")
    val `operator`: Int,
    @SerialName("uid")
    val uid: Int,
    @SerialName("uname")
    val uname: String
)