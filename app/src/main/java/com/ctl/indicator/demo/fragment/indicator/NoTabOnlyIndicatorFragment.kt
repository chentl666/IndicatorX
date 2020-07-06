package com.ctl.indicator.demo.fragment.indicator

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ctl.indicator.demo.R
import com.ctl.indicator.demo.adapter.ExamplePagerAdapter
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentNoTabOnlyIndicatorBinding
import com.ctl.indicator.lib.commonnavigator.CommonNavigator
import com.ctl.indicator.lib.commonnavigator.abs.CommonNavigatorAdapter
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerTitleView
import com.ctl.indicator.lib.commonnavigator.indicators.LinePagerIndicator
import com.ctl.indicator.lib.commonnavigator.indicators.TriangularPagerIndicator
import com.ctl.indicator.lib.commonnavigator.titles.DummyPagerTitleView
import com.ctl.indicator.lib.help.ViewPagerHelper
import com.ctl.indicator.lib.util.UIUtil


class NoTabOnlyIndicatorFragment : BaseFragment() {

    private lateinit var binding: FragmentNoTabOnlyIndicatorBinding
    private val navArgs: NoTabOnlyIndicatorFragmentArgs by navArgs()
    private val CHANNELS = arrayOf(
        "CUPCAKE",
        "DONUT",
        "ECLAIR",
        "GINGERBREAD"
    )
    private val mDataList = mutableListOf(*CHANNELS)
    private val mExamplePagerAdapter: ExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    override fun setLayoutView(): View? {
        binding = FragmentNoTabOnlyIndicatorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        binding.viewPager.adapter = mExamplePagerAdapter
        initIndicator1()
        initIndicator2()
    }

    override fun initData() {
        binding.toolbar.txtToolbarTitle.text = navArgs.title
    }

    override fun initListener() {
        binding.toolbar.imgToolbarBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initIndicator1() {
        binding.indicator1.setBackgroundColor(Color.LTGRAY)
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setAdjustMode(true)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                return DummyPagerTitleView(context)
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                val lineHeight = context.resources.getDimension(R.dimen.small_navigator_height)
                indicator.setLineHeight(lineHeight)
                indicator.setColors(Color.parseColor("#40c4ff"))
                return indicator
            }
        })
        binding.indicator1.setNavigator(commonNavigator)
        ViewPagerHelper.bind(binding.indicator1, binding.viewPager)
    }

    private fun initIndicator2() {
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setAdjustMode(true)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                return DummyPagerTitleView(context)
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = TriangularPagerIndicator(context)
                indicator.setReverse(true)
                val smallNavigatorHeight = context.resources.getDimension(R.dimen.small_navigator_height)
                indicator.setLineHeight(UIUtil.dip2px(context, 2.0))
                indicator.setTriangleHeight(smallNavigatorHeight.toInt())
                indicator.setLineColor(Color.parseColor("#e94220"))
                return indicator
            }
        })
        binding.indicator2.setNavigator(commonNavigator)
        ViewPagerHelper.bind(binding.indicator2, binding.viewPager)
    }
}