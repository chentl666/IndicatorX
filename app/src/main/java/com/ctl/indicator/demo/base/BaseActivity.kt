package com.ctl.indicator.demo.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * created by : chentl
 * Date: 2020/06/30
 */
abstract class BaseActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutView())
        initView()
        initListener()
        initData()
    }

    abstract fun setLayoutView(): View

    abstract fun initView()

    abstract fun initData()

    abstract fun initListener()

}