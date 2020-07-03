package com.ctl.indicator.lib.commonnavigator.abs

import com.ctl.indicator.lib.commonnavigator.model.PositionData

/**
 * 抽象的viewpager指示器，适用于CommonNavigator
 * created by : chentl
 * Date: 2020/07/02
 */
interface IPagerIndicator {

    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)

    fun onPageSelected(position: Int)

    fun onPageScrollStateChanged(state: Int)

    fun onPositionDataProvide(dataList: List<PositionData>?)
}