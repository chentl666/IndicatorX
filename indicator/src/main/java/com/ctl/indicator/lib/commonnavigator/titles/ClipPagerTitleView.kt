package com.ctl.indicator.lib.commonnavigator.titles

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import com.ctl.indicator.lib.commonnavigator.abs.IMeasurablePagerTitleView
import com.ctl.indicator.lib.util.UIUtil
import kotlin.math.min

/**
 * 类似今日头条切换效果的指示器标题
 * created by : chentl
 * Date: 2020/07/06
 */
open class ClipPagerTitleView(context: Context) : View(context), IMeasurablePagerTitleView {
    private var mText: String? = null
    private var mTextColor = 0
    private var mClipColor = 0
    private var mLeftToRight = false
    private var mClipPercent = 0f

    private var mPaint: Paint? = null
    private val mTextBounds = Rect()

    init {
        val textSize: Int = UIUtil.dip2px(context, 16.0)
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint?.textSize = textSize.toFloat()
        val padding: Int = UIUtil.dip2px(context, 10.0)
        setPadding(padding, 0, padding, 0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureTextBounds()
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    private fun measureTextBounds() {
        mPaint!!.getTextBounds(mText, 0, mText?.length ?: 0, mTextBounds)
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val size = MeasureSpec.getSize(widthMeasureSpec)
        var result = size
        when (mode) {
            MeasureSpec.AT_MOST -> {
                val width = mTextBounds.width() + paddingLeft + paddingRight
                result = min(width, size)
            }
            MeasureSpec.UNSPECIFIED -> result = mTextBounds.width() + paddingLeft + paddingRight
            else -> {
            }
        }
        return result
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        val size = MeasureSpec.getSize(heightMeasureSpec)
        var result = size
        when (mode) {
            MeasureSpec.AT_MOST -> {
                val height = mTextBounds.height() + paddingTop + paddingBottom
                result = min(height, size)
            }
            MeasureSpec.UNSPECIFIED -> result = mTextBounds.height() + paddingTop + paddingBottom
            else -> {
            }
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        val x = (width - mTextBounds.width()) / 2
        val fontMetrics = mPaint!!.fontMetrics
        val y = ((height - fontMetrics.bottom - fontMetrics.top) / 2).toInt()

        // 画底层
        mPaint!!.color = mTextColor
        canvas.drawText(mText!!, x.toFloat(), y.toFloat(), mPaint!!)

        // 画clip层
        canvas.save()
        if (mLeftToRight) {
            canvas.clipRect(0f, 0f, width * mClipPercent, height.toFloat())
        } else {
            canvas.clipRect(width * (1 - mClipPercent), 0f, width.toFloat(), height.toFloat())
        }
        mPaint!!.color = mClipColor
        canvas.drawText(mText!!, x.toFloat(), y.toFloat(), mPaint!!)
        canvas.restore()
    }

    override fun getContentLeft(): Int {
        val contentWidth = mTextBounds.width()
        return left + width / 2 - contentWidth / 2
    }

    override fun getContentTop(): Int {
        val metrics = mPaint!!.fontMetrics
        val contentHeight = metrics.bottom - metrics.top
        return (height / 2 - contentHeight / 2).toInt()
    }

    override fun getContentRight(): Int {
        val contentWidth = mTextBounds.width()
        return left + width / 2 + contentWidth / 2
    }

    override fun getContentBottom(): Int {
        val metrics = mPaint!!.fontMetrics
        val contentHeight = metrics.bottom - metrics.top
        return (height / 2 + contentHeight / 2).toInt()
    }

    override fun onSelected(index: Int, totalCount: Int) {
    }

    override fun onDeselected(index: Int, totalCount: Int) {
    }

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        mLeftToRight = !leftToRight
        mClipPercent = 1.0f - leavePercent
        invalidate()
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        mLeftToRight = leftToRight
        mClipPercent = enterPercent
        invalidate()
    }


    fun getText(): String? {
        return mText
    }

    fun setText(text: String) {
        mText = text
        requestLayout()
    }

    fun getTextSize(): Float {
        return mPaint!!.textSize
    }

    fun setTextSize(textSize: Float) {
        mPaint!!.textSize = textSize
        requestLayout()
    }

    fun getTextColor(): Int {
        return mTextColor
    }

    fun setTextColor(textColor: Int) {
        mTextColor = textColor
        invalidate()
    }

    fun getClipColor(): Int {
        return mClipColor
    }

    fun setClipColor(clipColor: Int) {
        mClipColor = clipColor
        invalidate()
    }
}