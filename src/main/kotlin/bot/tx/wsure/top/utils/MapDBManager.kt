package bot.tx.wsure.top.utils

import bot.tx.wsure.top.component.unofficial.YbbTrain
import bot.tx.wsure.top.config.Global.CACHE_PATH
import bot.tx.wsure.top.config.WeiboChatConfig
import bot.tx.wsure.top.spider.dtos.Mblog
import bot.tx.wsure.top.utils.MapDBUtils.getCacheByName
import bot.tx.wsure.top.utils.MapDBUtils.mapDBCborSerializer
import org.mapdb.DBMaker.fileDB
import java.io.File
import java.util.*


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

    val WB_CONFIG: MapDBWarp<String,List<WeiboChatConfig>> = MapDBWarp(db,getCacheByName(db,"WB_CONFIG"))

    val WB_CACHE: MapDBWarp<String,List<Mblog>> = MapDBWarp(db,getCacheByName(db,"WB_CACHE"))

}