package com.ctl.indicator.demo.fragment.indicator

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ctl.indicator.demo.R
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentScrollableTabBinding
import com.ctl.indicator.demo.lifecycle.CustomLifecycle

/**
 * created by : chentl
 * Date: 2020/07/01
 */
class ScrollableTabFragment : BaseFragment() {

    private lateinit var binding: FragmentScrollableTabBinding
    private val safeArgs: ScrollableTabFragmentArgs by navArgs()

    override fun setLayoutView(): View? {
        binding = FragmentScrollableTabBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        lifecycle.addObserver(CustomLifecycle())
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
}