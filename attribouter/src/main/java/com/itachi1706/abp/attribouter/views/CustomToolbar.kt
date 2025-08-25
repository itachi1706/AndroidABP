package com.itachi1706.abp.attribouter.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.android.material.appbar.MaterialToolbar
import com.itachi1706.abp.attribouter.R
import com.itachi1706.abp.utils.getThemedColor
import com.itachi1706.abp.utils.isColorLight

class CustomToolbar : MaterialToolbar {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    fun init() {
        val color = if (context.getThemedColor(androidx.appcompat.R.attr.colorPrimary)
                .isColorLight()
        ) Color.BLACK else Color.WHITE
        setTitleTextColor(color)

        navigationIcon = VectorDrawableCompat.create(
            resources,
            R.drawable.attribouter_ic_arrow_back,
            context.theme
        )?.let {
            DrawableCompat.wrap(it)
        }?.apply {
            colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                color,
                BlendModeCompat.SRC_ATOP
            )
        }
    }

}