package com.ctl.indicator.demo.fragment.indicator

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.ctl.indicator.demo.R
import com.ctl.indicator.demo.adapter.ExamplePagerAdapter
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentBadgeTabBinding
import com.ctl.indicator.lib.commonnavigator.CommonNavigator
import com.ctl.indicator.lib.commonnavigator.abs.CommonNavigatorAdapter
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerTitleView
import com.ctl.indicator.lib.commonnavigator.indicators.LinePagerIndicator
import com.ctl.indicator.lib.commonnavigator.titles.ColorTransitionPagerTitleView
import com.ctl.indicator.lib.commonnavigator.titles.SimplePagerTitleView
import com.ctl.indicator.lib.commonnavigator.titles.badge.BadgeAnchor
import com.ctl.indicator.lib.commonnavigator.titles.badge.BadgePagerTitleView
import com.ctl.indicator.lib.commonnavigator.titles.badge.BadgeRule
import com.ctl.indicator.lib.help.ViewPagerHelper
import com.ctl.indicator.lib.util.UIUtil


class BadgeTabFragment : BaseFragment() {

    private lateinit var binding: FragmentBadgeTabBinding

    private val CHANNELS = arrayOf(
        "KITKAT",
        "LOLLIPOP",
        "NOUGAT"
    )
    private val mDataList = mutableListOf(*CHANNELS)
    private val mExamplePagerAdapter: ExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    override fun setLayoutView(): View? {
        binding = FragmentBadgeTabBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        binding.viewPager.adapter = mExamplePagerAdapter
        initIndicator1()
    }

    override fun initData() {

    }

    override fun initListener() {
        binding.toolbar.imgToolbarBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initIndicator1() {
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val badgePagerTitleView = BadgePagerTitleView(context)
                val simplePagerTitleView: SimplePagerTitleView = ColorTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.setNormalColor(Color.parseColor("#88ffffff"))
                simplePagerTitleView.setSelectedColor(Color.WHITE)
                simplePagerTitleView.setOnClickListener(View.OnClickListener {
                    binding.viewPager.currentItem = index
                    badgePagerTitleView.setBadgeView(null) // cancel badge when click tab
                })
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView)

                // setup badge
                if (index != 2) {
                    val badgeTextView =
                        LayoutInflater.from(context).inflate(R.layout.simple_count_badge_layout, null) as TextView
                    badgeTextView.text = "" + (index + 1)
                    badgePagerTitleView.setBadgeView(badgeTextView)
                } else {
                    val badgeImageView =
                        LayoutInflater.from(context).inflate(R.layout.simple_red_dot_badge_layout, null) as ImageView
                    badgePagerTitleView.setBadgeView(badgeImageView)
                }

                // set badge position
                when (index) {
                    0 -> {
                        badgePagerTitleView.setXBadgeRule(BadgeRule(BadgeAnchor.CONTENT_LEFT, -UIUtil.dip2px(context, 6.0)))
                        badgePagerTitleView.setYBadgeRule(BadgeRule(BadgeAnchor.CONTENT_TOP, 0))
                    }
                    1 -> {
                        badgePagerTitleView.setXBadgeRule(BadgeRule(BadgeAnchor.CONTENT_RIGHT, -UIUtil.dip2px(context, 6.0)))
                        badgePagerTitleView.setYBadgeRule(BadgeRule(BadgeAnchor.CONTENT_TOP, 0))
                    }
                    2 -> {
                        badgePagerTitleView.setXBadgeRule(BadgeRule(BadgeAnchor.CENTER_X, -UIUtil.dip2px(context, 3.0)))
                        badgePagerTitleView.setYBadgeRule(BadgeRule(BadgeAnchor.CONTENT_BOTTOM, UIUtil.dip2px(context, 2.0)))
                    }

                    // don't cancel badge when tab selected
                }

                // don't cancel badge when tab selected
                badgePagerTitleView.setAutoCancelBadge(false)
                return badgePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.setColors(Color.parseColor("#40c4ff"))
                return indicator
            }
        })
        binding.indicator1.setNavigator(commonNavigator)
        val titleContainer: LinearLayout? = commonNavigator.getTitleContainer() // must after setNavigator

        titleContainer?.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer?.dividerPadding = UIUtil.dip2px(requireContext(), 15.0)
        titleContainer?.dividerDrawable = resources.getDrawable(R.drawable.simple_splitter)
        ViewPagerHelper.bind(binding.indicator1, binding.viewPager)
    }

}