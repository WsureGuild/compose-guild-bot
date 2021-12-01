package bot.tx.wsure.top.bililiver.dtos.event.cmd

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PkBattleProcess(
    @SerialName("cmd")
    val cmd: String,
    @SerialName("data")
    val `data`: Data,
    @SerialName("pk_id")
    val pkId: Int,
    @SerialName("pk_status")
    val pkStatus: Int,
    @SerialName("timestamp")
    val timestamp: Int
)

@Serializable
data class Data(
    @SerialName("battle_type")
    val battleType: Int,
    @SerialName("init_info")
    val initInfo: MatchInfo,
    @SerialName("match_info")
    val matchInfo: MatchInfo
)

@Serializable
data class MatchInfo(
    @SerialName("best_uname")
    val bestUname: String,
    @SerialName("room_id")
    val roomId: Int,
    @SerialName("vision_desc")
    val visionDesc: Int,
    @SerialName("votes")
    val votes: Int
)