package me.jfenn.gitrest.service

object NoCache : Cache {
    override suspend fun set(key: String, value: Any) {}
    override suspend fun <T> get(id: String): T? = null
}