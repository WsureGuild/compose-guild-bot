package bot.tx.wsure.top.component.bililiver

import bot.tx.wsure.top.bililiver.BiliLiverEvent
import bot.tx.wsure.top.bililiver.api.BiliLiverApi
import bot.tx.wsure.top.bililiver.dtos.api.danmu.Danmu
import bot.tx.wsure.top.bililiver.dtos.event.cmd.EntryEffect
import bot.tx.wsure.top.bililiver.dtos.event.cmd.InteractWord

class Welcome :BiliLiverEvent(){
    private val cookie = "_uuid=D794C974-47BF-9A9B-4243-9C189833C10627935infoc; buvid3=C0D3E559-71E9-4BA1-B2B1-4ADC88EBF05F143110infoc; rpdid=|(u~||Ylku)J0J'uY|mYmk|~k; buvid_fp=C0D3E559-71E9-4BA1-B2B1-4ADC88EBF05F143110infoc; buvid_fp_plain=C0D3E559-71E9-4BA1-B2B1-4ADC88EBF05F143110infoc; LIVE_BUVID=AUTO7816121051044024; _ga=GA1.2.469293574.1613318937; blackside_state=1; SESSDATA=8bd4e47a%2C1640888468%2Cef9ac%2A71; bili_jct=91c3fad9939d4b4453eea0d408a6375f; DedeUserID=777656; DedeUserID__ckMd5=d534dd78700f7ebd; sid=5cnbomel; _gid=GA1.2.673822273.1636370981; video_page_version=v_old_home; fingerprint3=d692048718f1afa4a2b040acff11bd7d; fingerprint=c8093926e87d229ac166cb3fbf7e6bd6; fingerprint_s=1aa2b7439ac6967808d3c9743458fb11; i-wanna-go-back=1; b_ut=6; Hm_lvt_8a6e55dbd2870f0f5bc9194cddf32a02=1637591802,1637932095,1638290793,1638450438; Hm_lpvt_8a6e55dbd2870f0f5bc9194cddf32a02=1638450438; CURRENT_QUALITY=80; CURRENT_FNVAL=2000; innersign=0; dy_spec_agreed=1; bp_video_offset_777656=600323199761085388; bp_t_offset_777656=600324307861608561; _dfcaptcha=abb955b5579201435bfff6ee2e20242c; b_lsid=67667C17_17D84F8AB38; _gat=1; PVID=5"
    val jct = "91c3fad9939d4b4453eea0d408a6375f"

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