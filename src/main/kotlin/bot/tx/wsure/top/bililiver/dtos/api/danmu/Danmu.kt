package bot.tx.wsure.top.bililiver.dtos.api.danmu

data class Danmu(
val msg :String ,
val roomid :String ,
val csrf :String ,
val csrf_token :String = csrf,
val bubble :String ="0",
val color :String ="16738408",
val mode :String ="1",
val fontsize :String ="25",
val rnd :String = System.currentTimeMillis().toString().take(10),
){

}