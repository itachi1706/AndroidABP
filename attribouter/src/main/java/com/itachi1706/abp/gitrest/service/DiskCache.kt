package com.itachi1706.abp.gitrest.service

import android.util.Log
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer
import kotlinx.serialization.serializerOrNull
import com.itachi1706.abp.gitrest.model.GitrestConfig
import com.itachi1706.abp.gitrest.provider.gitea.model.GiteaUser
import java.io.File
import java.io.IOException

/**
 * Naive caching implementation for the JVM that uses WeakReference
 * as a cache "buffer" that can be cleaned up by the GC when necessary.
 */
class DiskCache(
    val config: GitrestConfig,
    appCacheDir: File,
    val cacheDuration: Long = 864000000 // cache for ~10 days by default
) : Cache {

    val cacheDir = File(appCacheDir, "http")

    fun String.cacheFile() = File(cacheDir, "${this.replace(File.separator, "_")}.json")

    @InternalSerializationApi
    override suspend fun set(key: String, value: Any) {
        // obtain a serializer + type for the value (this is all just a ridiculous hack)
        val serializer: KSerializer<Any>
        val typeName: String
        if (value is List<*>) {
            // GiteaUser is just a default list hack for when the list is empty; it's never actually serialized in this case
            val type = value.firstOrNull()?.let { it::class } ?: GiteaUser::class
            serializer = ListSerializer(type.serializer()) as KSerializer<Any>
            typeName = "list:${type.java.name}"
        } else {
            serializer = value::class.serializerOrNull() as KSerializer<Any>
            typeName = value.javaClass.name
        }

        // add type + expiration metadata before serializing
        val string = typeName + "#" + System.currentTimeMillis() + "#" + config.jsonSerializer.encodeToString(serializer, value)

        try {
            cacheDir.mkdirs()
            key.cacheFile().writeText(string)
        } catch (e: IOException) {
            config.logError("GIT-REST: ${e::class.simpleName} - ${e.message}")
        }
    }

    @InternalSerializationApi
    override suspend fun <T> get(key: String): T? {
        return try {
            // check contents; destructure file parts if safe
            val fileContents = key.cacheFile().readText().split("#", limit = 3)
            if (fileContents.size != 3) return null
            val (className, lastModified, json) = fileContents

            // obtain the correct serializer for {className}
            Log.d("GIT-REST", "DiskCache: Deserializing $key with class $className")
            val serializer = if (className.startsWith("list:"))
                ListSerializer(Class.forName(className.substring(5)).kotlin.serializer()) as KSerializer<Any>
            else Class.forName(className).kotlin.serializer() as KSerializer<Any>

            // parse JSON if before expiry date; else return null for default behavior (fetch the actual request)
            if (System.currentTimeMillis() - lastModified.toLong() < cacheDuration) {
                config.jsonSerializer.decodeFromString(serializer, json) as? T
            } else null
        } catch (e : IOException) {
            config.logError("GIT-REST: ${e::class.simpleName} - ${e.message}")
            null
        }
    }

}