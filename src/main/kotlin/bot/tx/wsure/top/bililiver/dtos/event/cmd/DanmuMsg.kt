package bot.tx.wsure.top.bililiver.dtos.event.cmd

import kotlinx.serialization.Serializable

@Serializable
data class DanmuMsg(
    val context:String,
    val uid:Long,
    val uname:String,
    val color:String,
) {
    companion object{
        val contextRegex = Regex("(?<=],\").+?(?=\",\\[)")
        val uidRegex = Regex("(?<=\",\\[)\\d+")
        val unameRegex = Regex("(?<=\",\\[)\\d+,\".*?(?=\")")
        val colorRegex = Regex("(?<=\"color\\\\\":)\\d+(?=,)")

        fun String.toDanmuMsg():DanmuMsg{
            return DanmuMsg(
                contextRegex.findResult(this),
                uidRegex.findResult(this).toLong(),
                unameRegex.findResult(this).replace(Regex("^.*\""),""),
                colorRegex.findResult(this),
            )
        }
    }
}
fun Regex.findResult(str:String):String{
    return  this.findAll(str).joinToString(""){ it.value }
}