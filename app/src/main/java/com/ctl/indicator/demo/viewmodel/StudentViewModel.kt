package com.ctl.indicator.demo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ctl.indicator.demo.livedata.MyLiveData
import com.ctl.indicator.demo.room.StudentEntity
import com.ctl.indicator.demo.room.StudentRepository

/**
 * created by : chentl
 * Date: 2020/07/13
 */
class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val studentRepository: StudentRepository by lazy { StudentRepository(application) }

    fun getAllStudent(): MyLiveData<MutableList<StudentEntity>> {
        return studentRepository.getAllStudentLive()
    }

    fun insert(vararg students: StudentEntity) {
        studentRepository.insert(*students)
    }

    fun update(vararg students: StudentEntity) {
        studentRepository.update(*students)
    }

    fun delete(vararg students: StudentEntity) {
        studentRepository.delete(*students)
    }

    fun deleteAll() {
        studentRepository.deleteAll()
    }
}