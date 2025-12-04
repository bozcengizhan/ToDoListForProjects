package com.example.todolistforprojects.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository = TaskRepository()
) : ViewModel() {

    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> = _taskList

    init {
        // init içinde yükleme yapılıyor
        repository.getTasksRealTime { tasks ->
            _taskList.value = tasks
        }
    }

    fun addTask(name: String, description: String, date: String, time: String) {
        val task = Task(id = "", name = name, description = description, date = date, time = time, status = "Pending")
        repository.addTask(task)
    }
}

