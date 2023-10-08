package com.example.to_do

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.to_do.dao.TasksDao
import com.example.to_do.model.Task

@Database(entities = [Task::class] ,
                    version = 2 ,
                    //exportSchema = true ,
//                    autoMigrations = [
//                        AutoMigration(from = 1 , to = 2)
//                    ]
)

abstract class MyDataBase: RoomDatabase() {
    abstract fun tasksDao():TasksDao

    companion object{
        private var instance: MyDataBase?= null


        fun getInstance(context: Context): MyDataBase{
            if(instance == null){
                //init
                instance = Room.databaseBuilder(context.applicationContext,
                MyDataBase::class.java,
                "tasksDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}