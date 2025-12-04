package com.example.todolistforprojects.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TaskViewModel : ViewModel() {

    private val repository = TaskRepository()

    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> get() = _taskList

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        repository.getTasksRealTime { taskListFromDb ->
            val updatedTasks = taskListFromDb.map { task ->
                val remainingDays = calculateRemainingDays(task)
                task.copy(totalDays = remainingDays)
            }
            _taskList.value = updatedTasks
        }
    }

    private fun calculateRemainingDays(task: Task): Int {
        val start = task.startDate?.toDate() ?: return task.totalDays
        val today = java.util.Date()
        val diff = today.time - start.time
        val daysPassed = TimeUnit.MILLISECONDS.toDays(diff).toInt()
        val remaining = task.totalDays - daysPassed
        return if (remaining < 0) 0 else remaining
    }

    fun addTask(name: String, description: String, totalDays: Int) {
        val newTask = Task(
            name = name,
            description = description,
            totalDays = totalDays,
            startDate = com.google.firebase.Timestamp.now()
        )
        repository.addTask(newTask)
    }
}
