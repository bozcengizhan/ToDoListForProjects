package com.example.todolistforprojects

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.ui.theme.ToDoListForProjectsTheme

@Composable
fun toDoListScreen(tasks: List<Task>){
    LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White), verticalArrangement = Arrangement.Top, contentPadding = PaddingValues(5.dp))
    {
        items(tasks){
            taskRow(task = it)
        }
    }

}

@Composable
fun taskRow(task: Task){
    Column(modifier = Modifier.fillMaxWidth().background(Color.White).padding(bottom = 15.dp)) {
        Text(task.taskName, color = Color.Black, style = MaterialTheme.typography.displayMedium)
        Text(task.taskDescription, color = Color.Red,style = MaterialTheme.typography.displaySmall)
    }

}


@Preview(showBackground = true)
@Composable
fun toDoListScreenPreview() {
    ToDoListForProjectsTheme {
        val taskList = ArrayList<Task>()
        val task1 = Task("1","İlk işimiz","ilk işimizi yapıyoruz","01.02.2024","12:00","Yapıldı")
        val task2 = Task("2","İkinci işimiz","İkinci işimizi yapıyoruz","05.02.2023","13:00","Yapıldı")
        val task3 = Task("3","Üçüncü işimiz","Üçüncü işimizi yapıyoruz","21.06.2021","14:00","Yapılmadı")
        val task4 = Task("4","Dördüncü işimiz","Dördüncü işimizi yapıyoruz","02.12.2022","15:00","Yapıldı")
        val task5 = Task("5","Son işimiz","Son işimizi yapıyoruz","11.01.2013","22:00","Yapıldı")

        taskList.add(task1)
        taskList.add(task2)
        taskList.add(task3)
        taskList.add(task4)
        taskList.add(task5)



        toDoListScreen(tasks = taskList)
    }
}