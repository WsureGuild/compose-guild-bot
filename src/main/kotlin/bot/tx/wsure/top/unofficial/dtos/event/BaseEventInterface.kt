package bot.tx.wsure.top.unofficial.dtos.event

interface BaseEventInterface {
    val guildId: Long

    val channelId: Long

    val userId: Long
}