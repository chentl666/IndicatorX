package com.ctl.indicator.demo.fragment.indicator

import android.view.View
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentFixedTabBinding
import com.ctl.indicator.demo.lifecycle.CustomLifecycle


class fixedTabFragment : BaseFragment() {
    private lateinit var binding: FragmentFixedTabBinding

    override fun setLayoutView(): View? {
        binding = FragmentFixedTabBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        lifecycle.addObserver(CustomLifecycle())
    }

    override fun initData() {
    }

    override fun initListener() {
    }

}