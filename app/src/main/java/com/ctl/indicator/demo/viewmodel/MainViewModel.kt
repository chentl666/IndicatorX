package com.ctl.indicator.demo.viewmodel

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.findNavController
import com.ctl.indicator.demo.R
import com.ctl.indicator.demo.fragment.MainFragmentDirections
import com.ctl.indicator.demo.livedata.MyLiveData

/**
 * created by : chentl
 * Date: 2020/06/30
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context = application

    private val clickContent: MyLiveData<String> by lazy { MyLiveData<String>() }

    init {
        clickContent.value = ""
    }

    fun getClickContentMethod(): MyLiveData<String> {
        return clickContent
    }

    fun scrollableTab(view: View) {
        clickContent.value = "scrollableTab"
        view.findNavController().navigate(R.id.scrollableTabFragment)
//        val action = MainFragmentDirections.actionMainFragmentToScrollableTabFragment("scrollableTab")
//        view.findNavController().navigate(action)
    }

    fun fixedTab(view: View) {
        clickContent.value = "fixedTab"
        val bundle = Bundle()
        bundle.putString("title", "fixedTab")
        view.findNavController().navigate(R.id.fixedTabFragment, bundle)
    }

    fun dynamicTab(view: View) {
        clickContent.value = "dynamicTab"
        view.findNavController().navigate(R.id.dynamicTabFragment)
    }

    fun onlyIndicator(view: View) {
        clickContent.value = "onlyIndicator"
        view.findNavController().navigate(R.id.noTabOnlyIndicatorFragment)
    }

    fun workWithFragmentContainer(view: View) {
        clickContent.value = "workWithFragmentContainer"
        view.findNavController().navigate(R.id.containerFragment)
    }

    fun tabWithBadgeView(view: View) {
        clickContent.value = "tabWithBadgeView"
        view.findNavController().navigate(R.id.badgeTabFragment)
    }

    fun loadCustomLayout(view: View) {
        clickContent.value = "loadCustomLayout"
        view.findNavController().navigate(R.id.loadCustomLayoutFragment)
    }

    fun customNavigator(view: View) {
        clickContent.value = "customNavigator"
        view.findNavController().navigate(R.id.circleNavigatorFragment)
    }

    fun recyclerViewRoom(view: View) {
        view.findNavController().navigate(R.id.studentFragment)
    }
}