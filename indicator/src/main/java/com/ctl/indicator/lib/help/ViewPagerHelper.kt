package com.ctl.indicator.lib.help

import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ctl.indicator.lib.IndicatorX

/**
 * 简化和ViewPager绑定
 * created by : chentl
 * Date: 2020/07/02
 */
class ViewPagerHelper {

    companion object {
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

        fun bindViewPager2(indicator: IndicatorX, viewPager2: ViewPager2) {
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    indicator.onPageScrollStateChanged(state)
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    indicator.onPageSelected(position)
                }
            })
        }
    }
}