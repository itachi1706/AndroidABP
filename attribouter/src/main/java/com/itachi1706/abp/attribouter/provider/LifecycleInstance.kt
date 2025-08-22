package com.itachi1706.abp.attribouter.provider

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.itachi1706.abp.attribouter.BuildConfig
import com.itachi1706.abp.attribouter.interfaces.Notifiable
import com.itachi1706.abp.attribouter.wedges.Wedge
import com.itachi1706.abp.gitrest.base.ServiceBuilder
import com.itachi1706.abp.gitrest.gitrest
import com.itachi1706.abp.gitrest.service.DiskCache
import com.itachi1706.abp.gitrest.service.MemoryCache

class LifecycleInstance(
    context: Context,
    providers: List<ServiceBuilder<*>>,
    val scope: LifecycleCoroutineScope? = null,
    val notifiable: Notifiable? = null
) {

    val client = gitrest {
        this.providers = providers.toTypedArray()
        cache = MemoryCache(DiskCache(this, context.cacheDir))
        logDebug = {
            if (BuildConfig.DEBUG) Log.d("gitrest", it)
        }
        logError = {
            if (BuildConfig.DEBUG) Log.e("gitrest", it)
        }
    }

    fun notifyItemChanged(wedge: Wedge<*>) {
        notifiable?.onItemChanged(wedge)
    }

    fun launch(routine: suspend CoroutineScope.() -> Unit) {
        (scope ?: GlobalScope).launch(block = routine)
    }

}