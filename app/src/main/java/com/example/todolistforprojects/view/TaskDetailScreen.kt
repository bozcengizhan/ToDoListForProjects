package com.example.todolistforprojects.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.ui.theme.ToDoListForProjectsTheme
import com.example.todolistforprojects.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun taskDetailScreen(
    task: Task,
    viewModel: TaskViewModel = viewModel(),
    navController: NavController
){
    val formattedDate = task.startDate?.toDate()?.let { date ->
        val sdf = java.text.SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("tr"))
        sdf.format(date)
    } ?: "Tarih yok"

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier= Modifier.weight(0.1f))
            Text(task.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier= Modifier.weight(1.5f))

            Text(task.description)
            Spacer(modifier= Modifier.weight(0.25f))

            Text("Kalan Gün: ${task.totalDays}")

            Spacer(modifier = androidx.compose.ui.Modifier.height(20.dp))

            Spacer(modifier= Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.deleteTask(task)
                    navController.popBackStack() // silince liste ekranına dön
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Görevi Sil")
            }
            Spacer(modifier= Modifier.weight(0.5f))
            Text(formattedDate, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium, fontFamily = FontFamily.SansSerif)

        }
    }
}


@Preview(showBackground = true)
@Composable
fun TaskDetailScreenPreview() {
    val sampleTask = Task(
        id = "1",
        name = "Örnek Görev",
        description = "Bu görev bir preview için oluşturuldu.",
        totalDays = 5,
        startDate = com.google.firebase.Timestamp.now()
    )

    // Preview için ViewModel veya NavController olmadan çağırıyoruz
    ToDoListForProjectsTheme {
        taskDetailScreenPreview(task = sampleTask)
    }
}

// Preview için ayrı bir composable oluşturuyoruz, ViewModel ve NavController olmadan
@Composable
fun taskDetailScreenPreview(task: Task) {
    val formattedDate = task.startDate?.toDate()?.let { date ->
        val sdf = java.text.SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("tr"))
        sdf.format(date)
    } ?: "Tarih yok"


    androidx.compose.foundation.layout.Box(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
        androidx.compose.foundation.layout.Column(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier= Modifier.weight(0.1f))
            Text(task.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier= Modifier.weight(1.5f))

            Text(task.description)
            Spacer(modifier= Modifier.weight(0.25f))

            Text("Kalan Gün: ${task.totalDays}")

            Spacer(modifier = androidx.compose.ui.Modifier.height(20.dp))

            Spacer(modifier= Modifier.weight(1f))

            Text("Görevi Sil (Preview)", color = Color.Red)

            Spacer(modifier= Modifier.weight(0.5f))
            Text(formattedDate, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium, fontFamily = FontFamily.SansSerif)
        }
    }
}
