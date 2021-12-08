package bot.tx.wsure.top.utils

import bot.tx.wsure.top.utils.MapDBUtils.mapDBCborSerializer
import org.mapdb.DBMaker.fileDB
import org.mapdb.HTreeMap


object MapDBManager {
    var db = fileDB("file.db")
        .fileMmapEnable()
        .closeOnJvmShutdown()
        .transactionEnable()
        .make()


    val YBB: HTreeMap<String, MutableMap<String, Long>> = db
        .hashMap<String,MutableMap<String,Long>>("YBB", mapDBCborSerializer(), mapDBCborSerializer())
        .createOrOpen()

}