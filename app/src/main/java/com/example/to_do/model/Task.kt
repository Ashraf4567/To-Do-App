package com.example.to_do.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "tasks")
data class Task(
    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    @ColumnInfo
    var title:String? = null,
    @ColumnInfo
    var description: String? = null,
    @ColumnInfo
    var dateTime: Long? = null,
    @ColumnInfo(name = "isDone")
    var isDone: Boolean = false

):Serializable
