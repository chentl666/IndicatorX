package com.ctl.indicator.lib.commonnavigator.titles

import android.animation.ArgbEvaluator
import android.content.Context

/**
 * 两种颜色过渡的指示器标题
 * created by : chentl
 * Date: 2020/07/06
 */
open class ColorTransitionPagerTitleView(context: Context) : SimplePagerTitleView(context) {
    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        val color: Int = ArgbEvaluator().evaluate(leavePercent, mSelectedColor, mNormalColor) as Int
        setTextColor(color)
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        val color: Int = ArgbEvaluator().evaluate(enterPercent, mNormalColor, mSelectedColor) as Int
        setTextColor(color)
    }
}