package com.ctl.indicator.demo

import android.view.View
import com.ctl.indicator.demo.base.BaseActivity
import com.ctl.indicator.demo.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun setLayoutView(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

}