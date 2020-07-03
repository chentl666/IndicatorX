package com.ctl.indicator.demo.adapter

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

/**
 * created by : chentl
 * Date: 2020/07/03
 */
class ExamplePagerAdapter(dateList: MutableList<String>) : PagerAdapter() {

    private var mDataList: MutableList<String>? = null

    init {
        mDataList = dateList
    }

    override fun getCount(): Int {
        return if (mDataList == null) 0 else mDataList!!.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val textView = TextView(container.context)
        textView.text = mDataList!![position]
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.BLACK)
        textView.textSize = 24f
        container.addView(textView)
        return textView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun getItemPosition(obj: Any): Int {
        val textView = obj as TextView
        val text = textView.text.toString()
        val index = mDataList!!.indexOf(text)
        return if (index >= 0) {
            index
        } else POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mDataList!![position]
    }
}