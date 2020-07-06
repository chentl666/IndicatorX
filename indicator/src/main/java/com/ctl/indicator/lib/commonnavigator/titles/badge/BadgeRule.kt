package com.ctl.indicator.lib.commonnavigator.titles.badge

/**
 * 角标的定位规则
 * created by : chentl
 * Date: 2020/07/06
 */
class BadgeRule {
    private var mAnchor: BadgeAnchor? = null
    private var mOffset = 0

    constructor(anchor: BadgeAnchor, offset: Int){
        mAnchor = anchor
        mOffset = offset
    }
    fun getAnchor(): BadgeAnchor? {
        return mAnchor
    }

    fun setAnchor(anchor: BadgeAnchor?) {
        mAnchor = anchor
    }

    fun getOffset(): Int {
        return mOffset
    }

    fun setOffset(offset: Int) {
        mOffset = offset
    }
}