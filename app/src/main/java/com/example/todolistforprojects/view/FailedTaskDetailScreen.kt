package com.example.todolistforprojects.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.viewmodel.TaskViewModel
import java.util.Locale


@Composable
fun failedTaskDetailScreen(navController: NavController, viewModel: TaskViewModel = viewModel(), task: Task)
{
    val formattedDate = task.startDate?.toDate()?.let { date ->
        val sdf = java.text.SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("tr"))
        sdf.format(date)
    } ?: "Tarih yok"

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAA0A0)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier= Modifier.weight(0.1f))
            Text(task.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier= Modifier.weight(1.5f))

            Text(task.description, Modifier.width(350.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier= Modifier.weight(1f))

            Button(
                colors = ButtonColors(containerColor = Color.Red, contentColor = Color.Black,disabledContainerColor = Color.Red, disabledContentColor = Color.Black),
                onClick = {
                    viewModel.deleteFailedTask(task)
                    navController.popBackStack() // silince liste ekranına dön
                },
            ) {
                Text("Sil")
            }
            Spacer(modifier= Modifier.weight(0.5f))
            Text(formattedDate, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium, fontFamily = FontFamily.SansSerif)

        }
    }
}