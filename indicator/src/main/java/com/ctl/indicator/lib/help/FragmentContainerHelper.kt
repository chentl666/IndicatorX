package com.ctl.indicator.lib.help

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.viewpager.widget.ViewPager
import com.ctl.indicator.lib.IndicatorX
import com.ctl.indicator.lib.commonnavigator.model.PositionData
import java.util.*

/**
 * 使得IndicatorX在FragmentContainer中使用
 * created by : chentl
 * Date: 2020/07/03
 */
class FragmentContainerHelper {

    private val mIndicators: MutableList<IndicatorX> = ArrayList()
    private var mScrollAnimator: ValueAnimator? = null
    private var mLastSelectedIndex = 0
    private var mDuration = 150
    private var mInterpolator: Interpolator = AccelerateDecelerateInterpolator()

    private val mAnimatorListener: Animator.AnimatorListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            dispatchPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE)
            mScrollAnimator = null
        }
    }

    private val mAnimatorUpdateListener = AnimatorUpdateListener { animation ->
        val positionOffsetSum = animation.animatedValue as Float
        var position = positionOffsetSum.toInt()
        var positionOffset = positionOffsetSum - position
        if (positionOffsetSum < 0) {
            position = position - 1
            positionOffset = 1.0f + positionOffset
        }
        dispatchPageScrolled(position, positionOffset, 0)
    }

    fun FragmentContainerHelper() {}

    fun FragmentContainerHelper(magicIndicator: IndicatorX) {
        mIndicators.add(magicIndicator)
    }

    /**
     * IPagerIndicator支持弹性效果的辅助方法
     *
     * @param positionDataList
     * @param index
     * @return
     */
    companion object{
        fun getImitativePositionData(positionDataList: List<PositionData>, index: Int): PositionData? {
            return if (index >= 0 && index <= positionDataList.size - 1) { // 越界后，返回假的PositionData
                positionDataList[index]
            } else {
                val result = PositionData()
                val referenceData: PositionData
                val offset: Int
                if (index < 0) {
                    offset = index
                    referenceData = positionDataList[0]
                } else {
                    offset = index - positionDataList.size + 1
                    referenceData = positionDataList[positionDataList.size - 1]
                }
                result.mLeft = referenceData.mLeft + offset * referenceData.width()
                result.mTop = referenceData.mTop
                result.mRight = referenceData.mRight + offset * referenceData.width()
                result.mBottom = referenceData.mBottom
                result.mContentLeft = referenceData.mContentLeft + offset * referenceData.width()
                result.mContentTop = referenceData.mContentTop
                result.mContentRight = referenceData.mContentRight + offset * referenceData.width()
                result.mContentBottom = referenceData.mContentBottom
                result
            }
        }
    }

    fun handlePageSelected(selectedIndex: Int) {
        handlePageSelected(selectedIndex, true)
    }

    fun handlePageSelected(selectedIndex: Int, smooth: Boolean) {
        if (mLastSelectedIndex == selectedIndex) {
            return
        }
        if (smooth) {
            if (mScrollAnimator == null || !mScrollAnimator!!.isRunning) {
                dispatchPageScrollStateChanged(ViewPager.SCROLL_STATE_SETTLING)
            }
            dispatchPageSelected(selectedIndex)
            var currentPositionOffsetSum = mLastSelectedIndex.toFloat()
            if (mScrollAnimator != null) {
                currentPositionOffsetSum = mScrollAnimator!!.animatedValue as Float
                mScrollAnimator!!.cancel()
                mScrollAnimator = null
            }
            mScrollAnimator = ValueAnimator()
            mScrollAnimator!!.setFloatValues(
                currentPositionOffsetSum,
                selectedIndex.toFloat()
            ) // position = selectedIndex, positionOffset = 0.0f
            mScrollAnimator!!.addUpdateListener(mAnimatorUpdateListener)
            mScrollAnimator!!.addListener(mAnimatorListener)
            mScrollAnimator!!.interpolator = mInterpolator
            mScrollAnimator!!.duration = mDuration.toLong()
            mScrollAnimator!!.start()
        } else {
            dispatchPageSelected(selectedIndex)
            if (mScrollAnimator != null && mScrollAnimator!!.isRunning) {
                dispatchPageScrolled(mLastSelectedIndex, 0.0f, 0)
            }
            dispatchPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE)
            dispatchPageScrolled(selectedIndex, 0.0f, 0)
        }
        mLastSelectedIndex = selectedIndex
    }

    fun setDuration(duration: Int) {
        mDuration = duration
    }

    fun setInterpolator(interpolator: Interpolator?) {
        mInterpolator = interpolator ?: AccelerateDecelerateInterpolator()
    }

    fun attachIndicatorX(indicator: IndicatorX) {
        mIndicators.add(indicator)
    }

    private fun dispatchPageSelected(pageIndex: Int) {
        for (indicator in mIndicators) {
            indicator.onPageSelected(pageIndex)
        }
    }

    private fun dispatchPageScrollStateChanged(state: Int) {
        for (indicator in mIndicators) {
            indicator.onPageScrollStateChanged(state)
        }
    }

    private fun dispatchPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        for (indicator in mIndicators) {
            indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }
    }
}