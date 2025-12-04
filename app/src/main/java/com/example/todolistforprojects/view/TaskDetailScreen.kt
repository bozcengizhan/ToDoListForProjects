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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.ui.theme.ToDoListForProjectsTheme
import com.example.todolistforprojects.viewmodel.TaskViewModel

@Composable
fun taskDetailScreen(
    task: Task,
    viewModel: TaskViewModel = viewModel(),
    navController: NavController
){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(task.name)
            Text(task.description)
            Text("Kalan Gün: ${task.totalDays}")

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.deleteTask(task)
                    navController.popBackStack() // silince liste ekranına dön
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Görevi Sil")
            }
        }
    }
}




/*

@Preview(showBackground = true)
@Composable
fun taskDetailScreenPreview() {
    ToDoListForProjectsTheme {
        val taskList = ArrayList<Task>()
        val task1 = Task("1","İlk işimiz","ilk işimizi yapıyoruz","01.02.2024","12:00","Yapıldı")
        val task2 = Task("2","İkinci işimiz","İkinci işimizi yapıyoruz","05.02.2023","13:00","Yapıldı")
        val task3 = Task("3","Üçüncü işimiz","Üçüncü işimizi yapıyoruz","21.06.2021","14:00","Yapılmadı")
        val task4 = Task("4","Dördüncü işimiz","Dördüncü işimizi yapıyoruz","02.12.2022","15:00","Yapıldı")
        val task5 = Task("5","Son işimiz","Son işimizi yapıyoruz","11.01.2013","22:00","Yapıldı")

        taskDetailScreen(task = task1)
    }
}

 */