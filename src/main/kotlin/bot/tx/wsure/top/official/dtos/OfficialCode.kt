package bot.tx.wsure.top.official.dtos

object OfficialCode {
    val removeAt = Regex("<@!\\d+>")
    val findAt = Regex("(?<=<@!)\\d+(?=>)")
    fun String.messageContent():String{
        return this.replace(removeAt,"")
    }
    fun String.toAtOC():String{
        return "<@!$this+>"
    }

    fun String.getOCAt():List<String>{
        return findAt.findAll(this).map { it.value }.toList()
    }

}