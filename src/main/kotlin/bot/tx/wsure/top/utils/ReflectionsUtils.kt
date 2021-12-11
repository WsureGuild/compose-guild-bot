package bot.tx.wsure.top.utils

import org.reflections.Reflections
import kotlin.reflect.KClass

object ReflectionsUtils {

    fun <T :Any , R:T> KClass<T>.getAllSubClass():Set<R>{
        val reflections = Reflections(this.java.packageName)
        val allClasses = reflections.getSubTypesOf(this::class.java)
        return allClasses.map { it.kotlin }.mapNotNull { it.objectInstance }.map { it as R }.toSet()
    }
}