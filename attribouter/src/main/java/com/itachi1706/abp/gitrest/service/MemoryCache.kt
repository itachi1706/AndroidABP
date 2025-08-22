package com.itachi1706.abp.gitrest.service

import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

/**
 * Naive caching implementation for the JVM that uses WeakReference
 * as a cache "buffer" that can be cleaned up by the GC when necessary.
 */
class MemoryCache(
    val underlyingCache: Cache = NoCache
) : Cache {

    private class WeakEntry internal constructor(
        internal val key: String,
        value: Any,
        referenceQueue: ReferenceQueue<Any>) : WeakReference<Any>(value, referenceQueue)

    private val referenceQueue = ReferenceQueue<Any>()
    private val map = HashMap<String, WeakEntry>()

    override suspend fun set(key: String, value: Any) {
        // remove any invalid references
        var weakEntry = referenceQueue.poll() as WeakEntry?
        while (weakEntry != null) {
            map.remove(weakEntry.key)
            weakEntry = referenceQueue.poll() as WeakEntry?
        }

        map[key] = WeakEntry(key, value, referenceQueue)
        underlyingCache.set(key, value)
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> get(id: String): T? {
        val weakEntry = map[id]
        weakEntry?.get()?.let {
            return it as? T
        }

        underlyingCache.get<T>(id)?.let {
            map[id] = WeakEntry(id, it as Any, referenceQueue)
            return it
        }

        return null
    }


}