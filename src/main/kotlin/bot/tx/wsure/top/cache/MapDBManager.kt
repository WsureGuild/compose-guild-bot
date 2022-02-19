package bot.tx.wsure.top.cache

import bot.tx.wsure.top.config.ChannelConfig
import bot.tx.wsure.top.config.Global.CACHE_PATH
import bot.tx.wsure.top.spider.dtos.weibo.Mblog
import bot.tx.wsure.top.cache.MapDBUtils.getCacheByName
import bot.tx.wsure.top.component.unofficial.YbbTrainMapDB
import bot.tx.wsure.top.utils.FileUtils
import org.mapdb.DBMaker.fileDB
import top.wsure.bililiver.bililiver.dtos.api.dynamic.DynamicCard
import top.wsure.bililiver.bililiver.dtos.api.room.RoomInfo
import java.io.File


object MapDBManager {
    init {
        FileUtils.createFileAndDirectory(File(CACHE_PATH))
    }
    var db = fileDB(CACHE_PATH)
        .fileMmapEnable()
        .closeOnJvmShutdown()
        .transactionEnable()
        .make()


    val YBB: MapDBWarp<String, MutableMap<String, Long>> = MapDBWarp(db,getCacheByName(db,"YBB"))

    val YBB_TOP: MapDBWarp<String, MutableMap<String, List<YbbTrainMapDB.TopRecord>>> = MapDBWarp(db,getCacheByName(db,"YBB_TOP"))

    val WB_CONFIG: MapDBWarp<String, List<ChannelConfig>> = MapDBWarp(db,getCacheByName(db,"WB_CONFIG"))

    val WB_CACHE: MapDBWarp<String, List<Mblog>> = MapDBWarp(db,getCacheByName(db,"WB_CACHE"))

    val WB_TOP: MapDBWarp<String, List<Mblog>> = MapDBWarp(db,getCacheByName(db,"WB_TOP"))

    val BL_CONFIG: MapDBWarp<String, List<ChannelConfig>> = MapDBWarp(db,getCacheByName(db,"BL_CONFIG"))

    val BL_ROOM_CACHE: MapDBWarp<String, RoomInfo> = MapDBWarp(db,getCacheByName(db,"BL_ROOM_CACHE"))

    val BL_DYNAMIC_CONFIG: MapDBWarp<String, List<ChannelConfig>> = MapDBWarp(db,getCacheByName(db,"BL_DYNAMIC_CONFIG"))

    val BL_DYNAMIC_CACHE: MapDBWarp<String, List<DynamicCard>> = MapDBWarp(db,getCacheByName(db,"BL_DYNAMIC_CACHE"))
}