package bot.tx.wsure.top.official.dtos.operation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class DispatchDto(
    @SerialName("s")
    val s: Long,
    @SerialName("t")
    val type: DispatchEnums
): Operation(0)

@Serializable
enum class DispatchEnums{
    READY,
    GUILD_MEMBER_ADD,
    GUILD_MEMBER_UPDATE,
    GUILD_MEMBER_REMOVE,
    CHANNEL_CREATE,
    CHANNEL_UPDATE,
    CHANNEL_DELETE,
    DIRECT_MESSAGE_CREATE,
    AT_MESSAGE_CREATE,
    AUDIO_START,           // 音频开始播放时
    AUDIO_FINISH,          // 音频播放结束时
    AUDIO_ON_MIC,          // 上麦时
    AUDIO_OFF_MIC,          // 下麦时
}