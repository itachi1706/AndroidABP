package com.itachi1706.abp.gitrest.base

import com.itachi1706.abp.gitrest.model.GitrestConfig
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface ServiceBuilder<T: RequestProvider> {

    val key : String
    val tokens : MutableMap<String, String>

    fun GitrestConfig.ktor(block: HttpClientConfig<*>.() -> Unit) : HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json{ignoreUnknownKeys = true})
            }

            block()
        }
    }

    fun create(config: GitrestConfig, hostname: String): T

}