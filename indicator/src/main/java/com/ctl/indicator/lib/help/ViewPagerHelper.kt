package com.ctl.indicator.lib.help

import androidx.viewpager.widget.ViewPager
import com.ctl.indicator.lib.IndicatorX

/**
 * 简化和ViewPager绑定
 * created by : chentl
 * Date: 2020/07/02
 */
class ViewPagerHelper {

    fun bind(indicator: IndicatorX, viewPager: ViewPager) {

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                indicator.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                indicator.onPageSelected(position)
            }
        })
    }
}