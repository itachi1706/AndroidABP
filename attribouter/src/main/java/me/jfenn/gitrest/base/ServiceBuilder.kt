package me.jfenn.gitrest.base

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import me.jfenn.gitrest.model.GitrestConfig

interface ServiceBuilder<T: RequestProvider> {

    val key : String
    val tokens : MutableMap<String, String>

    fun GitrestConfig.ktor(block: HttpClientConfig<*>.() -> Unit) : HttpClient {
        return HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(jsonSerializer)
            }

            block()
        }
    }

    fun create(config: GitrestConfig, hostname: String): T

}