package bot.tx.wsure.top.official.dtos.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiRes(
    val code:Long?,
    val message:String?,
) {
}