package com.ctl.indicator.lib.commonnavigator.abs

/**
 * 可测量内容区域的指示器标题
 * created by : chentl
 * Date: 2020/07/03
 */
interface IMeasurablePagerTitleView : IPagerTitleView {
    fun getContentLeft(): Int

    fun getContentTop(): Int

    fun getContentRight(): Int

    fun getContentBottom(): Int
}