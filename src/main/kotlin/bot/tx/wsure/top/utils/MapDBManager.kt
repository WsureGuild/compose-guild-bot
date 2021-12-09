package bot.tx.wsure.top.utils

import bot.tx.wsure.top.component.unofficial.YbbTrain
import bot.tx.wsure.top.config.Global.CACHE_PATH
import bot.tx.wsure.top.utils.MapDBUtils.mapDBCborSerializer
import org.mapdb.DBMaker.fileDB


object MapDBManager {
    var db = fileDB(CACHE_PATH)
        .fileMmapEnable()
        .closeOnJvmShutdown()
        .transactionEnable()
        .make()


    val YBB: MapDBWarp<String, MutableMap<String, Long>> = MapDBWarp(db,db
        .hashMap<String,MutableMap<String,Long>>("YBB", mapDBCborSerializer(), mapDBCborSerializer())
        .createOrOpen()
    )
    val YBB_TOP: MapDBWarp<String,MutableMap<String, List<YbbTrain.TopRecord>>> = MapDBWarp(db,db
        .hashMap<String,MutableMap<String, List<YbbTrain.TopRecord>>>("YBB_TOP", mapDBCborSerializer(), mapDBCborSerializer())
        .createOrOpen()
    )

}