package com.ctl.indicator.demo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * created by : chentl
 * Date: 2020/07/01
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (view == null) {
            setLayoutView()
        } else {
            view
        }
    }

    abstract fun setLayoutView(): View?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun initListener()

}