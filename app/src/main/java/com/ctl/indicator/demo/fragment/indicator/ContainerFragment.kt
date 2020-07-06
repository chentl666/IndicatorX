package com.ctl.indicator.demo.fragment.indicator

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.ctl.indicator.demo.R
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentContainerBinding
import com.ctl.indicator.lib.commonnavigator.CommonNavigator
import com.ctl.indicator.lib.commonnavigator.abs.CommonNavigatorAdapter
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerTitleView
import com.ctl.indicator.lib.commonnavigator.indicators.LinePagerIndicator
import com.ctl.indicator.lib.commonnavigator.titles.ClipPagerTitleView
import com.ctl.indicator.lib.help.FragmentContainerHelper
import com.ctl.indicator.lib.util.UIUtil
import java.util.*

class ContainerFragment : BaseFragment() {
    private lateinit var binding: FragmentContainerBinding
    private val CHANNELS = arrayOf("KITKAT", "NOUGAT", "DONUT")
    private val mFragments: MutableList<Fragment> = ArrayList()
    private val mFragmentContainerHelper: FragmentContainerHelper = FragmentContainerHelper()
    override fun setLayoutView(): View? {
        binding = FragmentContainerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        initFragments()
        initIndicator1()

        mFragmentContainerHelper.handlePageSelected(1, false)
        switchPages(1)
    }

    override fun initData() {
    }

    override fun initListener() {
        binding.toolbar.imgToolbarBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun switchPages(index: Int) {
        val fragmentManager: FragmentManager = childFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        var fragment: Fragment
        var i = 0
        val j = mFragments.size
        while (i < j) {
            if (i == index) {
                i++
                continue
            }
            fragment = mFragments[i]
            if (fragment.isAdded) {
                fragmentTransaction.hide(fragment)
            }
            i++
        }
        fragment = mFragments[index]
        if (fragment.isAdded) {
            fragmentTransaction.show(fragment)
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun initFragments() {
        for (i in CHANNELS.indices) {
            val testFragment = ContainerTestFragment()
            val bundle = Bundle()
            bundle.putString("text", CHANNELS[i])
            testFragment.arguments = bundle
            mFragments.add(testFragment)
        }
    }

    private fun initIndicator1() {
        binding.indicator1.setBackgroundResource(R.drawable.round_indicator_bg)
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return CHANNELS.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.setText(
                    CHANNELS[index]
                )
                clipPagerTitleView.setTextColor(Color.parseColor("#e94220"))
                clipPagerTitleView.setClipColor(Color.WHITE)
                clipPagerTitleView.setOnClickListener(View.OnClickListener {
                    mFragmentContainerHelper.handlePageSelected(index)
                    switchPages(index)
                })
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                val navigatorHeight = context.resources.getDimension(R.dimen.common_navigator_height)
                val borderWidth: Float = UIUtil.dip2px(context, 1.0).toFloat()
                val lineHeight = navigatorHeight - 2 * borderWidth
                indicator.setLineHeight(lineHeight)
                indicator.setRoundRadius(lineHeight / 2)
                indicator.setYOffset(borderWidth)
                indicator.setColors(Color.parseColor("#bc2a2a"))
                return indicator
            }
        })
        binding.indicator1.setNavigator(commonNavigator)
        mFragmentContainerHelper.attachIndicatorX(binding.indicator1)
    }
}