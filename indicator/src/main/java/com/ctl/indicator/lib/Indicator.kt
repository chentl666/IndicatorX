package com.ctl.indicator.lib

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.ctl.indicator.lib.abs.IPagerNavigator

/**
 * 整个框架的入口，核心
 * created by : chentl
 * Date: 2020/06/30
 */
class Indicator(context: Context) : FrameLayout(context) {

    private var mNavigator: IPagerNavigator? = null

    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mNavigator?.onPageScrolled(position, positionOffset, positionOffsetPixels.toFloat())
    }

    fun onPageSelected(position: Int) {
        mNavigator?.onPageSelected(position)
    }

    fun onPageScrollStateChanged(state: Int) {
        mNavigator?.onPageScrollStateChanged(state)
    }

    fun getNavigator(): IPagerNavigator? {
        return mNavigator
    }

    fun setNavigator(navigator: IPagerNavigator) {
        if (mNavigator === navigator) {
            return
        }
        mNavigator?.onDetachFromMagicIndicator()
        mNavigator = navigator
        removeAllViews()
        if (mNavigator is View) {
            val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            addView(mNavigator as View?, lp)
            mNavigator!!.onAttachToMagicIndicator()
        }
    }
}