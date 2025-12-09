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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.ui.theme.ToDoListForProjectsTheme
import com.example.todolistforprojects.viewmodel.TaskViewModel
import com.google.gson.Gson

@Composable
fun failedTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
){
    val failedTasks by viewModel.failedTasks.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchFailedTasks()
    }

    LaunchedEffect(Unit) {
        viewModel.cleanExpiredTasks()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF85D5D))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ModernTopBar2(title = "Failed Tasks")


        Spacer(modifier = Modifier.height(20.dp))

        if (failedTasks.isEmpty()) {
            Text(
                text = "There are no failed tasks.",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                items(failedTasks) { task ->

                    FailedTaskItem (
                        task = task,
                        onClick = {
                            navController.navigate("failedTaskDetailScreen/${Gson().toJson(task)}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ModernTopBar2(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .shadow(12.dp, RoundedCornerShape(10.dp))  // gölge + yuvarlak köşe
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFFABAD),
                        Color(0xFFFF8688),
                        Color(0xFFFD6B6D),
                        Color(0xFFFD6B6D),
                        Color(0xFFFD6B6D),
                        Color(0xFFFF8688),
                        Color(0xFFFFABAD)
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
fun FailedTaskItem(task: Task, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFAA9A9), RoundedCornerShape(12.dp))
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
            text = "Status: Unsuccessful ✘",
            color = Color(0xFFF83C59),
            fontWeight = FontWeight.Bold
        )
    }
}



