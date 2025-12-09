package com.example.todolistforprojects.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
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

    LaunchedEffect(true) {
        viewModel.fetchCompletedTasks()
    }

    LaunchedEffect(Unit) {
        viewModel.cleanExpiredTasks()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF50E383))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ModernTopBar3(title = "Completed Tasks")

        Spacer(modifier = Modifier.height(5.dp))

        if (completedTasks.isEmpty()) {
            Text(
                text = "There are no completed tasks.",
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
fun ModernTopBar3(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .shadow(12.dp, RoundedCornerShape(10.dp))  // gölge + yuvarlak köşe
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF94E0B1),
                        Color(0xFF6FE79A),
                        Color(0xFF3BE576),
                        Color(0xFF3BE576),
                        Color(0xFF3BE576),
                        Color(0xFF6FE79A),
                        Color(0xFF94E0B1)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            ).border(3.dp, Color.Black, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize * 1.5f // %20 daha büyük
            )
        )
    }
}



@Composable
fun CompletedTaskItem(task: Task, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFA9FAC7), RoundedCornerShape(12.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = task.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Added By: ${task.creatorEmail}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Status: Successful ✔",
            color = Color(0xFF10DA1F),
            fontWeight = FontWeight.Bold
        )
    }
}

