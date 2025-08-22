package com.itachi1706.abp.attribouter.interfaces

import com.itachi1706.abp.attribouter.wedges.Wedge

fun interface Notifiable {

    fun onItemChanged(changed: Wedge<*>)

}