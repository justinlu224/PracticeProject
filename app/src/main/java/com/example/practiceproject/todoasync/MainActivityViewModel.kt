package com.example.practiceproject.todoasync

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val todoList = mutableListOf<Todo>()
    val onNewTodo = MutableLiveData<Unit>()
    val onColorChange = MutableLiveData<Unit>()
    val todoLiveData: LiveData<List<Todo>> = MediatorLiveData<List<Todo>>().apply {
        addSource(onNewTodo, Observer {
            todoList.add(Todo("title $count", false))
            this.value = todoList
            count++
        })

        addSource(onColorChange){

        }
    }
    var count = 0
    fun addNewTodo() {
    }

}