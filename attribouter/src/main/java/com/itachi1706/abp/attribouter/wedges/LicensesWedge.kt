package com.itachi1706.abp.attribouter.wedges

import android.view.View
import com.itachi1706.abp.attribouter.addDefaults

open class LicensesWedge : ListWedge("@string/attribouter_title_licenses", true) {

    var showDefaults: Boolean by Attr("showDefaults", true)

    override fun onCreate() {
        if (showDefaults)
            addDefaults()
    }

    override fun getListItems(): List<Wedge<*>> {
        return getTypedChildren<LicenseWedge>().filter { !it.isHidden }
    }

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

}
