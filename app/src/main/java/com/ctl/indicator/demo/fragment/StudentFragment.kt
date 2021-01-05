package com.ctl.indicator.demo.fragment

import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctl.indicator.demo.adapter.StudentAdapter
import com.ctl.indicator.demo.base.BaseFragment
import com.ctl.indicator.demo.databinding.FragmentStudentBinding
import com.ctl.indicator.demo.room.StudentDao
import com.ctl.indicator.demo.room.StudentEntity
import com.ctl.indicator.demo.viewmodel.StudentViewModel


class StudentFragment : BaseFragment() {
    private lateinit var sdao: StudentDao
    private lateinit var binding: FragmentStudentBinding

    private val viewModel: StudentViewModel by activityViewModels()
    private lateinit var mAdapter: StudentAdapter

    override fun setLayoutView(): View? {
        binding = FragmentStudentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        binding.toolbar.txtToolbarTitle.text = "数据库room"
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter = StudentAdapter()
        binding.recyclerView.adapter = mAdapter
    }

    override fun initData() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
//        context?.let {
//            val mDb = MyDateBase.getInstance(it)
//            sdao = mDb.studentDao()
//
//        }
    }

    override fun initListener() {
        viewModel.getAllStudent().observe(this, onChanged = {
            Toast.makeText(activity,"11",Toast.LENGTH_SHORT).show()
//            var txtString = StringBuilder()
//            for (item in it) {
//                txtString
//                    .append(item.id)
//                    .append(item.sno)
//                    .append(item.name)
//                    .append(item.age)
//                    .append("\n")
//            }
//            binding.txtInfo.text = txtString
        })
        binding.toolbar.imgToolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAdd.setOnClickListener {
            val stu1 = StudentEntity(3, 1001, "张三", "18岁")
            val stu2 = StudentEntity(4, 1002, "李四", "28岁")
            viewModel.insert(stu1, stu2)
//            var ast = viewModel.getAllStudent().value
//            mAdapter.refreshData(ast!!)
//            sdao.insertStudents(stu1, stu2)
//            updateView()
        }
    }

    fun updateView() {
        val list = sdao.getAllStudent()
        var txtString = StringBuilder()
        for (item in list) {
            txtString
                .append(item.id)
                .append(item.sno)
                .append(item.name)
                .append(item.age)
                .append("\n")
        }
        binding.txtInfo.text = txtString
    }

}