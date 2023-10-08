package com.example.to_do.ui.home.tabs.tasks_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.to_do.MyDataBase
import com.example.to_do.databinding.FragmentTasksListBinding
import com.example.to_do.model.Constants
import com.example.to_do.model.Task
import com.example.to_do.ui.home.EditTaskActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.util.Calendar


class TasksListFragment:Fragment() {

    lateinit var viewBinding: FragmentTasksListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTasksListBinding.inflate(inflater,container,false)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onStart() {
        super.onStart()
        loadTasks()
    }

     fun loadTasks() {
         context?.let {
             val tasks:List<Task> = MyDataBase.getInstance(it)
                 .tasksDao().getTasksByDate(selectedDate.timeInMillis)
             tasksAdapter.bindTasks(tasks.toMutableList())
         }


    }

    private val tasksAdapter = TasksAdapter(null)
    var selectedDate: Calendar = Calendar.getInstance()

    private fun initViews() {
        viewBinding.recyclerView.adapter = tasksAdapter

        selectedDate.set(Calendar.HOUR,0)
        selectedDate.set(Calendar.MINUTE,0)
        selectedDate.set(Calendar.SECOND,0)
        selectedDate.set(Calendar.MILLISECOND,0)

        tasksAdapter.onItemClickListener = TasksAdapter.OnItemClickListener{
            position, task ->
            showTaskDetails(task)
        }

        tasksAdapter.onDoneClickListener = TasksAdapter.OnItemClickListener{
            position, task ->

            taskDone(task,position)

        }

        viewBinding.calendarView.setSelectedDate(CalendarDay.today())

        viewBinding.calendarView.setOnDateChangedListener(OnDateSelectedListener{
            widget, date, selected ->
            if (selected){
                //reload tasks on the selected date
                selectedDate.set(Calendar.YEAR,date.year)
                selectedDate.set(Calendar.MONTH,date.month-1)
                selectedDate.set(Calendar.DAY_OF_MONTH,date.day)

                loadTasks()

            }
        })

        tasksAdapter.onItemSwipeListener = TasksAdapter.OnItemClickListener{
            position, task ->

            deleteTask(task , position)
            tasksAdapter.taskDeleted(task)

        }

    }

    private fun deleteTask(task: Task, position: Int) {
        MyDataBase.getInstance(requireContext())
            .tasksDao().deleteTask(task)

    }


    private fun taskDone(task: Task ,position: Int ) {
        task.isDone = true
        MyDataBase.getInstance(requireContext())
            .tasksDao().updateTask(task)


    }



    private fun showTaskDetails(task: Task) {

        val intent = Intent(activity , EditTaskActivity::class.java)
        intent.putExtra(Constants.TASK_EXTRA,task)
        startActivity(intent)

    }

}