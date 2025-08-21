package me.jfenn.gitrest.model

open class User : DelegateResource() {
    open val name: String? = null
    open val url: String? = null
    open val avatarUrl: String? = null
    open val websiteUrl: String? = null
    open val email: String? = null
    open val bio: String? = null
}