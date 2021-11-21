package bot.tx.wsure.top.bililiver.dtos.api
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
open class BiliResponse<T>(
    open val code: Int,
    open val message: String,
    open val msg: String?,
    open val ttl: Int?,
    open val `data` : T
)