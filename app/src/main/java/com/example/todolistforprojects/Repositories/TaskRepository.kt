package com.example.todolistforprojects.repository

import com.example.todolistforprojects.model.Task
import com.google.firebase.firestore.FirebaseFirestore

class TaskRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addTask(task: Task) {
        val newDoc = db.collection("tasks").document()
        val taskWithId = task.copy(id = newDoc.id)
        newDoc.set(taskWithId)
    }

    fun getTasksRealTime(onDataChanged: (List<Task>) -> Unit) {
        db.collection("tasks")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val tasks = snapshot.toObjects(Task::class.java)
                    onDataChanged(tasks)
                }
            }
    }

    fun deleteTask(task: Task, onComplete: (Boolean) -> Unit) {
        if (task.id.isEmpty()) {
            onComplete(false)
            return
        }

        db.collection("tasks").document(task.id)
            .delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

}
