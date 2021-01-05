package com.ctl.indicator.demo.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * created by : chentl
 * Date: 2020/07/13
 */
abstract class BaseAdapter<T>(var data: List<T> = listOf()) : RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),viewType,parent,false))
    }

    override fun getItemCount(): Int {
        return  data.size
    }
    fun refreshData(newData :MutableList<T>){
        this.data = newData
        this.notifyDataSetChanged()
    }
}