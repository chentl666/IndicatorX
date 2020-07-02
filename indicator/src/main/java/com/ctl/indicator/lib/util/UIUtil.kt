package com.ctl.indicator.lib.util

import android.content.Context

/**
 * created by : chentl
 * Date: 2020/07/02
 */
class UIUtil {

    fun dip2px(context: Context, dpValue: Double): Int {
        val density = context.resources.displayMetrics.density
        return (dpValue * density + 0.5).toInt()
    }

    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }
}