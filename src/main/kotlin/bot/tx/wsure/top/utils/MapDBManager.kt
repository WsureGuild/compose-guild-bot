package bot.tx.wsure.top.utils

import bot.tx.wsure.top.component.unofficial.YbbTrain
import bot.tx.wsure.top.config.ChannelConfig
import bot.tx.wsure.top.config.Global.CACHE_PATH
import bot.tx.wsure.top.spider.dtos.weibo.Mblog
import bot.tx.wsure.top.utils.MapDBUtils.getCacheByName
import org.mapdb.DBMaker.fileDB
import top.wsure.bililiver.bililiver.dtos.api.space.LiveRoom
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

    val YBB_TOP: MapDBWarp<String,MutableMap<String, List<YbbTrain.TopRecord>>> = MapDBWarp(db,getCacheByName(db,"YBB_TOP"))

    val WB_CONFIG: MapDBWarp<String,List<ChannelConfig>> = MapDBWarp(db,getCacheByName(db,"WB_CONFIG"))

    val WB_CACHE: MapDBWarp<String,List<Mblog>> = MapDBWarp(db,getCacheByName(db,"WB_CACHE"))

    val WB_TOP: MapDBWarp<String,List<Mblog>> = MapDBWarp(db,getCacheByName(db,"WB_TOP"))

    val BL_CONFIG: MapDBWarp<String,List<ChannelConfig>> = MapDBWarp(db,getCacheByName(db,"BL_CONFIG"))

    val BL_CACHE: MapDBWarp<String, LiveRoom> = MapDBWarp(db,getCacheByName(db,"BL_CACHE"))
}