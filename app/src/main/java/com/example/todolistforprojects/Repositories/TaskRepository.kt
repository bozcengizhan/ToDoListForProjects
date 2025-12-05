package com.example.todolistforprojects.repository

import com.example.todolistforprojects.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TaskRepository {

    private val db = FirebaseFirestore.getInstance()


    fun markTaskCompleted(task: Task, onComplete: (Boolean) -> Unit) {

        val completedRef = db.collection("completed_tasks").document(task.id)

        val completedTaskMap = hashMapOf(
            "id" to task.id,
            "name" to task.name,
            "description" to task.description,
            "totalDays" to task.totalDays,
            "startDate" to task.startDate,
            "creatorEmail" to task.creatorEmail,
            "status" to "completed"
        )

        // önce completed listesine ekle
        completedRef.set(completedTaskMap)
            .addOnSuccessListener {

                // sonra normal listeden sil
                db.collection("tasks").document(task.id)
                    .delete()
                    .addOnSuccessListener { onComplete(true) }
                    .addOnFailureListener { onComplete(false) }
            }
            .addOnFailureListener { onComplete(false) }
    }

    fun getCompletedTasksRealTime(onDataChanged: (List<Task>) -> Unit) {
        db.collection("completed_tasks")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val tasks = snapshot.toObjects(Task::class.java)
                    onDataChanged(tasks)
                }
            }
    }



    fun addTask(task: Task) {
        val newDoc = db.collection("tasks").document()
        val taskWithId = task.copy(id = newDoc.id)

        // startDate'i Firebase server timestamp ile ayarlıyoruz
        val taskMap = hashMapOf(
            "id" to taskWithId.id,
            "name" to taskWithId.name,
            "description" to taskWithId.description,
            "totalDays" to taskWithId.totalDays,
            "startDate" to com.google.firebase.firestore.FieldValue.serverTimestamp(),
            "creatorEmail" to taskWithId.creatorEmail,
            "status" to taskWithId.status
        )

        newDoc.set(taskMap)
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
