package me.jfenn.gitrest.service

interface Cache {

    suspend fun set(key: String, value: Any)

    suspend fun <T> get(id: String) : T?

}