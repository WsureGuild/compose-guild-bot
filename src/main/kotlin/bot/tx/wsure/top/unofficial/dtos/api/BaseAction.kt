package bot.tx.wsure.top.unofficial.dtos.api

import bot.tx.wsure.top.unofficial.enums.ActionEnums
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class BaseAction<T>(
    val action:ActionEnums,
    val params:T,
    val echo:String = UUID.randomUUID().toString(),
)