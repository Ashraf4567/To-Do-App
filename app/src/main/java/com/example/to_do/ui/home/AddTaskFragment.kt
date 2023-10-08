package com.example.to_do.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.to_do.MyDataBase
import com.example.to_do.databinding.FragmentAddTaskBinding
import com.example.to_do.model.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskFragment:BottomSheetDialogFragment() {
    lateinit var viewBinding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddTaskBinding.inflate(layoutInflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.addTaskBtn.setOnClickListener{

            createTask()
        }
        viewBinding.dateTimeContainer.setOnClickListener{
            showDatePickerDialog()
        }

    }

    val calender = Calendar.getInstance()
    private fun showDatePickerDialog() {
        context?.let { val dialog  = DatePickerDialog(it)

            dialog.setOnDateSetListener{ datePicker, year, month, day ->
                viewBinding.dateTime.setText("$day-${month+1}-$year")
                calender.set(year , month , day)
                // to ignore time
                calender.set(Calendar.HOUR,0)
                calender.set(Calendar.MINUTE,0)
                calender.set(Calendar.SECOND,0)
                calender.set(Calendar.MILLISECOND,0)
            }
            dialog.show() }
    }

    private fun valid(): Boolean {
        var isValid = true
        if(viewBinding.title.text.toString().isNullOrBlank()){
            viewBinding.titleContainer.error = "please Enter Title"
            isValid = false
        }else{
            viewBinding.titleContainer.error =null
        }
        if(viewBinding.description.text.toString().isNullOrBlank()){
            viewBinding.descContainer.error = "please Enter description"
            isValid = false
        }else{
            viewBinding.descContainer.error =null
        }
        if(viewBinding.dateTime.text.toString().isNullOrBlank()){
            viewBinding.dateTimeContainer.error = "please chose Date & Time"
            isValid = false
        }else{
            viewBinding.dateTimeContainer.error =null
        }
        return isValid

    }

    private fun createTask() {
        if(!valid()){
            return
        }
        val task = Task(title = viewBinding.title.text.toString(),
                        description = viewBinding.description.text.toString(),
                        dateTime = calender.timeInMillis
                        )
        MyDataBase.getInstance(requireContext())
            .tasksDao()
            .insertTask(task)
            onTaskAddedListener?.onTaskAdded()
            dismiss()
    }

    var onTaskAddedListener: OnTaskAddedListener? = null

    fun interface OnTaskAddedListener{
        fun onTaskAdded()
    }

}