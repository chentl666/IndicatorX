package com.ctl.indicator.demo.room

import androidx.room.*

/**
 * created by : chentl
 * Date: 2020/07/13
 */
@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudents(vararg students: StudentEntity)

    @Update
    fun updateStudents(vararg students: StudentEntity)

    @Delete
    fun deleteStudents(vararg student: StudentEntity)

    @Query("DELETE FROM StudentTable")
    fun deleteAllStudent()

    @Query("SELECT * FROM StudentTable ORDER BY id DESC")
    fun getAllStudent(): MutableList<StudentEntity>
}