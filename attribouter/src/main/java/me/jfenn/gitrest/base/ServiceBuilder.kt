package me.jfenn.gitrest.base

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import me.jfenn.gitrest.model.GitrestConfig

interface ServiceBuilder<T: RequestProvider> {

    val key : String
    val tokens : MutableMap<String, String>

    fun GitrestConfig.ktor(block: HttpClientConfig<*>.() -> Unit) : HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json()
            }

            block()
        }
    }

    fun create(config: GitrestConfig, hostname: String): T

}