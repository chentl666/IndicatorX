package com.ctl.indicator.demo.room

import android.content.Context
import android.os.AsyncTask
import com.ctl.indicator.demo.livedata.MyLiveData

/**
 * created by : chentl
 * Date: 2020/07/13
 */
class StudentRepository(context: Context) {
    private var studentDao: StudentDao
    private var allStudentLive: MyLiveData<MutableList<StudentEntity>> = MyLiveData()

    init {
        val studentDb = MyDateBase.getInstance(context)
        studentDao = studentDb.studentDao()
        allStudentLive.value = AllStudentAsyncTask(studentDao).execute().get()
    }

    fun getAllStudentLive(): MyLiveData<MutableList<StudentEntity>> {
        return allStudentLive
    }

    fun insert(vararg students: StudentEntity) {
        InsertAsyncTask(studentDao).execute(*students)
        allStudentLive.value = AllStudentAsyncTask(studentDao).execute().get()
    }

    fun update(vararg students: StudentEntity) {
        UpdateAsyncTask(studentDao).execute(*students)
        allStudentLive.value = AllStudentAsyncTask(studentDao).execute().get()
    }

    fun delete(vararg students: StudentEntity) {
        deleteAsyncTask(studentDao).execute(*students)
        allStudentLive.value = AllStudentAsyncTask(studentDao).execute().get()
    }

    fun deleteAll() {
        clearAsyncTask(studentDao).execute()
        allStudentLive.value = AllStudentAsyncTask(studentDao).execute().get()
    }

    class AllStudentAsyncTask(private var studentDao: StudentDao) : AsyncTask<Void, Void, MutableList<StudentEntity>>() {
        override fun doInBackground(vararg params: Void?): MutableList<StudentEntity>? {
            return studentDao.getAllStudent()
        }
    }

    class InsertAsyncTask(private var studentDao: StudentDao) : AsyncTask<StudentEntity, Void, Void>() {
        override fun doInBackground(vararg students: StudentEntity): Void? {
            studentDao.insertStudents(*students)
            return null
        }
    }

    class UpdateAsyncTask(private var studentDao: StudentDao) : AsyncTask<StudentEntity, Void, Void>() {
        override fun doInBackground(vararg students: StudentEntity): Void? {
            studentDao.updateStudents(*students)
            return null
        }
    }

    class deleteAsyncTask(private var studentDao: StudentDao) : AsyncTask<StudentEntity, Void, Void>() {
        override fun doInBackground(vararg students: StudentEntity): Void? {
            studentDao.deleteStudents(*students)
            return null
        }
    }

    class clearAsyncTask(private var studentDao: StudentDao) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            studentDao.deleteAllStudent()
            return null
        }
    }
}