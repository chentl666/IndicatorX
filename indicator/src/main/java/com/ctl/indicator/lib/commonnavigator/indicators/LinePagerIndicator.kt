package com.ctl.indicator.lib.commonnavigator.indicators

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.model.PositionData
import com.ctl.indicator.lib.help.FragmentContainerHelper
import com.ctl.indicator.lib.util.UIUtil
import java.util.*

/**
 * 直线viewpager指示器，带颜色渐变
 * created by : chentl
 * Date: 2020/07/03
 */
class LinePagerIndicator(context: Context) : View(context), IPagerIndicator {

    companion object{
        val MODE_MATCH_EDGE = 0 // 直线宽度 == title宽度 - 2 * mXOffset
        val MODE_WRAP_CONTENT = 1 // 直线宽度 == title内容宽度 - 2 * mXOffset
        val MODE_EXACTLY = 2 // 直线宽度 == mLineWidth
    }

    private var mMode = 0// 默认为MODE_MATCH_EDGE模式

    // 控制动画
    private var mStartInterpolator: Interpolator? = LinearInterpolator()
    private var mEndInterpolator: Interpolator? = LinearInterpolator()

    private var mYOffset = 0f// 相对于底部的偏移量，如果你想让直线位于title上方，设置它即可
    private var mLineHeight = 0f
    private var mXOffset = 0f
    private var mLineWidth = 0f
    private var mRoundRadius = 0f

    private var mPaint: Paint? = null
    private var mPositionDataList: List<PositionData>? = null
    private var mColors: List<Int>? = null

    private val mLineRect = RectF()


    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.style = Paint.Style.FILL
        mLineHeight = UIUtil.dip2px(context, 3.0).toFloat()
        mLineWidth = UIUtil.dip2px(context, 10.0).toFloat()
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(mLineRect, mRoundRadius, mRoundRadius, mPaint!!)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Float) {
        if (mPositionDataList == null || mPositionDataList!!.isEmpty()) {
            return
        }

        // 计算颜色
        if (mColors != null && mColors!!.size > 0) {
            val currentColor = mColors!![Math.abs(position) % mColors!!.size]
            val nextColor = mColors!![Math.abs(position + 1) % mColors!!.size]
            val color: Int = ArgbEvaluator().evaluate(positionOffset, currentColor, nextColor) as Int
            mPaint!!.color = color
        }

        // 计算锚点位置
        val current: PositionData = FragmentContainerHelper.getImitativePositionData(mPositionDataList!!, position)!!
        val next: PositionData = FragmentContainerHelper.getImitativePositionData(mPositionDataList!!, position + 1)!!
        val leftX: Float
        val nextLeftX: Float
        val rightX: Float
        val nextRightX: Float
        if (mMode == MODE_MATCH_EDGE) {
            leftX = current.mLeft + mXOffset
            nextLeftX = next.mLeft + mXOffset
            rightX = current.mRight - mXOffset
            nextRightX = next.mRight - mXOffset
        } else if (mMode == MODE_WRAP_CONTENT) {
            leftX = current.mContentLeft + mXOffset
            nextLeftX = next.mContentLeft + mXOffset
            rightX = current.mContentRight - mXOffset
            nextRightX = next.mContentRight - mXOffset
        } else {    // MODE_EXACTLY
            leftX = current.mLeft + (current.width() - mLineWidth) / 2
            nextLeftX = next.mLeft + (next.width() - mLineWidth) / 2
            rightX = current.mLeft + (current.width() + mLineWidth) / 2
            nextRightX = next.mLeft + (next.width() + mLineWidth) / 2
        }
        mLineRect.left = leftX + (nextLeftX - leftX) * mStartInterpolator!!.getInterpolation(positionOffset)
        mLineRect.right = rightX + (nextRightX - rightX) * mEndInterpolator!!.getInterpolation(positionOffset)
        mLineRect.top = height - mLineHeight - mYOffset
        mLineRect.bottom = height - mYOffset
        invalidate()
    }

    override fun onPageSelected(position: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPositionDataProvide(dataList: List<PositionData>?) {
        mPositionDataList = dataList
    }


    fun getYOffset(): Float {
        return mYOffset
    }

    fun setYOffset(yOffset: Float) {
        mYOffset = yOffset
    }

    fun getXOffset(): Float {
        return mXOffset
    }

    fun setXOffset(xOffset: Float) {
        mXOffset = xOffset
    }

    fun getLineHeight(): Float {
        return mLineHeight
    }

    fun setLineHeight(lineHeight: Float) {
        mLineHeight = lineHeight
    }

    fun getLineWidth(): Float {
        return mLineWidth
    }

    fun setLineWidth(lineWidth: Float) {
        mLineWidth = lineWidth
    }

    fun getRoundRadius(): Float {
        return mRoundRadius
    }

    fun setRoundRadius(roundRadius: Float) {
        mRoundRadius = roundRadius
    }

    fun getMode(): Int {
        return mMode
    }

    fun setMode(mode: Int) {
        mMode = if (mode == MODE_EXACTLY || mode == MODE_MATCH_EDGE || mode == MODE_WRAP_CONTENT) {
            mode
        } else {
            throw IllegalArgumentException("mode $mode not supported.")
        }
    }

    fun getPaint(): Paint? {
        return mPaint
    }

    fun getColors(): List<Int>? {
        return mColors
    }

    fun setColors(vararg colors: Int?) {
        mColors = Arrays.asList<Int>(*colors)
    }

    fun getStartInterpolator(): Interpolator? {
        return mStartInterpolator
    }

    fun setStartInterpolator(startInterpolator: Interpolator?) {
        mStartInterpolator = startInterpolator
        if (mStartInterpolator == null) {
            mStartInterpolator = LinearInterpolator()
        }
    }

    fun getEndInterpolator(): Interpolator? {
        return mEndInterpolator
    }

    fun setEndInterpolator(endInterpolator: Interpolator?) {
        mEndInterpolator = endInterpolator
        if (mEndInterpolator == null) {
            mEndInterpolator = LinearInterpolator()
        }
    }
}