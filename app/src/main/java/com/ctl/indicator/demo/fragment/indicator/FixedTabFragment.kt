package com.ctl.indicator.demo.fragment.indicator

import android.view.View
import androidx.navigation.fragment.findNavController
import com.ctl.indicator.demo.R
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentFixedTabBinding
import com.ctl.indicator.demo.lifecycle.CustomLifecycle


class FixedTabFragment : BaseFragment() {
    private lateinit var binding: FragmentFixedTabBinding

    override fun setLayoutView(): View? {
        binding = FragmentFixedTabBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        lifecycle.addObserver(CustomLifecycle())
    }

    override fun initData() {
        binding.toolbar.txtToolbarTitle.text = "FixedTabFragment"
    }

    override fun initListener() {
        binding.txtClick.setOnClickListener {
            findNavController().navigate(R.id.fixedTabFragment)
        }
        binding.toolbar.imgToolbarBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}