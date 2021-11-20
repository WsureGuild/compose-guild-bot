package bot.tx.wsure.top.unofficial.dtos.api

import bot.tx.wsure.top.unofficial.enums.ActionEnums
import kotlinx.serialization.Serializable

@Serializable
data class BaseAction<T>(
    val action:ActionEnums,
    val params:T,
    val echo:String? = null,
)