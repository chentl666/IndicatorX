package com.ctl.indicator.lib.help

import android.util.SparseArray
import android.util.SparseBooleanArray

/**
 * 方便扩展IPagerNavigator的帮助类，将ViewPager的3个回调方法转换成
 * onSelected、onDeselected、onEnter等回调，方便扩展
 * created by : chentl
 * Date: 2020/07/02
 */
class NavigatorHelper {

    private val mDeselectedItems = SparseBooleanArray()
    private val mLeavedPercents = SparseArray<Float>()

    private var mTotalCount = 0
    private var mCurrentIndex = 0
    private var mLastIndex = 0
    private var mLastPositionOffsetSum = 0f
    private var mScrollState = ScrollState.SCROLL_STATE_IDLE

    private var mSkimOver = false
    private var mNavigatorScrollListener: OnNavigatorScrollListener? = null

    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val currentPositionOffsetSum = position + positionOffset
        var leftToRight = false
        if (mLastPositionOffsetSum <= currentPositionOffsetSum) {
            leftToRight = true
        }
        if (mScrollState != ScrollState.SCROLL_STATE_IDLE) {
            if (currentPositionOffsetSum == mLastPositionOffsetSum) {
                return
            }
            var nextPosition = position + 1
            var normalDispatch = true
            if (positionOffset == 0.0f) {
                if (leftToRight) {
                    nextPosition = position - 1
                    normalDispatch = false
                }
            }
            for (i in 0 until mTotalCount) {
                if (i == position || i == nextPosition) {
                    continue
                }
                val leavedPercent = mLeavedPercents[i, 0.0f]
                if (leavedPercent != 1.0f) {
                    dispatchOnLeave(i, 1.0f, leftToRight, true)
                }
            }
            if (normalDispatch) {
                if (leftToRight) {
                    dispatchOnLeave(position, positionOffset, true, false)
                    dispatchOnEnter(nextPosition, positionOffset, true, false)
                } else {
                    dispatchOnLeave(nextPosition, 1.0f - positionOffset, false, false)
                    dispatchOnEnter(position, 1.0f - positionOffset, false, false)
                }
            } else {
                dispatchOnLeave(nextPosition, 1.0f - positionOffset, true, false)
                dispatchOnEnter(position, 1.0f - positionOffset, true, false)
            }
        } else {
            for (i in 0 until mTotalCount) {
                if (i == mCurrentIndex) {
                    continue
                }
                val deselected = mDeselectedItems[i]
                if (!deselected) {
                    dispatchOnDeselected(i)
                }
                val leavedPercent = mLeavedPercents[i, 0.0f]
                if (leavedPercent != 1.0f) {
                    dispatchOnLeave(i, 1.0f, false, true)
                }
            }
            dispatchOnEnter(mCurrentIndex, 1.0f, false, true)
            dispatchOnSelected(mCurrentIndex)
        }
        mLastPositionOffsetSum = currentPositionOffsetSum
    }

    fun onPageSelected(position: Int) {
        mLastIndex = mCurrentIndex
        mCurrentIndex = position
        dispatchOnSelected(mCurrentIndex)
        for (i in 0 until mTotalCount) {
            if (i == mCurrentIndex) {
                continue
            }
            val deselected = mDeselectedItems[i]
            if (!deselected) {
                dispatchOnDeselected(i)
            }
        }
    }

    fun onPageScrollStateChanged(state: ScrollState) {
        mScrollState = state
    }

    fun setNavigatorScrollListener(navigatorScrollListener: OnNavigatorScrollListener) {
        mNavigatorScrollListener = navigatorScrollListener
    }

    fun setSkimOver(skimOver: Boolean) {
        mSkimOver = skimOver
    }

    fun getTotalCount(): Int {
        return mTotalCount
    }

    fun setTotalCount(totalCount: Int) {
        mTotalCount = totalCount
        mDeselectedItems.clear()
        mLeavedPercents.clear()
    }

    fun getCurrentIndex(): Int {
        return mCurrentIndex
    }

    fun getScrollState(): ScrollState {
        return mScrollState
    }

    private fun dispatchOnEnter(index: Int, enterPercent: Float, leftToRight: Boolean, force: Boolean) {
        if (mSkimOver || index == mCurrentIndex || mScrollState == ScrollState.SCROLL_STATE_DRAGGING || force) {
            mNavigatorScrollListener?.onEnter(index, mTotalCount, enterPercent, leftToRight)
            mLeavedPercents.put(index, 1.0f - enterPercent)
        }
    }

    private fun dispatchOnLeave(index: Int, leavePercent: Float, leftToRight: Boolean, force: Boolean) {
        if (mSkimOver || index == mLastIndex || mScrollState == ScrollState.SCROLL_STATE_DRAGGING || (index == mCurrentIndex - 1 || index == mCurrentIndex + 1) && mLeavedPercents[index, 0.0f] != 1.0f || force) {
            mNavigatorScrollListener?.onLeave(index, mTotalCount, leavePercent, leftToRight)
            mLeavedPercents.put(index, leavePercent)
        }
    }

    private fun dispatchOnSelected(index: Int) {
        mNavigatorScrollListener?.onSelected(index, mTotalCount)
        mDeselectedItems.put(index, false)
    }

    private fun dispatchOnDeselected(index: Int) {
        mNavigatorScrollListener?.onDeselected(index, mTotalCount)
        mDeselectedItems.put(index, true)
    }

    interface OnNavigatorScrollListener {
        fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean)
        fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean)
        fun onSelected(index: Int, totalCount: Int)
        fun onDeselected(index: Int, totalCount: Int)
    }
}