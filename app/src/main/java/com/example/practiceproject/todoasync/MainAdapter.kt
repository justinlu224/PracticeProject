package com.example.practiceproject.todoasync

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceproject.databinding.MainAdapterBinding

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var list = mutableListOf<Todo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            MainAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class MainViewHolder(val binding: MainAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(todo: Todo) {
        binding.checkbox.text = todo.title
    }

}
