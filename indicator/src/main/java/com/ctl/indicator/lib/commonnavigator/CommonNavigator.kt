package com.ctl.indicator.lib.commonnavigator

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.ctl.indicator.lib.R
import com.ctl.indicator.lib.abs.IPagerNavigator
import com.ctl.indicator.lib.commonnavigator.abs.CommonNavigatorAdapter
import com.ctl.indicator.lib.commonnavigator.abs.IMeasurablePagerTitleView
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerTitleView
import com.ctl.indicator.lib.commonnavigator.model.PositionData
import com.ctl.indicator.lib.help.NavigatorHelper
import kotlinx.android.synthetic.main.pager_navigator_layout.view.*
import java.util.*
import kotlin.math.min

/**
 * 通用的ViewPager指示器，包含PagerTitle和PagerIndicator
 * created by : chentl
 * Date: 2020/07/02
 */
class CommonNavigator(context: Context) : FrameLayout(context), IPagerNavigator, NavigatorHelper.OnNavigatorScrollListener {

    private var mScrollView: HorizontalScrollView? = null
    private var mTitleContainer: LinearLayout? = null
    private var mIndicatorContainer: LinearLayout? = null
    private var mIndicator: IPagerIndicator? = null
    private var mAdapter: CommonNavigatorAdapter? = null
    private val mNavigatorHelper: NavigatorHelper = NavigatorHelper()

    // 保存每个title的位置信息，为扩展indicator提供保障
    private val mPositionDataList: MutableList<PositionData> = ArrayList()

    /**
     * 提供给外部的参数配置
     */
    private var mAdjustMode = false // 自适应模式，适用于数目固定的、少量的title
    private var mEnablePivotScroll = false // 启动中心点滚动
    private var mScrollPivotX = 0.5f // 滚动中心点 0.0f - 1.0f
    private var mSmoothScroll = true // 是否平滑滚动，适用于 !mAdjustMode && !mFollowTouch
    private var mFollowTouch = true // 是否手指跟随滚动
    private var mRightPadding = 0
    private var mLeftPadding = 0
    private var mIndicatorOnTop = false// 指示器是否在title上层，默认为下层
    private var mSkimOver = false// 跨多页切换时，中间页是否显示 "掠过" 效果
    private var mReselectWhenLayout = true // PositionData准备好时，是否重新选中当前页，为true可保证在极端情况下指示器状态正确

    init {
        mNavigatorHelper.setNavigatorScrollListener(this)
    }

    private val mObserver: DataSetObserver = object : DataSetObserver() {
        override fun onChanged() {
            mNavigatorHelper.setTotalCount(mAdapter!!.getCount()) // 如果使用helper，应始终保证helper中的totalCount为最新
            init()
        }

        override fun onInvalidated() {
            // 没什么用，暂不做处理
        }
    }

    private fun init() {
        removeAllViews()
        val root: View = if (mAdjustMode) {
            LayoutInflater.from(context).inflate(R.layout.pager_navigator_layout_no_scroll, this)
        } else {
            LayoutInflater.from(context).inflate(R.layout.pager_navigator_layout, this)
        }
        mScrollView = root.scroll_view  // mAdjustMode为true时，mScrollView为null
        mTitleContainer = root.title_container
        mTitleContainer!!.setPadding(mLeftPadding, 0, mRightPadding, 0)
        mIndicatorContainer = root.indicator_container
        if (mIndicatorOnTop) {
            mIndicatorContainer!!.parent.bringChildToFront(mIndicatorContainer)
        }
        initTitlesAndIndicator()
    }

    /**
     * 初始化title和indicator
     */
    private fun initTitlesAndIndicator() {
        var i = 0
        val j = mNavigatorHelper.getTotalCount()
        while (i < j) {
            val v: IPagerTitleView? = mAdapter!!.getTitleView(context, i)
            if (v is View) {
                val view = v as View?
                var lp: LinearLayout.LayoutParams
                if (mAdjustMode) {
                    lp = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
                    lp.weight = mAdapter!!.getTitleWeight(context, i)
                } else {
                    lp = LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT
                    )
                }
                mTitleContainer!!.addView(view, lp)
            }
            i++
        }
        if (mAdapter != null) {
            mIndicator = mAdapter!!.getIndicator(context)
            if (mIndicator is View) {
                val lp = LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                mIndicatorContainer!!.addView(mIndicator as View?, lp)
            }
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (mAdapter != null) {
            preparePositionData()
            mIndicator?.onPositionDataProvide(mPositionDataList)
            if (mReselectWhenLayout && mNavigatorHelper.getScrollState() == ViewPager.SCROLL_STATE_IDLE) {
                onPageSelected(mNavigatorHelper.getCurrentIndex())
                onPageScrolled(mNavigatorHelper.getCurrentIndex(), 0f, 0f)
            }
        }
    }

    /**
     * 获取title的位置信息，为打造不同的指示器、各种效果提供可能
     */
    private fun preparePositionData() {
        mPositionDataList.clear()
        var i = 0
        val j = mNavigatorHelper.getTotalCount()
        while (i < j) {
            val data = PositionData()
            val v = mTitleContainer!!.getChildAt(i)
            if (v != null) {
                data.mLeft = v.left
                data.mTop = v.top
                data.mRight = v.right
                data.mBottom = v.bottom
                if (v is IMeasurablePagerTitleView) {
                    val view: IMeasurablePagerTitleView = v
                    data.mContentLeft = view.getContentLeft()
                    data.mContentTop = view.getContentTop()
                    data.mContentRight = view.getContentRight()
                    data.mContentBottom = view.getContentBottom()
                } else {
                    data.mContentLeft = data.mLeft
                    data.mContentTop = data.mTop
                    data.mContentRight = data.mRight
                    data.mContentBottom = data.mBottom
                }
            }
            mPositionDataList.add(data)
            i++
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Float) {
        if (mAdapter != null) {
            mNavigatorHelper.onPageScrolled(position, positionOffset, positionOffsetPixels)
            mIndicator?.onPageScrolled(position, positionOffset, positionOffsetPixels)

            // 手指跟随滚动
            if (mScrollView != null && mPositionDataList.size > 0 && position >= 0 && position < mPositionDataList.size) {
                if (mFollowTouch) {
                    val currentPosition = min(mPositionDataList.size - 1, position)
                    val nextPosition = min(mPositionDataList.size - 1, position + 1)
                    val current = mPositionDataList[currentPosition]
                    val next = mPositionDataList[nextPosition]
                    val scrollTo = current.horizontalCenter() - mScrollView!!.width * mScrollPivotX
                    val nextScrollTo = next.horizontalCenter() - mScrollView!!.width * mScrollPivotX
                    mScrollView!!.scrollTo(
                        (scrollTo + (nextScrollTo - scrollTo) * positionOffset).toInt(),
                        0
                    )
                }
//                else if (!mEnablePivotScroll) {
//                    // TODO 实现待选中项完全显示出来
//                }
            }
        }
    }

    override fun onPageSelected(position: Int) {
        if (mAdapter != null) {
            mNavigatorHelper.onPageSelected(position)
            mIndicator?.onPageSelected(position)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (mAdapter != null) {
            mNavigatorHelper.onPageScrollStateChanged(state)
            mIndicator?.onPageScrollStateChanged(state)
        }
    }

    override fun onAttachToIndicatorX() {
        init() // 将初始化延迟到这里
    }

    override fun onDetachFromIndicatorX() {
    }

    override fun notifyDataSetChanged() {
        mAdapter?.notifyDataSetChanged()
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        if (mTitleContainer == null) {
            return
        }
        val v = mTitleContainer!!.getChildAt(index)
        if (v is IPagerTitleView) {
            (v as IPagerTitleView).onEnter(index, totalCount, enterPercent, leftToRight)
        }
    }

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        if (mTitleContainer == null) {
            return
        }
        val v = mTitleContainer!!.getChildAt(index)
        if (v is IPagerTitleView) {
            (v as IPagerTitleView).onLeave(index, totalCount, leavePercent, leftToRight)
        }
    }

    override fun onSelected(index: Int, totalCount: Int) {
        if (mTitleContainer == null) {
            return
        }
        val v = mTitleContainer!!.getChildAt(index)
        if (v is IPagerTitleView) {
            (v as IPagerTitleView).onSelected(index, totalCount)
        }
        if (!mAdjustMode && !mFollowTouch && mScrollView != null && mPositionDataList.size > 0) {
            val currentIndex = min(mPositionDataList.size - 1, index)
            val current = mPositionDataList[currentIndex]
            if (mEnablePivotScroll) {
                val scrollTo =
                    current.horizontalCenter() - mScrollView!!.width * mScrollPivotX
                if (mSmoothScroll) {
                    mScrollView!!.smoothScrollTo(scrollTo.toInt(), 0)
                } else {
                    mScrollView!!.scrollTo(scrollTo.toInt(), 0)
                }
            } else {
                // 如果当前项被部分遮挡，则滚动显示完全
                if (mScrollView!!.scrollX > current.mLeft) {
                    if (mSmoothScroll) {
                        mScrollView!!.smoothScrollTo(current.mLeft, 0)
                    } else {
                        mScrollView!!.scrollTo(current.mLeft, 0)
                    }
                } else if (mScrollView!!.scrollX + width < current.mRight) {
                    if (mSmoothScroll) {
                        mScrollView!!.smoothScrollTo(current.mRight - width, 0)
                    } else {
                        mScrollView!!.scrollTo(current.mRight - width, 0)
                    }
                }
            }
        }
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        if (mTitleContainer == null) {
            return
        }
        val v = mTitleContainer!!.getChildAt(index)
        if (v is IPagerTitleView) {
            (v as IPagerTitleView).onDeselected(index, totalCount)
        }
    }

    fun isAdjustMode(): Boolean {
        return mAdjustMode
    }

    fun setAdjustMode(adjustMode: Boolean) {
        mAdjustMode = adjustMode
    }

    fun getAdapter(): CommonNavigatorAdapter? {
        return mAdapter
    }

    fun setAdapter(adapter: CommonNavigatorAdapter) {
        if (mAdapter === adapter) {
            return
        }
        mAdapter?.unregisterDataSetObserver(mObserver)
        mAdapter = adapter
        if (mAdapter != null) {
            mAdapter!!.registerDataSetObserver(mObserver)
            mNavigatorHelper.setTotalCount(mAdapter!!.getCount())
            if (mTitleContainer != null) {  // adapter改变时，应该重新init，但是第一次设置adapter不用，onAttachToMagicIndicator中有init
                mAdapter!!.notifyDataSetChanged()
            }
        } else {
            mNavigatorHelper.setTotalCount(0)
            init()
        }
    }

    fun getScrollPivotX(): Float {
        return mScrollPivotX
    }

    fun setScrollPivotX(scrollPivotX: Float) {
        mScrollPivotX = scrollPivotX
    }

    fun getPagerIndicator(): IPagerIndicator? {
        return mIndicator
    }

    fun isEnablePivotScroll(): Boolean {
        return mEnablePivotScroll
    }

    fun setEnablePivotScroll(enablePivotScroll: Boolean) {
        mEnablePivotScroll = enablePivotScroll
    }

    fun isSmoothScroll(): Boolean {
        return mSmoothScroll
    }

    fun setSmoothScroll(smoothScroll: Boolean) {
        mSmoothScroll = smoothScroll
    }

    fun isFollowTouch(): Boolean {
        return mFollowTouch
    }

    fun setFollowTouch(followTouch: Boolean) {
        mFollowTouch = followTouch
    }

    fun isSkimOver(): Boolean {
        return mSkimOver
    }

    fun setSkimOver(skimOver: Boolean) {
        mSkimOver = skimOver
        mNavigatorHelper.setSkimOver(skimOver)
    }

    fun getPagerTitleView(index: Int): IPagerTitleView? {
        return if (mTitleContainer == null) {
            null
        } else mTitleContainer!!.getChildAt(index) as IPagerTitleView
    }

    fun getTitleContainer(): LinearLayout? {
        return mTitleContainer
    }

    fun getRightPadding(): Int {
        return mRightPadding
    }

    fun setRightPadding(rightPadding: Int) {
        mRightPadding = rightPadding
    }

    fun getLeftPadding(): Int {
        return mLeftPadding
    }

    fun setLeftPadding(leftPadding: Int) {
        mLeftPadding = leftPadding
    }

    fun isIndicatorOnTop(): Boolean {
        return mIndicatorOnTop
    }

    fun setIndicatorOnTop(indicatorOnTop: Boolean) {
        mIndicatorOnTop = indicatorOnTop
    }

    fun isReselectWhenLayout(): Boolean {
        return mReselectWhenLayout
    }

    fun setReselectWhenLayout(reselectWhenLayout: Boolean) {
        mReselectWhenLayout = reselectWhenLayout
    }
}