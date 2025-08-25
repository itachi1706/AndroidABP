package com.itachi1706.abp.gitrest.service

object NoCache : Cache {
    override suspend fun set(key: String, value: Any) {
        // No operation
    }
    override suspend fun <T> get(id: String): T? = null
}