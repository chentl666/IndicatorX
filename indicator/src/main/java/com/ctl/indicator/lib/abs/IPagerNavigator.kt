package com.ctl.indicator.lib.abs

/**
 * 抽象的ViewPager导航器
 * created by : chentl
 * Date: 2020/06/30
 */
interface IPagerNavigator {

    //ViewPager的3个回调
    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Float)
    fun onPageSelected(position: Int)
    fun onPageScrollStateChanged(state: Int)

    /**
     * 当IPagerNavigator被添加到MagicIndicator时调用
     */
    fun onAttachToMagicIndicator()

    /**
     * 当IPagerNavigator从MagicIndicator上移除时调用
     */
    fun onDetachFromMagicIndicator()

    /**
     * ViewPager内容改变时需要先调用此方法，自定义的IPagerNavigator应当遵守此约定
     */
    fun notifyDataSetChanged()
}