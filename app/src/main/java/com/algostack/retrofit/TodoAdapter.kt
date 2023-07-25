package com.algostack.retrofit

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.algostack.retrofit.databinding.ActivityMainBinding
import com.algostack.retrofit.databinding.ItemtodoBinding

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class  TodoViewHolder(val binding: ItemtodoBinding) : RecyclerView.ViewHolder(binding.root)


    private val diffrentCallback = object :DiffUtil.ItemCallback<Todo>() {

        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
           return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffrentCallback)
    var todo:List<Todo>
        get() = differ.currentList
        set(value){
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(ItemtodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount() = todo.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
       holder.binding.apply {

           val todo = todo[position]
           tvTitile.text = todo.title
           tvCheckbox.isChecked = todo.completed
       }
    }

}