package com.ctl.indicator.lib.commonnavigator.indicators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.model.PositionData
import com.ctl.indicator.lib.help.FragmentContainerHelper
import com.ctl.indicator.lib.util.UIUtil

/**
 * created by : chentl
 * Date: 2020/07/06
 */
class TriangularPagerIndicator(context: Context) : View(context), IPagerIndicator {
    private var mPositionDataList: List<PositionData>? = null
    private var mPaint: Paint? = null
    private var mLineHeight = 0
    private var mLineColor = 0
    private var mTriangleHeight = 0
    private var mTriangleWidth = 0
    private var mReverse = false
    private var mYOffset = 0f

    private val mPath = Path()
    private var mStartInterpolator: Interpolator? = LinearInterpolator()
    private var mAnchorX = 0f


    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.style = Paint.Style.FILL
        mLineHeight = UIUtil.dip2px(context, 3.0)
        mTriangleWidth = UIUtil.dip2px(context, 14.0)
        mTriangleHeight = UIUtil.dip2px(context, 8.0)
    }

    override fun onDraw(canvas: Canvas) {
        mPaint!!.color = mLineColor
        if (mReverse) {
            canvas.drawRect(
                0f,
                height - mYOffset - mTriangleHeight,
                width.toFloat(),
                height - mYOffset - mTriangleHeight + mLineHeight,
                mPaint!!
            )
        } else {
            canvas.drawRect(0f, height - mLineHeight - mYOffset, width.toFloat(), height - mYOffset, mPaint!!)
        }
        mPath.reset()
        if (mReverse) {
            mPath.moveTo(mAnchorX - mTriangleWidth / 2, height - mYOffset - mTriangleHeight)
            mPath.lineTo(mAnchorX, height - mYOffset)
            mPath.lineTo(mAnchorX + mTriangleWidth / 2, height - mYOffset - mTriangleHeight)
        } else {
            mPath.moveTo(mAnchorX - mTriangleWidth / 2, height - mYOffset)
            mPath.lineTo(mAnchorX, height - mTriangleHeight - mYOffset)
            mPath.lineTo(mAnchorX + mTriangleWidth / 2, height - mYOffset)
        }
        mPath.close()
        canvas.drawPath(mPath, mPaint!!)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Float) {
        if (mPositionDataList == null || mPositionDataList!!.isEmpty()) {
            return
        }

        // 计算锚点位置
        val current: PositionData = FragmentContainerHelper.getImitativePositionData(mPositionDataList!!, position)
        val next: PositionData = FragmentContainerHelper.getImitativePositionData(mPositionDataList!!, position + 1)
        val leftX: Float = (current.mLeft + (current.mRight - current.mLeft) / 2).toFloat()
        val rightX: Float = (next.mLeft + (next.mRight - next.mLeft) / 2).toFloat()
        mAnchorX = leftX + (rightX - leftX) * mStartInterpolator!!.getInterpolation(positionOffset)
        invalidate()
    }

    override fun onPageSelected(position: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPositionDataProvide(dataList: List<PositionData>?) {
        mPositionDataList = dataList
    }

    fun getLineHeight(): Int {
        return mLineHeight
    }

    fun setLineHeight(lineHeight: Int) {
        mLineHeight = lineHeight
    }

    fun getLineColor(): Int {
        return mLineColor
    }

    fun setLineColor(lineColor: Int) {
        mLineColor = lineColor
    }

    fun getTriangleHeight(): Int {
        return mTriangleHeight
    }

    fun setTriangleHeight(triangleHeight: Int) {
        mTriangleHeight = triangleHeight
    }

    fun getTriangleWidth(): Int {
        return mTriangleWidth
    }

    fun setTriangleWidth(triangleWidth: Int) {
        mTriangleWidth = triangleWidth
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

    fun isReverse(): Boolean {
        return mReverse
    }

    fun setReverse(reverse: Boolean) {
        mReverse = reverse
    }

    fun getYOffset(): Float {
        return mYOffset
    }

    fun setYOffset(yOffset: Float) {
        mYOffset = yOffset
    }
}