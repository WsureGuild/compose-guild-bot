package bot.tx.wsure.top.utils

import bot.tx.wsure.top.utils.JsonUtils.jsonToObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream
import kotlin.io.path.Path

object FileUtils {
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    inline fun <reified T> String.readResourceJson(): T? {
        return kotlin.runCatching { FileUtils::class.java.classLoader.getResource(this)?.readText()?.jsonToObject<T>() }
            .onFailure {
                logger.warn("Read resource file {} by classloader failure !!", this, it)
            }.getOrNull()
    }

    inline fun <reified T> String.readFileJson(): T? {
        return kotlin.runCatching { File(this).readText().jsonToObject<T>() }
            .onFailure {
                logger.warn("Read file {} by File method failure !!", this, it)
            }.getOrNull()
    }
    fun createFileAndDirectory(file:File,isDir:Boolean = false){
        if(isDir) file.mkdirs()
        else if(!file.parentFile.exists()){
            file.parentFile.mkdirs()
        }
    }

    fun File.copyInputStreamToFile(inputStream: InputStream) {
        this.outputStream().use { fileOut ->
            inputStream.copyTo(fileOut)
        }
    }

    fun copyResource(resourcePath:String){
        val config = Path(resourcePath).toAbsolutePath().toFile()
        if(!config.exists() || config.length() == 0L ){
            val resource = this::class.java.classLoader.getResourceAsStream(resourcePath)
            createFileAndDirectory(config,false)
            if(resource != null){
                config.copyInputStreamToFile(resource)
            }
        }
    }
}
