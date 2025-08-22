package com.itachi1706.abp.attribouter.provider.wedge

import com.itachi1706.abp.attribouter.wedges.Wedge

interface WedgeProvider {

    fun getWedges(parent: Wedge<*>? = null): List<Wedge<*>>
    fun <T> getAttribute(wedge: Wedge<*>, attribute: String, defaultValue: T? = null): T?
    fun  map(map: (WedgeProvider, Wedge<*>) -> Wedge<*>): WedgeProvider

}
