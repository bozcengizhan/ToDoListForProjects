package com.example.todolistforprojects.model

import com.google.firebase.Timestamp

data class Task(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val totalDays: Int = 0,          // Başlangıçta verilen toplam gün sayısı
    val startDate: Timestamp? = null // Görevin başlama tarihi
)
