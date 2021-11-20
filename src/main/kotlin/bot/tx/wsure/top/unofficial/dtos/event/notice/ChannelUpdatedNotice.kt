package bot.tx.wsure.top.unofficial.dtos.event.notice
import bot.tx.wsure.top.unofficial.dtos.event.BaseEventDto
import bot.tx.wsure.top.unofficial.dtos.event.BaseEventInterface
import bot.tx.wsure.top.unofficial.enums.PostTypeEnum
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class ChannelUpdatedNotice(
    @SerialName("channel_id")
    override val channelId: Long,
    @SerialName("guild_id")
    override val guildId: Long,
    @SerialName("user_id")
    override val userId: Long,

    @SerialName("new_info")
    val newInfo: ChannelInfo,
    @SerialName("notice_type")
    val noticeType: String,
    @SerialName("old_info")
    val oldInfo: ChannelInfo,
    @SerialName("operator_id")
    val operatorId: Long,
    @SerialName("self_id")
    val selfId: Long,
    @SerialName("self_tiny_id")
    val selfTinyId: Long,
    @SerialName("time")
    val time: Long,
): BaseEventDto(PostTypeEnum.NOTICE),BaseEventInterface

@Serializable
data class ChannelInfo(
    @SerialName("channel_id")
    val channelId: Long,
    @SerialName("channel_name")
    val channelName: String,
    @SerialName("channel_type")
    val channelType: Long,
    @SerialName("create_time")
    val createTime: Long,
    @SerialName("creator_id")
    val creatorId: Long,
    @SerialName("creator_tiny_id")
    val creatorTinyId: Long,
    @SerialName("current_slow_mode")
    val currentSlowMode: Long,
    @SerialName("owner_guild_id")
    val ownerGuildId: Long,
    @SerialName("slow_modes")
    val slowModeInfos: List<SlowModeInfo>,
    @SerialName("talk_permission")
    val talkPermission: Long,
    @SerialName("visible_type")
    val visibleType: Long
)

@Serializable
data class SlowModeInfo(
    @SerialName("slow_mode_circle")
    val slowModeCircle: Long,
    @SerialName("slow_mode_key")
    val slowModeKey: Long,
    @SerialName("slow_mode_text")
    val slowModeText: String,
    @SerialName("speak_frequency")
    val speakFrequency: Long
)