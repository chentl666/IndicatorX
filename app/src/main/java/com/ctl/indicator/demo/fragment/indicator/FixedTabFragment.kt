package com.ctl.indicator.demo.fragment.indicator

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.ctl.indicator.demo.R
import com.ctl.indicator.demo.adapter.ExamplePagerAdapter
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentFixedTabBinding
import com.ctl.indicator.demo.lifecycle.CustomLifecycle
import com.ctl.indicator.lib.commonnavigator.CommonNavigator
import com.ctl.indicator.lib.commonnavigator.abs.CommonNavigatorAdapter
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerTitleView
import com.ctl.indicator.lib.commonnavigator.indicators.LinePagerIndicator
import com.ctl.indicator.lib.commonnavigator.titles.ClipPagerTitleView
import com.ctl.indicator.lib.commonnavigator.titles.ColorTransitionPagerTitleView
import com.ctl.indicator.lib.commonnavigator.titles.ScaleTransitionPagerTitleView
import com.ctl.indicator.lib.commonnavigator.titles.SimplePagerTitleView
import com.ctl.indicator.lib.help.FragmentContainerHelper
import com.ctl.indicator.lib.help.ViewPagerHelper
import com.ctl.indicator.lib.util.UIUtil
import kotlinx.android.synthetic.main.fragment_fixed_tab.*


class FixedTabFragment : BaseFragment() {
    private lateinit var binding: FragmentFixedTabBinding

    private val CHANNELS = arrayOf(
        "KITKAT",
        "LOLLIPOP",
        "NOUGAT"
    )
    private val mDataList = mutableListOf(*CHANNELS)
    private val mExamplePagerAdapter: ExamplePagerAdapter = ExamplePagerAdapter(mDataList)

    override fun setLayoutView(): View? {
        binding = FragmentFixedTabBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        lifecycle.addObserver(CustomLifecycle())
        binding.viewPager.adapter = mExamplePagerAdapter

        initIndicator1()
        initIndicator2()
        initIndicator3()
        initIndicator4()
    }

    override fun initData() {
        val title = arguments?.getString("title") ?: ""
        binding.toolbar.txtToolbarTitle.text = title
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
                val simplePagerTitleView: SimplePagerTitleView = ColorTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.setNormalColor(Color.parseColor("#88ffffff"))
                simplePagerTitleView.setSelectedColor(Color.WHITE)
                simplePagerTitleView.setOnClickListener(View.OnClickListener { view_pager.currentItem = index })
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.setColors(Color.parseColor("#40c4ff"))
                return indicator
            }
        })
        indicator1.setNavigator(commonNavigator)

        val titleContainer: LinearLayout? = commonNavigator.getTitleContainer() // must after setNavigator
        titleContainer?.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer?.dividerPadding = UIUtil.dip2px(requireContext(), 15.0)
        titleContainer?.dividerDrawable = resources.getDrawable(R.drawable.simple_splitter, null)
        ViewPagerHelper.bind(indicator1, view_pager)
    }

    private fun initIndicator2() {
        indicator2.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setAdjustMode(true)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView: SimplePagerTitleView = ScaleTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.textSize = 18.0f
                simplePagerTitleView.setNormalColor(Color.parseColor("#616161"))
                simplePagerTitleView.setSelectedColor(Color.parseColor("#f57c00"))
                simplePagerTitleView.setOnClickListener { view_pager.setCurrentItem(index) }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.setStartInterpolator(AccelerateInterpolator())
                indicator.setEndInterpolator(DecelerateInterpolator(1.6f))
                indicator.setYOffset(UIUtil.dip2px(context, 39.0).toFloat())
                indicator.setLineHeight(UIUtil.dip2px(context, 1.0).toFloat())
                indicator.setColors(Color.parseColor("#f57c00"))
                return indicator
            }

            override fun getTitleWeight(context: Context?, index: Int): Float {
                return when (index) {
                    0 -> {
                        2.0f
                    }
                    1 -> {
                        1.2f
                    }
                    else -> {
                        1.0f
                    }
                }
            }
        })
        indicator2.setNavigator(commonNavigator)
        ViewPagerHelper.bind(indicator2, view_pager)
    }

    private fun initIndicator3() {
        indicator3.setBackgroundResource(R.drawable.round_indicator_bg)
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.setText(mDataList[index])
                clipPagerTitleView.setTextColor(Color.parseColor("#e94220"))
                clipPagerTitleView.setClipColor(Color.WHITE)
                clipPagerTitleView.setOnClickListener(View.OnClickListener { view_pager.setCurrentItem(index) })
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                val navigatorHeight = context.resources.getDimension(R.dimen.common_navigator_height)
                val borderWidth = UIUtil.dip2px(context, 1.0).toFloat()
                val lineHeight = navigatorHeight - 2 * borderWidth
                indicator.setLineHeight(lineHeight)
                indicator.setRoundRadius(lineHeight / 2)
                indicator.setYOffset(borderWidth)
                indicator.setColors(Color.parseColor("#bc2a2a"))
                return indicator
            }
        })
        indicator3.setNavigator(commonNavigator)
        ViewPagerHelper.bind(indicator3, view_pager)
    }

    private fun initIndicator4() {
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView: SimplePagerTitleView = ColorTransitionPagerTitleView(context)
                simplePagerTitleView.setNormalColor(Color.GRAY)
                simplePagerTitleView.setSelectedColor(Color.WHITE)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.setOnClickListener { view_pager.setCurrentItem(index) }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val linePagerIndicator = LinePagerIndicator(context)
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY)
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 10.0).toFloat())
                linePagerIndicator.setColors(Color.WHITE)
                return linePagerIndicator
            }
        })
        indicator4.setNavigator(commonNavigator)
        val titleContainer = commonNavigator.getTitleContainer() // must after setNavigator

        titleContainer!!.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer.dividerDrawable = object : ColorDrawable() {
            override fun getIntrinsicWidth(): Int {
                return UIUtil.dip2px(requireContext(), 15.0)
            }
        }

        val fragmentContainerHelper = FragmentContainerHelper(indicator4)
        fragmentContainerHelper.setInterpolator(OvershootInterpolator(2.0f))
        fragmentContainerHelper.setDuration(300)
        view_pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                fragmentContainerHelper.handlePageSelected(position)
            }
        })
    }
}