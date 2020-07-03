package com.ctl.indicator.demo.fragment.indicator

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ctl.indicator.demo.R
import com.ctl.indicator.demo.adapter.ExamplePagerAdapter
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentScrollableTabBinding
import com.ctl.indicator.demo.lifecycle.CustomLifecycle
import com.ctl.indicator.lib.commonnavigator.CommonNavigator
import com.ctl.indicator.lib.commonnavigator.abs.CommonNavigatorAdapter
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerTitleView
import com.ctl.indicator.lib.commonnavigator.indicators.LinePagerIndicator
import com.ctl.indicator.lib.commonnavigator.titles.SimplePagerTitleView
import com.ctl.indicator.lib.help.ViewPagerHelper
import com.ctl.indicator.lib.util.UIUtil

/**
 * created by : chentl
 * Date: 2020/07/01
 */
class ScrollableTabFragment : BaseFragment() {

    private lateinit var binding: FragmentScrollableTabBinding
    private val safeArgs: ScrollableTabFragmentArgs by navArgs()
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
        binding = FragmentScrollableTabBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        lifecycle.addObserver(CustomLifecycle())
        binding.viewPager.adapter = mExamplePagerAdapter

        initIndicator1()
    }

    override fun initData() {
        binding.txtClick.text = safeArgs.value
        binding.toolbar.txtToolbarTitle.text = "ScrollableTab"
    }

    override fun initListener() {

        binding.txtClick.setOnClickListener {
            findNavController().navigate(R.id.fixedTabFragment)
        }
        binding.toolbar.imgToolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initIndicator1() {
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setSkimOver(true)
        val padding: Int = UIUtil.getScreenWidth(requireContext()) / 2
        commonNavigator.setRightPadding(padding)
        commonNavigator.setLeftPadding(padding)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.setNormalColor(Color.parseColor("#c8e6c9"))
                simplePagerTitleView.setSelectedColor(Color.WHITE)
                simplePagerTitleView.textSize = 12f
                simplePagerTitleView.setOnClickListener { binding.viewPager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(requireContext())
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY)
                indicator.setYOffset(UIUtil.dip2px(requireContext(), 3.0).toFloat())
                indicator.setColors(Color.parseColor("#ffffff"))
                return indicator
            }
        })
        binding.indicator1.setNavigator(commonNavigator)
        ViewPagerHelper.bind(binding.indicator1, binding.viewPager)
    }
}