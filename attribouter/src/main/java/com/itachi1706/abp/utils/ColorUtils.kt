package com.itachi1706.abp.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.itachi1706.abp.attribouter.R

/**
 * Determine if a color is readable on a light background, using some magic numbers.
 *
 * @return True if the color should be considered "dark".
 */
fun Int.isColorDark() : Boolean {
    return getColorDarkness(this) >= 0.5
}

/**
 * Negation of Int.isColorDark()
 *
 * @return True if the color should be considered "light".
 */
fun Int.isColorLight() : Boolean {
    return !isColorDark()
}

/**
 * Determine the darkness of a color, using some magic numbers.
 *
 * @param color         A color int to determine the luminance of.
 * @return              The darkness of the color; a double between 0 and 1.
 */
private fun getColorDarkness(@ColorInt color: Int): Double {
    if (color == Color.BLACK) return 1.0 else if (color == Color.WHITE || color == Color.TRANSPARENT) return 0.0
    return 1 - (0.259 * Color.red(color) + 0.667 * Color.green(color) + 0.074 * Color.blue(color)) / 255
}

/**
 * Set light status/navigation bar window flags automatically.
 * Falls back to Color.BLACK on lower SDK versions.
 *
 * For SDK 30 and above, this is handled by the system automatically.
 */
@Suppress("DEPRECATION") // Deprecated for SDK 30 and above, but we still need to support lower SDK versions
fun Window.autoSystemUiColors() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        return // No need to do anything, this is handled by the system
    }
    // handle light status bar colors
    if (statusBarColor.isColorLight()) {
        if (Build.VERSION.SDK_INT >= 23)
            decorView.systemUiVisibility = decorView.systemUiVisibility.or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        else statusBarColor = Color.BLACK
    }

    // handle light nav bar colors
    if (navigationBarColor.isColorLight()) {
        if (Build.VERSION.SDK_INT >= 26)
            decorView.systemUiVisibility = decorView.systemUiVisibility.or(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        else navigationBarColor = Color.BLACK
    }
}

fun Window.addEdgeToEdgeFlags() {
    // Add color
    val statusBarView = View(this.context)
    addContentView(statusBarView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    // Set the status bar color to match the theme
    statusBarView.setBackgroundColor(this.context.getThemedColor(R.attr.attribouter_textColorAccent))

    WindowCompat.setDecorFitsSystemWindows(this, false)
    val rootView = findViewById<View>(android.R.id.content)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
        v.setPadding(insets.left, insets.top, insets.right, insets.bottom)

        statusBarView.updateLayoutParams {
            height = insets.top
        }

        WindowInsetsCompat.CONSUMED
    }
}