package bot.tx.wsure.top.unofficial.dtos.event

import kotlinx.serialization.SerialName

interface BaseEventInterface {
    val guildId: Long

    val channelId: Long

    val userId: Long
}