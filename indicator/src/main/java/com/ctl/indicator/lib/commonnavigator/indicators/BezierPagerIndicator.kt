package com.ctl.indicator.lib.commonnavigator.indicators

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.model.PositionData
import com.ctl.indicator.lib.help.FragmentContainerHelper
import com.ctl.indicator.lib.util.UIUtil
import kotlin.math.abs

/**
 * 贝塞尔曲线ViewPager指示器，带颜色渐变
 * created by : chentl
 * Date: 2020/07/06
 */
class BezierPagerIndicator(context: Context) : View(context), IPagerIndicator {
    private var mPositionDataList: List<PositionData>? = null

    private var mLeftCircleRadius = 0f
    private var mLeftCircleX = 0f
    private var mRightCircleRadius = 0f
    private var mRightCircleX = 0f

    private var mYOffset = 0f
    private var mMaxCircleRadius = 0f
    private var mMinCircleRadius = 0f

    private var mPaint: Paint? = null
    private val mPath = Path()

    private var mColors: MutableList<Int>? = null
    private var mStartInterpolator: Interpolator = AccelerateInterpolator()
    private var mEndInterpolator: Interpolator = DecelerateInterpolator()

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint?.style = Paint.Style.FILL
        mMaxCircleRadius = UIUtil.dip2px(context, 3.5).toFloat()
        mMinCircleRadius = UIUtil.dip2px(context, 2.0).toFloat()
        mYOffset = UIUtil.dip2px(context, 1.5).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(mLeftCircleX, height - mYOffset - mMaxCircleRadius, mLeftCircleRadius, mPaint!!)
        canvas.drawCircle(mRightCircleX, height - mYOffset - mMaxCircleRadius, mRightCircleRadius, mPaint!!)
        drawBezierCurve(canvas)
    }

    /**
     * 绘制贝塞尔曲线
     *
     * @param canvas
     */
    private fun drawBezierCurve(canvas: Canvas) {
        mPath.reset()
        val y = height - mYOffset - mMaxCircleRadius
        mPath.moveTo(mRightCircleX, y)
        mPath.lineTo(mRightCircleX, y - mRightCircleRadius)
        mPath.quadTo(mRightCircleX + (mLeftCircleX - mRightCircleX) / 2.0f, y, mLeftCircleX, y - mLeftCircleRadius)
        mPath.lineTo(mLeftCircleX, y + mLeftCircleRadius)
        mPath.quadTo(mRightCircleX + (mLeftCircleX - mRightCircleX) / 2.0f, y, mRightCircleX, y + mRightCircleRadius)
        mPath.close() // 闭合
        canvas.drawPath(mPath, mPaint!!)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Float) {
        if (mPositionDataList == null || mPositionDataList!!.isEmpty()) {
            return
        }
        // 计算颜色
        if (mColors != null && mColors!!.size > 0) {
            val currentColor = mColors!![abs(position) % mColors!!.size]
            val nextColor = mColors!![abs(position + 1) % mColors!!.size]
            val color: Int = ArgbEvaluator().evaluate(positionOffset, currentColor, nextColor) as Int
            mPaint!!.color = color
        }

        // 计算锚点位置
        val current: PositionData = FragmentContainerHelper.getImitativePositionData(mPositionDataList!!, position)
        val next: PositionData = FragmentContainerHelper.getImitativePositionData(mPositionDataList!!, position + 1)

        val leftX = (current.mLeft + (current.mRight - current.mLeft) / 2).toFloat()
        val rightX = (next.mLeft + (next.mRight - next.mLeft) / 2).toFloat()

        mLeftCircleX = leftX + (rightX - leftX) * mStartInterpolator.getInterpolation(positionOffset)
        mRightCircleX = leftX + (rightX - leftX) * mEndInterpolator.getInterpolation(positionOffset)
        mLeftCircleRadius = mMaxCircleRadius + (mMinCircleRadius - mMaxCircleRadius) * mEndInterpolator.getInterpolation(positionOffset)
        mRightCircleRadius =
            mMinCircleRadius + (mMaxCircleRadius - mMinCircleRadius) * mStartInterpolator.getInterpolation(positionOffset)

        invalidate()
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPositionDataProvide(dataList: List<PositionData>?) {
        mPositionDataList = dataList
    }

    fun getMaxCircleRadius(): Float {
        return mMaxCircleRadius
    }

    fun setMaxCircleRadius(maxCircleRadius: Float) {
        mMaxCircleRadius = maxCircleRadius
    }

    fun getMinCircleRadius(): Float {
        return mMinCircleRadius
    }

    fun setMinCircleRadius(minCircleRadius: Float) {
        mMinCircleRadius = minCircleRadius
    }

    fun getYOffset(): Float {
        return mYOffset
    }

    fun setYOffset(yOffset: Float) {
        mYOffset = yOffset
    }

    fun setColors(vararg colors: Int) {
        mColors = mutableListOf(*colors.toTypedArray())
    }

    fun setStartInterpolator(startInterpolator: Interpolator) {
        mStartInterpolator = startInterpolator
        if (mStartInterpolator == null) {
            mStartInterpolator = AccelerateInterpolator()
        }
    }

    fun setEndInterpolator(endInterpolator: Interpolator) {
        mEndInterpolator = endInterpolator
        if (mEndInterpolator == null) {
            mEndInterpolator = DecelerateInterpolator()
        }
    }
}