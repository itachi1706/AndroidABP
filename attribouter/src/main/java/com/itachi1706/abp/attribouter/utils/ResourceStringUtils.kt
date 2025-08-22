package com.itachi1706.abp.attribouter.utils

fun String?.isResourceMutable() : Boolean {
    return this?.let {
        !it.startsWith('^')
    } ?: true
}
