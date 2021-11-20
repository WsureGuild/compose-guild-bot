package bot.tx.wsure.top.official.dtos.operation

import bot.tx.wsure.top.official.enums.OPCodeEnums
import kotlinx.serialization.Serializable

@Serializable
open class Operation(
    val op:Int
){
    constructor(op: OPCodeEnums):this(op.code)
    fun type(): OPCodeEnums {
        return OPCodeEnums.getOPCodeByCode(op)
    }
}