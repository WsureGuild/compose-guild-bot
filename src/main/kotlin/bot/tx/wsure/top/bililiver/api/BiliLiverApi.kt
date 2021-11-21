package bot.tx.wsure.top.bililiver.api

import bot.tx.wsure.top.utils.JsonUtils.jsonToObjectOrNull
import bot.tx.wsure.top.utils.OkHttpUtils
import bot.tx.wsure.top.utils.UA
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object BiliLiverApi {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private const val ROOM_INFO = "https://api.live.bilibili.com/room_ex/v1/RoomNews/get?roomid={{room_id}}"

    fun getRealRoomId(roomId:String):Room?{
        val url = ROOM_INFO.replace("{{room_id}}",roomId.toString())
        val res = OkHttpUtils.getJson(url, getApiHeader(roomId)).jsonToObjectOrNull<SuccessRoomInfo>()
        logger.info("$roomId getRealRoomId ${if(res != null) "success" else "fail"}")
        return res?.data
    }

    private fun getApiHeader(roomId:String):Map<String,String>{
        return mutableMapOf(
            "User-Agent" to UA.PC.getValue(),
            "referer" to "https://live.bilibili.com/$roomId"
        )
    }
}