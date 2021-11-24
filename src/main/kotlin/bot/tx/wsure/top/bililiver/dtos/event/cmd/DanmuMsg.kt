package bot.tx.wsure.top.bililiver.dtos.event.cmd

import kotlinx.serialization.Serializable

@Serializable
data class DanmuMsg(
    val uname:String,
    val uid:Long,
    val context:String,
    val guardInfo:DanmuGuardInfo?,
) {
    companion object{

        fun String.toDanmuMsg():DanmuMsg?{
            return null
        }
    }
}
@Serializable
data class DanmuGuardInfo(
    val level:Int,
    val name:String,
    val anchorUname:String,
)
