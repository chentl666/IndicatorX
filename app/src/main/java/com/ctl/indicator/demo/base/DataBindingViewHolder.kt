package com.ctl.indicator.demo.base

import android.content.Context
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * created by : chentl
 * Date: 2020/07/14
 */
abstract class DataBindingViewHolder<T>(val view: View) : RecyclerView.ViewHolder(view) {

    @Throws(Exception::class)
    abstract fun bindData(data: T, position: Int)

    fun view() = view

    fun context(): Context {
        return view.context
    }

    inline fun <reified T : ViewDataBinding> viewHolderBinding(view: View): Lazy<T> =
        lazy {
            requireNotNull(DataBindingUtil.bind<T>(view)) { "cannot find the matched layout." }
        }

}