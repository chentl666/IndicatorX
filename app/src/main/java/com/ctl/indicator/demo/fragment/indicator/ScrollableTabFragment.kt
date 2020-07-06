package com.ctl.indicator.demo.fragment.indicator

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ctl.indicator.demo.adapter.ExamplePagerAdapter
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentScrollableTabBinding
import com.ctl.indicator.demo.lifecycle.CustomLifecycle
import com.ctl.indicator.lib.commonnavigator.CommonNavigator
import com.ctl.indicator.lib.commonnavigator.abs.CommonNavigatorAdapter
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerTitleView
import com.ctl.indicator.lib.commonnavigator.indicators.BezierPagerIndicator
import com.ctl.indicator.lib.commonnavigator.indicators.LinePagerIndicator
import com.ctl.indicator.lib.commonnavigator.indicators.TriangularPagerIndicator
import com.ctl.indicator.lib.commonnavigator.indicators.WrapPagerIndicator
import com.ctl.indicator.lib.commonnavigator.titles.*
import com.ctl.indicator.lib.help.ViewPagerHelper
import com.ctl.indicator.lib.util.UIUtil
import kotlinx.android.synthetic.main.fragment_scrollable_tab.*

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
        initIndicator2()
        initIndicator3()
        initIndicator4()
        initIndicator5()
        initIndicator6()
        initIndicator7()
        initIndicator8()
        initIndicator9()
    }

    override fun initData() {
        binding.toolbar.txtToolbarTitle.text = safeArgs.value
    }

    override fun initListener() {

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
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.setText(mDataList[index])
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"))
                clipPagerTitleView.setClipColor(Color.WHITE)
                clipPagerTitleView.setOnClickListener(View.OnClickListener { binding.viewPager.currentItem = index })
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        })
        binding.indicator1.setBackgroundColor(Color.parseColor("#d43d3d"))
        binding.indicator1.setNavigator(commonNavigator)
        ViewPagerHelper.bind(binding.indicator1, binding.viewPager)
    }

    private fun initIndicator2() {
        binding.indicator2.setBackgroundColor(Color.parseColor("#00c853"))
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setScrollPivotX(0.25f)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.setNormalColor(Color.parseColor("#c8e6c9"))
                simplePagerTitleView.setSelectedColor(Color.WHITE)
                simplePagerTitleView.textSize = 12.0f
                simplePagerTitleView.setOnClickListener(View.OnClickListener { binding.viewPager.currentItem = index })
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY)
                indicator.setYOffset(UIUtil.dip2px(context, 3.0).toFloat())
                indicator.setColors(Color.parseColor("#ffffff"))
                return indicator
            }
        })
        binding.indicator2.setNavigator(commonNavigator)
        ViewPagerHelper.bind(binding.indicator2, binding.viewPager)
    }

    private fun initIndicator3() {
        binding.indicator3.setBackgroundColor(Color.BLACK)
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
                simplePagerTitleView.setOnClickListener { binding.viewPager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val linePagerIndicator = LinePagerIndicator(context)
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT)
                linePagerIndicator.setColors(Color.WHITE)
                return linePagerIndicator
            }
        })
        binding.indicator3.setNavigator(commonNavigator)
        ViewPagerHelper.bind(binding.indicator3, binding.viewPager)
    }

    private fun initIndicator4() {
        indicator4.setBackgroundColor(Color.parseColor("#455a64"))
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
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.setColors(Color.parseColor("#40c4ff"))
                return indicator
            }
        })
        indicator4.setNavigator(commonNavigator)
        ViewPagerHelper.bind(indicator4, view_pager)
    }

    private fun initIndicator5() {
        indicator5.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setScrollPivotX(0.8f)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView: SimplePagerTitleView = ScaleTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.textSize = 18f
                simplePagerTitleView.setNormalColor(Color.parseColor("#616161"))
                simplePagerTitleView.setSelectedColor(Color.parseColor("#f57c00"))
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
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
        })
        indicator5.setNavigator(commonNavigator)
        ViewPagerHelper.bind(indicator5, view_pager)
    }

    private fun initIndicator6() {
        indicator6.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView: SimplePagerTitleView = ScaleTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.textSize = 18f
                simplePagerTitleView.setNormalColor(Color.GRAY)
                simplePagerTitleView.setSelectedColor(Color.BLACK)
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = BezierPagerIndicator(context)
                indicator.setColors(
                    Color.parseColor("#ff4a42"),
                    Color.parseColor("#fcde64"),
                    Color.parseColor("#73e8f4"),
                    Color.parseColor("#76b0ff"),
                    Color.parseColor("#c683fe")
                )
                return indicator
            }
        })
        indicator6.setNavigator(commonNavigator)
        ViewPagerHelper.bind(indicator6, view_pager)
    }

    private fun initIndicator7() {
        indicator7.setBackgroundColor(Color.parseColor("#fafafa"))
        val commonNavigator7 = CommonNavigator(requireContext())
        commonNavigator7.setScrollPivotX(0.65f)
        commonNavigator7.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView: SimplePagerTitleView = ColorFlipPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.setNormalColor(Color.parseColor("#9e9e9e"))
                simplePagerTitleView.setSelectedColor(Color.parseColor("#00c853"))
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY)
                indicator.setLineHeight(UIUtil.dip2px(context, 6.0).toFloat())
                indicator.setLineWidth(UIUtil.dip2px(context, 10.0).toFloat())
                indicator.setRoundRadius(UIUtil.dip2px(context, 3.0).toFloat())
                indicator.setStartInterpolator(AccelerateInterpolator())
                indicator.setEndInterpolator(DecelerateInterpolator(2.0f))
                indicator.setColors(Color.parseColor("#00c853"))
                return indicator
            }
        })
        indicator7.setNavigator(commonNavigator7)
        ViewPagerHelper.bind(indicator7, view_pager)
    }

    private fun initIndicator8() {
        indicator8.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setScrollPivotX(0.35f)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"))
                simplePagerTitleView.setSelectedColor(Color.parseColor("#e94220"))
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = WrapPagerIndicator(context)
                indicator.setFillColor(Color.parseColor("#ebe4e3"))
                return indicator
            }
        })
        indicator8.setNavigator(commonNavigator)
        ViewPagerHelper.bind(indicator8, view_pager)
    }

    private fun initIndicator9() {
        indicator9.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setScrollPivotX(0.15f)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"))
                simplePagerTitleView.setSelectedColor(Color.parseColor("#e94220"))
                simplePagerTitleView.setOnClickListener { view_pager.setCurrentItem(index) }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = TriangularPagerIndicator(context)
                indicator.setLineColor(Color.parseColor("#e94220"))
                return indicator
            }
        })
        indicator9.setNavigator(commonNavigator)
        ViewPagerHelper.bind(indicator9, view_pager)
    }
}