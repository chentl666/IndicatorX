package com.ctl.indicator.demo.fragment.indicator

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ctl.indicator.demo.adapter.ExamplePagerAdapter
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentDynamicTabBinding
import com.ctl.indicator.lib.commonnavigator.CommonNavigator
import com.ctl.indicator.lib.commonnavigator.abs.CommonNavigatorAdapter
import com.ctl.indicator.lib.commonnavigator.abs.IPagerIndicator
import com.ctl.indicator.lib.commonnavigator.abs.IPagerTitleView
import com.ctl.indicator.lib.commonnavigator.titles.ClipPagerTitleView
import com.ctl.indicator.lib.help.ViewPagerHelper
import java.util.*


class DynamicTabFragment : BaseFragment() {
    private lateinit var mCommonNavigator: CommonNavigator
    private lateinit var binding: FragmentDynamicTabBinding
    private val safeArgs: DynamicTabFragmentArgs by navArgs()
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
    private lateinit var toast: Toast

    override fun setLayoutView(): View? {
        binding = FragmentDynamicTabBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        binding.viewPager.adapter = mExamplePagerAdapter

        initIndicator1()
    }

    override fun initData() {
        binding.toolbar.txtToolbarTitle.text = safeArgs.value
    }

    override fun initListener() {
        binding.toolbar.imgToolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnRandom.setOnClickListener {
            randomPage()
        }
    }

    private fun initIndicator1() {
        binding.indicator1.setBackgroundColor(Color.parseColor("#d43d3d"))
        mCommonNavigator = CommonNavigator(requireContext())
        mCommonNavigator.setSkimOver(true)
        mCommonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.setText(mDataList[index])
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"))
                clipPagerTitleView.setClipColor(Color.WHITE)
                clipPagerTitleView.setOnClickListener(View.OnClickListener { binding.viewPager.setCurrentItem(index) })
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        })
        binding.indicator1.setNavigator(mCommonNavigator)
        ViewPagerHelper.bind(binding.indicator1, binding.viewPager)

        toast = Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT)
    }

    private fun randomPage() {
        mDataList.clear()
        val total = Random().nextInt(CHANNELS.size)
        for (i in 0..total) {
            mDataList.add(CHANNELS[i])
        }
        mCommonNavigator.notifyDataSetChanged() // must call firstly
        mExamplePagerAdapter.notifyDataSetChanged()
        toast.setText("" + mDataList.size + " page")
        toast.show()
    }
}