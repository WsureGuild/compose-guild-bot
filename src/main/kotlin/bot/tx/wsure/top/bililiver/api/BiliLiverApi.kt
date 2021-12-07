package bot.tx.wsure.top.bililiver.api

import bot.tx.wsure.top.bililiver.dtos.api.BiliResponse
import bot.tx.wsure.top.bililiver.dtos.api.danmu.Danmu
import bot.tx.wsure.top.bililiver.dtos.api.danmu.DanmuRes
import bot.tx.wsure.top.bililiver.dtos.api.room.Room
import bot.tx.wsure.top.bililiver.dtos.api.token.TokenAndUrl
import bot.tx.wsure.top.utils.JsonUtils.jsonToObjectOrNull
import bot.tx.wsure.top.utils.JsonUtils.toMap
import bot.tx.wsure.top.utils.OkHttpUtils
import bot.tx.wsure.top.utils.UA
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object BiliLiverApi {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private const val ROOM_INFO = "https://api.live.bilibili.com/room_ex/v1/RoomNews/get?roomid={{room_id}}"

    private const val TOKEN_AND_URL = "https://api.live.bilibili.com/xlive/web-room/v1/index/getDanmuInfo?id={{real_room_id}}&type=0"

    private const val SEND_DANMU = "https://api.live.bilibili.com/msg/send"

    fun getRealRoomId(roomId:String): Room?{
        val url = ROOM_INFO.replace("{{room_id}}", roomId)
        val res = OkHttpUtils.getJson(url, getApiHeader(roomId)).jsonToObjectOrNull<BiliResponse<Room>>()
        logger.info("$roomId getRealRoomId ${if(res != null) "success" else "fail"}")
        return res?.data
    }
    fun getTokenAndUrl(realRoomId:String): TokenAndUrl?{
        val url = TOKEN_AND_URL.replace("{{real_room_id}}", realRoomId)
        val res = OkHttpUtils.getJson(url, mutableMapOf("User-Agent" to UA.PC.getValue()))
            .jsonToObjectOrNull<BiliResponse<TokenAndUrl>>()
        logger.info("$realRoomId getTokenAndUrl ${if(res != null) "success" else "fail"}")
        return res?.data
    }

    fun sendDanmu(danmu: Danmu,cookie:String) : DanmuRes?{
        val res = OkHttpUtils.postStr(SEND_DANMU ,danmu.toMap(),OkHttpUtils.addHeaders(mutableMapOf(
            "User-Agent" to UA.PC.getValue(),
            "Cookie" to cookie
        ))).jsonToObjectOrNull<BiliResponse<DanmuRes>>()
        logger.info("send danmu :${danmu.msg} ${if(res != null) "success" else "fail"}")
        return res?.data
    }

    private fun getApiHeader(roomId:String):Map<String,String>{
        return mutableMapOf(
            "User-Agent" to UA.PC.getValue(),
            "referer" to "https://live.bilibili.com/$roomId"
        )
    }
}