package me.jfenn.utils

import android.content.res.Resources

/**
 * Converts dp units to pixels.
 *
 * @param dp            A distance measurement, in dp.
 * @return              The value of the provided dp units, in pixels.
 */
fun dpToPx(dp: Float): Int {
    return (Resources.getSystem().displayMetrics.density * dp).toInt()
}