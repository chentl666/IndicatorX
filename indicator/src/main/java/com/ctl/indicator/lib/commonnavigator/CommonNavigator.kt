package com.ctl.indicator.lib.commonnavigator

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.ctl.indicator.lib.R
import com.ctl.indicator.lib.abs.IPagerNavigator
import com.ctl.indicator.lib.commonnavigator.abs.CommonNavigatorAdapter
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerTitleView
import com.ctl.indicator.lib.commonnavigator.model.PositionData
import com.ctl.indicator.lib.help.NavigatorHelper
import kotlinx.android.synthetic.main.pager_navigator_layout.view.*
import java.util.*

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
    private val mAdapter: CommonNavigatorAdapter? = null
    private val mNavigatorHelper: NavigatorHelper = NavigatorHelper()

    // 保存每个title的位置信息，为扩展indicator提供保障
    private val mPositionDataList: List<PositionData> = ArrayList<PositionData>()
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
        mScrollView =  root.scroll_view  // mAdjustMode为true时，mScrollView为null
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
                    lp.weight = mAdapter.getTitleWeight(context, i)
                } else {
                    lp = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
                }
                mTitleContainer!!.addView(view, lp)
            }
            i++
        }
        if (mAdapter != null) {
            mIndicator = mAdapter.getIndicator(context)
            if (mIndicator is View) {
                val lp = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                mIndicatorContainer!!.addView(mIndicator as View?, lp)
            }
        }
    }



    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Float) {
        TODO("Not yet implemented")
    }

    override fun onPageSelected(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onPageScrollStateChanged(state: Int) {
        TODO("Not yet implemented")
    }

    override fun onAttachToMagicIndicator() {
        TODO("Not yet implemented")
    }

    override fun onDetachFromMagicIndicator() {
        TODO("Not yet implemented")
    }

    override fun notifyDataSetChanged() {
        mAdapter?.notifyDataSetChanged()
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onSelected(index: Int, totalCount: Int) {
        TODO("Not yet implemented")
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        TODO("Not yet implemented")
    }

}