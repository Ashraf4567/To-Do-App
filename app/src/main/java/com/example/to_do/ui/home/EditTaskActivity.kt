package com.example.to_do.ui.home

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AlertDialog
import com.example.to_do.MyDataBase
import com.example.to_do.R
import com.example.to_do.databinding.ActivityEditTaskBinding
import com.example.to_do.model.Constants
import com.example.to_do.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditTaskActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityEditTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initParams()
        bindTaskData()
        viewBinding.editBtn.setOnClickListener{
            updateToDo()
        }


    }

    private fun updateToDo() {
        task?.title = viewBinding.title.text.toString()
        task?.description = viewBinding.description.text.toString()
        MyDataBase.getInstance(this).tasksDao().updateTask(task!!)
        finish()

    }



    private fun bindTaskData() {
        viewBinding.title.text = task?.title?.let { Editable.Factory.getInstance().newEditable(it) }
        viewBinding.description.text = task?.description.let { Editable.Factory.getInstance().newEditable(it) }

        val date = convertLongToDate(task?.dateTime)
        viewBinding.dateTime.text=date
    }


    private fun convertLongToDate(dateTime: Long?): String {
        val date = Date(dateTime!!)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(date)

    }

    var task: Task? = null
    private fun initParams() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            task = intent.getSerializableExtra(Constants.TASK_EXTRA,Task::class.java)
        }else{
            task = intent.getSerializableExtra(Constants.TASK_EXTRA) as Task
        }
    }






}