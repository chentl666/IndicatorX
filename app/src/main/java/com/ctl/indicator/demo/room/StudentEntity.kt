package com.ctl.indicator.demo.room

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * created by : chentl
 * Date: 2020/07/13
 * , indices = [Index(value = ["s_id"], unique = true)]
 */
@Entity(tableName = "StudentTable")
data class StudentEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int? = 0,
    @ColumnInfo(name = "s_no")
    var sno: Int? = null,
    @ColumnInfo(name = "s_name")
    var name: String? = null,
    var age: String? = null
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<StudentEntity>() {
            override fun areItemsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean =
                oldItem == newItem
        }
    }
}


