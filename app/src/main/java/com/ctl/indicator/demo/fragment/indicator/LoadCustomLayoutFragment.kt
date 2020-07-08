package com.ctl.indicator.demo.fragment.indicator

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ctl.indicator.demo.R
import com.ctl.indicator.demo.adapter.ExamplePagerAdapter
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentLoadCustomLayoutBinding
import com.ctl.indicator.lib.commonnavigator.CommonNavigator
import com.ctl.indicator.lib.commonnavigator.abs.CommonNavigatorAdapter
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerTitleView
import com.ctl.indicator.lib.commonnavigator.titles.CommonPagerTitleView
import com.ctl.indicator.lib.help.ViewPagerHelper
import kotlinx.android.synthetic.main.simple_pager_title_layout.view.*


class LoadCustomLayoutFragment : BaseFragment() {
    private lateinit var binding: FragmentLoadCustomLayoutBinding

    private val CHANNELS = arrayOf(
        "NOUGAT", "DONUT", "ECLAIR", "KITKAT"
    )
    private val mDataList = mutableListOf(*CHANNELS)
    private val mExamplePagerAdapter: ExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    override fun setLayoutView(): View? {
        binding = FragmentLoadCustomLayoutBinding.inflate(layoutInflater)
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
        binding.indicator1.setBackgroundColor(Color.BLACK)
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.setAdjustMode(true)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val commonPagerTitleView = CommonPagerTitleView(context)

                // load custom layout
                val customLayout: View = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, null)
                val titleImg = customLayout.title_img
                val titleText = customLayout.title_text
                titleImg.setImageResource(R.mipmap.ic_launcher)
                titleText.text = mDataList[index]
                commonPagerTitleView.setContentView(customLayout)
                commonPagerTitleView.setOnPagerTitleChangeListener(object : CommonPagerTitleView.OnPagerTitleChangeListener {
                    override fun onSelected(index: Int, totalCount: Int) {
                        titleText.setTextColor(Color.WHITE)
                    }

                    override fun onDeselected(index: Int, totalCount: Int) {
                        titleText.setTextColor(Color.LTGRAY)
                    }

                    override fun onLeave(
                        index: Int,
                        totalCount: Int,
                        leavePercent: Float,
                        leftToRight: Boolean
                    ) {
                        titleImg.scaleX = 1.3f + (0.8f - 1.3f) * leavePercent
                        titleImg.scaleY = 1.3f + (0.8f - 1.3f) * leavePercent
                    }

                    override fun onEnter(
                        index: Int,
                        totalCount: Int,
                        enterPercent: Float,
                        leftToRight: Boolean
                    ) {
                        titleImg.scaleX = 0.8f + (1.3f - 0.8f) * enterPercent
                        titleImg.scaleY = 0.8f + (1.3f - 0.8f) * enterPercent
                    }
                })
                commonPagerTitleView.setOnClickListener { binding.viewPager.currentItem = index }
                return commonPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        })
        binding.indicator1.setNavigator(commonNavigator)
        ViewPagerHelper.bind(binding.indicator1, binding.viewPager)
    }

}