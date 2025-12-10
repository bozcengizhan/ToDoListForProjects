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

    private val _completedTasks = MutableStateFlow<List<Task>>(emptyList())
    val completedTasks: StateFlow<List<Task>> get() = _completedTasks

    private val _failedTasks = MutableStateFlow<List<Task>>(emptyList())
    val failedTasks: StateFlow<List<Task>> get() = _failedTasks

    fun fetchCompletedTasks() {
        repository.getCompletedTasksRealTime { list ->
            _completedTasks.value = list
        }
    }

    fun fetchFailedTasks() {
        repository.getFailedTasksRealTime { list ->
            _failedTasks.value = list
        }
    }

    fun fetchTasks() {
        repository.getTasksRealTime { taskListFromDb ->

            val activeTasks = mutableListOf<Task>()
            val failedTasks = mutableListOf<Task>()

            taskListFromDb.forEach { task ->
                val remaining = calculateRemainingDays(task)

                if (remaining <= 0) {
                    failTask(task)  // 🔥 burada sorun çözülmüş olacak
                    failedTasks.add(task.copy(totalDays = 0))
                } else {
                    activeTasks.add(task.copy(totalDays = remaining))
                }
            }

            _taskList.value = activeTasks
            _failedTasks.value = failedTasks
        }
    }

    fun failTask(task: Task) {
        repository.failTask(task) {
            // Başarılı olursa tekrar fetch edelim
            fetchTasks()
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

    fun addTask(name: String, description: String, totalDays: Int, creatorEmail: String) {
        val newTask = Task(
            name = name,
            description = description,
            totalDays = totalDays,
            startDate = null, // Firestore server timestamp ile atanacak
            creatorEmail = creatorEmail
        )
        repository.addTask(newTask)
    }

    fun deleteTask(task: Task, onComplete: (Boolean) -> Unit = {}) {
        repository.deleteTask(task) { success ->
            if (success) {
                // Eğer istersen local StateFlow listesini de güncelleyebilirsin
                _taskList.value = _taskList.value.filter { it.id != task.id }
            }
            onComplete(success)
        }
    }

    fun deleteCompletedTask(task: Task, onComplete: (Boolean) -> Unit = {}) {
        repository.deleteCompletedTask(task) { success ->
            if (success) {
                // Eğer istersen local StateFlow listesini de güncelleyebilirsin
                _taskList.value = _taskList.value.filter { it.id != task.id }
            }
            onComplete(success)
        }
    }

    fun deleteFailedTask(task: Task, onComplete: (Boolean) -> Unit = {}) {
        repository.deleteFailedTask(task) { success ->
            if (success) {
                // Eğer istersen local StateFlow listesini de güncelleyebilirsin
                _taskList.value = _taskList.value.filter { it.id != task.id }
            }
            onComplete(success)
        }
    }


    fun completeTask(task: Task) {
        repository.markTaskCompleted(task) { success ->
            if (success) {
                fetchTasks() // listeleri güncelle
            }
        }
    }

    fun cleanExpiredTasks() {
        repository.cleanExpiredTasks { success ->
            if (success) {
                fetchCompletedTasks()
                fetchFailedTasks()
            }
        }
    }


}
