package com.example.to_do.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.to_do.R
import com.example.to_do.databinding.ActivityHomeBinding
import com.example.to_do.ui.home.tabs.SettingsFragment
import com.example.to_do.ui.home.tabs.tasks_list.TasksListFragment
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityHomeBinding
    var tasksListFragmentRef: TasksListFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.bottomNavigation
            .setOnItemSelectedListener {
                when(it.itemId){
                    R.id.navigation_tasks_list->{
                        tasksListFragmentRef = TasksListFragment()
                        showFragment(tasksListFragmentRef!!)
                    }
                    R.id.navigation_settings->{

                        showFragment(SettingsFragment())
                    }
                }
                return@setOnItemSelectedListener true
            }
        viewBinding.bottomNavigation.selectedItemId = R.id.navigation_tasks_list
        viewBinding.addTask.setOnClickListener{
            showAddTaskBottomSheet()
        }
    }

    private fun showAddTaskBottomSheet() {
        val addTaskSheet = AddTaskFragment()
        addTaskSheet.onTaskAddedListener = AddTaskFragment.OnTaskAddedListener {
            Snackbar.make(viewBinding.root,
            "Task Added Successfully" ,1000)
                .show()
            //notify Tasks list fragment
            tasksListFragmentRef?.loadTasks()

        }
        addTaskSheet.show(supportFragmentManager,"")
    }

    private fun showFragment(fragment: Fragment) {

        supportFragmentManager
            .beginTransaction().replace(R.id.fragment_container,fragment)
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            .commit()
    }


}