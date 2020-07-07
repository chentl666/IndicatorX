package com.ctl.indicator.demo

import android.graphics.Color
import android.view.View
import com.ctl.indicator.demo.adapter.ExamplePagerAdapter
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentCircleNavigatorBinding
import com.ctl.indicator.lib.circlenavigator.CircleNavigator
import com.ctl.indicator.lib.circlenavigator.ScaleCircleNavigator
import com.ctl.indicator.lib.help.ViewPagerHelper


class CircleNavigatorFragment : BaseFragment() {
    private lateinit var binding: FragmentCircleNavigatorBinding
    private val CHANNELS = arrayOf(
        "CUPCAKE",
        "DONUT",
        "ECLAIR",
        "GINGERBREAD",
        "HONEYCOMB",
        "ICE_CREAM_SANDWICH",
        "JELLY_BEAN",
        "KITKAT",
        "LOLLIPOP",
        "M",
        "NOUGAT"
    )
    private val mDataList = mutableListOf(*CHANNELS)
    private val mExamplePagerAdapter: ExamplePagerAdapter = ExamplePagerAdapter(mDataList)

    override fun setLayoutView(): View? {
        binding = FragmentCircleNavigatorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        binding.viewPager.adapter = mExamplePagerAdapter
    }

    override fun initData() {
        initIndicator1()
        initIndicator2()
        initIndicator3()
    }

    override fun initListener() {
    }

    private fun initIndicator1() {
        val circleNavigator = CircleNavigator(requireContext())
        circleNavigator.setCircleCount(CHANNELS.size)
        circleNavigator.setCircleColor(Color.RED)
        circleNavigator.setCircleClickListener(object : CircleNavigator.OnCircleClickListener {
            override fun onClick(index: Int) {
                binding.viewPager.currentItem = index
            }
        })
        binding.indicator1.setNavigator(circleNavigator)
        ViewPagerHelper.bind(binding.indicator1, binding.viewPager)
    }

    private fun initIndicator2() {
        val circleNavigator = CircleNavigator(requireContext())
        circleNavigator.setFollowTouch(false)
        circleNavigator.setCircleCount(CHANNELS.size)
        circleNavigator.setCircleColor(Color.RED)
        circleNavigator.setCircleClickListener(object : CircleNavigator.OnCircleClickListener {
            override fun onClick(index: Int) {
                binding.viewPager.currentItem = index
            }
        })
        binding.indicator2.setNavigator(circleNavigator)
        ViewPagerHelper.bind(binding.indicator2, binding.viewPager)
    }

    private fun initIndicator3() {
        val scaleCircleNavigator = ScaleCircleNavigator(requireContext())
        scaleCircleNavigator.setCircleCount(CHANNELS.size)
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY)
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY)
        scaleCircleNavigator.setCircleClickListener(object : ScaleCircleNavigator.OnCircleClickListener {
            override fun onClick(index: Int) {
                binding.viewPager.currentItem = index
            }
        })
        binding.indicator3.setNavigator(scaleCircleNavigator)
        ViewPagerHelper.bind(binding.indicator3, binding.viewPager)
    }
}