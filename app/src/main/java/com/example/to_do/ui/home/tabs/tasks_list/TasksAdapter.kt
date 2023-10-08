package com.example.to_do.ui.home.tabs.tasks_list

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do.R
import com.example.to_do.databinding.TaskItemBinding
import com.example.to_do.model.Task

class TasksAdapter(var tasks:MutableList<Task>?): RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    class ViewHolder(val taskItemBinding: TaskItemBinding) : RecyclerView.ViewHolder(taskItemBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = TaskItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = tasks?.size?:0

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.taskItemBinding.title.text = tasks!![position].title
        holder.taskItemBinding.description.text = tasks!![position].description

        if (onItemClickListener!=null){
            holder.taskItemBinding.isDoneBtn.setOnClickListener(View.OnClickListener {
                onDoneClickListener!!.onClick(position,tasks!![position])
                holder.taskItemBinding.title.setTextColor(Color.GREEN)
                holder.taskItemBinding.isDoneBtn.setBackgroundColor(Color.GREEN)
                holder.taskItemBinding.guideline.setBackgroundColor(Color.GREEN)
            })
        }

        if (onItemClickListener!=null){
            if (!tasks!![position].isDone) {
                holder.taskItemBinding.title.setTextColor(R.color.lightPrimary)
                holder.taskItemBinding.isDoneBtn.setBackgroundColor(R.color.lightPrimary)
                holder.taskItemBinding.guideline.setBackgroundColor(R.color.lightPrimary)
            }
        }

        if (onItemClickListener!=null){
            holder.taskItemBinding.dragItem.setOnLongClickListener(View.OnLongClickListener {
                onItemClickListener!!.onClick(position, tasks!![position])
                    true
            })
        }

        if (onItemSwipeListener!=null){
            holder.taskItemBinding.deleteView.setOnClickListener(View.OnClickListener {
                holder.taskItemBinding.swipeLayout.close(true)
                onItemSwipeListener!!.onClick(position,tasks!![position])
            })
        }


    }




    var onItemClickListener: OnItemClickListener? = null


    fun interface OnItemClickListener{
        fun onClick(position: Int, task: Task)
    }

    var onDoneClickListener: OnItemClickListener?=null




    var onItemSwipeListener: OnItemClickListener? = null



    fun bindTasks(tasks: MutableList<Task>) {

        this.tasks = tasks
        notifyDataSetChanged()
    }

    fun taskDeleted(task: Task) {

        val position = tasks!!.indexOf(task)

        tasks!!.remove(task)
        notifyItemRemoved(position!!)
    }




}