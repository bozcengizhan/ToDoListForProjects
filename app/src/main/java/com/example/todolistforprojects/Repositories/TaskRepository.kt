package com.example.todolistforprojects.repository

import com.example.todolistforprojects.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TaskRepository {

    private val db = FirebaseFirestore.getInstance()


    fun markTaskCompleted(task: Task, onComplete: (Boolean) -> Unit) {

        val completedRef = db.collection("completed_tasks").document(task.id)
        val expireDate = com.google.firebase.Timestamp.now() // şimdi
            .toDate()
            .apply { time += 7L * 24 * 60 * 60 * 1000 } // +7 gün

        val completedTaskMap = hashMapOf(
            "id" to task.id,
            "name" to task.name,
            "description" to task.description,
            "totalDays" to task.totalDays,
            "startDate" to task.startDate,
            "creatorEmail" to task.creatorEmail,
            "status" to "completed",
            "expireAt" to com.google.firebase.Timestamp(expireDate)
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
            .orderBy("startDate", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val tasks = snapshot.toObjects(Task::class.java)
                    onDataChanged(tasks)
                }
            }
    }


    fun getFailedTasksRealTime(onDataChanged: (List<Task>) -> Unit) {
        db.collection("failedTasks")
            .orderBy("startDate", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val tasks = snapshot.toObjects(Task::class.java)
                    onDataChanged(tasks)
                }
            }
    }

    fun failTask(task: Task, onComplete: (Boolean) -> Unit = {}) {
        val failedRef = db.collection("failedTasks").document(task.id)
        val expireDate = com.google.firebase.Timestamp.now() // şimdi
            .toDate()
            .apply { time +=  30 * 1000 } // +3 gün

        val taskMap = hashMapOf(
            "id" to task.id,
            "name" to task.name,
            "description" to task.description,
            "totalDays" to 0,
            "startDate" to task.startDate,
            "creatorEmail" to task.creatorEmail,
            "status" to "failed",
            "expireAt" to com.google.firebase.Timestamp(expireDate)
        )

        failedRef.set(taskMap)
            .addOnSuccessListener {
                // Orijinal koleksiyondan sil
                db.collection("tasks").document(task.id)
                    .delete()
                    .addOnSuccessListener { onComplete(true) }
                    .addOnFailureListener { onComplete(false) }
            }
            .addOnFailureListener { onComplete(false) }
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
            .orderBy("startDate", com.google.firebase.firestore.Query.Direction.DESCENDING)
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

    fun deleteCompletedTask(task: Task, onComplete: (Boolean) -> Unit) {
        if (task.id.isEmpty()) {
            onComplete(false)
            return
        }

        db.collection("completed_tasks").document(task.id)
            .delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun deleteFailedTask(task: Task, onComplete: (Boolean) -> Unit) {
        if (task.id.isEmpty()) {
            onComplete(false)
            return
        }

        db.collection("failedTasks").document(task.id)
            .delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun cleanExpiredTasks(onComplete: (Boolean) -> Unit = {}) {
        val now = com.google.firebase.Timestamp.now()

        val collections = listOf("completed_tasks", "failedTasks")
        var failures = 0
        var pending = collections.size

        collections.forEach { col ->
            db.collection(col)
                .whereLessThan("expireAt", now)
                .get()
                .addOnSuccessListener { snapshot ->
                    val batch = db.batch()

                    for (doc in snapshot.documents) {
                        batch.delete(doc.reference)
                    }

                    batch.commit()
                        .addOnFailureListener { failures++ }
                        .addOnCompleteListener {
                            pending--
                            if (pending == 0) {
                                onComplete(failures == 0)
                            }
                        }

                }
                .addOnFailureListener {
                    failures++
                    pending--
                    if (pending == 0) {
                        onComplete(false)
                    }
                }
        }
    }


}
