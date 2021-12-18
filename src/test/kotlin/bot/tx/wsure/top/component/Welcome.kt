package bot.tx.wsure.top.component

import top.wsure.bililiver.bililiver.BiliLiverEvent
import top.wsure.bililiver.bililiver.api.BiliLiverApi
import top.wsure.bililiver.bililiver.dtos.api.danmu.Danmu
import top.wsure.bililiver.bililiver.dtos.event.cmd.EntryEffect
import top.wsure.bililiver.bililiver.dtos.event.cmd.InteractWord

class Welcome : BiliLiverEvent(){
    private val cookie = ""
    val jct = ""

    override fun onInteractWord(interactWord: InteractWord) {
        // send welcome
        val msg = "欢迎${interactWord.uname}进入直播间，爹"
        val danmu = Danmu(msg,room.roomid,jct)
        BiliLiverApi.sendDanmu(danmu,cookie)
    }

    override fun onEntryEffect(entryEffect: EntryEffect) {
        val msg = "${entryEffect.copyWriting.replace("欢迎 \\u003c%","").replace("%\\u003e 进入直播间","")}爹你回来啦"
        val danmu = Danmu(msg,room.roomid,jct)
        BiliLiverApi.sendDanmu(danmu,cookie)
    }


}