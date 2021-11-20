package bot.tx.wsure.top.official.dtos

import bot.tx.wsure.top.official.dtos.operation.Operation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/*
    OP CODE DTOS
 */
// OP

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

@Serializable
data class User(
    @SerialName("bot")
    val bot: Boolean,
    @SerialName("id")
    val id: String,
    @SerialName("username")
    val username: String
)


// OP Identify
@Serializable
data class IdentifyOpDto(
    @SerialName("d")
    val d: IdentifyOpDtoData,
): Operation(op = 2){
    constructor(token: String):this(IdentifyOpDtoData(token = token))
}

//

@Serializable
data class IdentifyOpDtoData(
    @SerialName("intents")
    val intents: Long = 1610612739L,
    @SerialName("properties")
    val properties: Properties = Properties(),
    @SerialName("shard")
    val shard: List<Int> = listOf(0,1),
    @SerialName("token")
    val token: String
){
}

@Serializable
data class Properties(
    @SerialName("\$browser")
    val browser: String = "chrome",
    @SerialName("\$device")
    val device: String = "RTX 3090",
    @SerialName("\$os")
    val os: String = "linux"
)


// GuildAtMessage  ---- start


@Serializable
data class Author(
    @SerialName("avatar")
    val avatar: String? = null,
    @SerialName("bot")
    val bot: Boolean? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("username")
    val username: String? = null
)

@Serializable
data class Member(
    @SerialName("joined_at")
    val joinedAt: String? = null,
    @SerialName("roles")
    val roles: List<String>? = null
)

@Serializable
data class Mention(
    @SerialName("avatar")
    val avatar: String? = null,
    @SerialName("bot")
    val bot: Boolean? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("username")
    val username: String? = null
)
// GuildAtMessage  ---- end


//api
// rols
