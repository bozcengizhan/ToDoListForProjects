package com.example.todolistforprojects.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.viewmodel.TaskViewModel
import com.google.gson.Gson

@Composable
fun CompletedTasksScreen(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
) {
    val completedTasks by viewModel.completedTasks.collectAsState()

    // Ekran açılır açılmaz verileri çek
    LaunchedEffect(true) {
        viewModel.fetchCompletedTasks()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Tamamlanan Görevler",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (completedTasks.isEmpty()) {
            Text(
                text = "Tamamlanan görev bulunmuyor.",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                items(completedTasks) { task ->

                    CompletedTaskItem(
                        task = task,
                        onClick = {
                            navController.navigate("completedTaskDetailScreen/${Gson().toJson(task)}")
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun CompletedTaskItem(task: Task, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE7FFE7), RoundedCornerShape(12.dp))
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = task.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Ekleyen: ${task.creatorEmail}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Durum: Tamamlandı ✔️",
            color = Color(0xFF2E7D32),
            fontWeight = FontWeight.Bold
        )
    }
}

