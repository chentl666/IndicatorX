package com.ctl.indicator.demo.fragment

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentMainBinding
import com.ctl.indicator.demo.lifecycle.CustomLifecycle
import com.ctl.indicator.demo.viewmodel.MainViewModel

/**
 * created by : chentl
 * Date: 2020/07/01
 */
class MainFragment : BaseFragment() {
    private val TAG: String = this.javaClass.simpleName
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding

    override fun setLayoutView(): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        lifecycle.addObserver(CustomLifecycle())

        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun initData() {
        viewModel.getClickContentMethod().observe(this, object : Observer<String?> {
            override fun onChanged(t: String?) {
//                Toast.makeText(activity, t, Toast.LENGTH_SHORT).show()
                Log.i(TAG, t)
            }
        })
    }

    override fun initListener() {

    }
}