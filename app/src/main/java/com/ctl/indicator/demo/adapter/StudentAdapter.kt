package com.ctl.indicator.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.ctl.indicator.demo.R
import com.ctl.indicator.demo.base.DataBindingViewHolder
import com.ctl.indicator.demo.base.dowithTry
import com.ctl.indicator.demo.databinding.AdapterStudentItemBinding
import com.ctl.indicator.demo.room.StudentEntity

/**
 * created by : chentl
 * Date: 2020/07/13
 */
class StudentAdapter : PagingDataAdapter<StudentEntity, StudentAdapter.StudentViewHolder> (StudentEntity.diffCallback){

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        dowithTry {
            val data = getItem(position)
            data?.let {
                holder.bindData(it, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_student_item,parent,false))
    }

    class StudentViewHolder(view: View) : DataBindingViewHolder<StudentEntity>(view) {
        val binding: AdapterStudentItemBinding by viewHolderBinding(view)
        override fun bindData(data: StudentEntity, position: Int) {
            binding.apply {
                studentEntity = data
                executePendingBindings()
            }
        }
    }

}