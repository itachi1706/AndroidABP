package me.jfenn.gitrest.model

open class License : DelegateResource() {
    open val name: String? = null
    open val description: String? = null
    open val body: String? = null
    open val infoUrl: String? = null
    open val permissions: Array<String>? = null
    open val conditions: Array<String>? = null
    open val limitations: Array<String>? = null
}