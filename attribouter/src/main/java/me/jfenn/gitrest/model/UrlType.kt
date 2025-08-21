package me.jfenn.gitrest.model

sealed class UrlType(
    val prefix: String,
    val separator: String
) {

    fun getUrl(hostname: String, path: String) : String {
        return "$prefix$hostname$separator$path"
    }

    object HTTP : UrlType("http://", "/")
    object HTTPS : UrlType("https://", "/")
    object SSH : UrlType("git@", ":")

}