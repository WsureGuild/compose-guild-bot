package bot.tx.wsure.top.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeUtils {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun todayString(): String {
        return LocalDateTime.now().format(formatter)
    }
}