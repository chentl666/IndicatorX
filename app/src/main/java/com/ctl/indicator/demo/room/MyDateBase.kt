package com.ctl.indicator.demo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * created by : chentl
 * Date: 2020/07/13
 */
@Database(entities = [StudentEntity::class], version = 2, exportSchema = false)
abstract class MyDateBase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: MyDateBase? = null
        fun getInstance(context: Context): MyDateBase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, MyDateBase::class.java, "MyDatabase.db")
//                .allowMainThreadQueries()
                .build()
    }

    abstract fun studentDao(): StudentDao
}