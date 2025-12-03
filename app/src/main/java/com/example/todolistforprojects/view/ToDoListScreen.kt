package com.example.todolistforprojects.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.ui.theme.ToDoListForProjectsTheme
import com.google.gson.Gson

@Composable
fun toDoListScreen(tasks: List<Task>, navController: NavController){
    val cornerShape = RoundedCornerShape(12.dp)
    Column(modifier = Modifier.fillMaxSize().background(Color.White), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "To Do List", style = MaterialTheme.typography.displayMedium)

        Spacer(modifier = Modifier.padding(100.dp))


        LazyRow(modifier = Modifier.background(Color.Red).height(250.dp),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, contentPadding = PaddingValues(5.dp)) {
            items(tasks){
                taskRow(task = it , navController = navController)
            }
        }

        Spacer(modifier = Modifier.padding(100.dp))

        TextField(value = "deneme", onValueChange = {}, shape = cornerShape)

    }


}

@Composable
fun taskRow(task: Task, navController: NavController){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.background(Color.White).padding(end = 5.dp).clickable{
        navController.navigate("taskDetailScreen/${Gson().toJson(task)}")

    }.fillMaxSize().padding(5.dp)) {
        Text(task.taskName)
        Spacer(modifier = Modifier.padding(1.dp))
        Text(task.taskDescription)
        Spacer(modifier = Modifier.padding(1.dp))
        Text(task.taskDate)
        Spacer(modifier = Modifier.padding(1.dp))
        Text(task.taskTime)
    }

}

/*
@Preview(showBackground = true)
@Composable
fun toDoListScreenPreview() {
    ToDoListForProjectsTheme {
        val taskList = ArrayList<Task>()
        val task1 = Task("1","İlk işimiz","ilk işimizi yapıyoruz","01.02.2024","12:00","Yapıldı")
        val task2 = Task("2","İkinci işimiz","İkinci işimizi yapıyoruz","05.02.2023","13:00","Yapıldı")
        val task3 = Task("3","Üçüncü işimiz","Üçüncü işimizi yapıyoruz","21.06.2021","14:00","Yapılmadı")
        val task4 = Task("4","Dördüncü işimiz","Dördüncü işimizi yapıyoruz","02.12.2022","15:00","Yapıldı")
        val task5 = Task("5","Beşinci işimiz","Beşinci işimizi yapıyoruz","11.01.2013","22:00","Yapıldı")

        taskList.add(task1)
        taskList.add(task2)
        taskList.add(task3)
        taskList.add(task4)
        taskList.add(task5)



        toDoListScreen(tasks = taskList, navController = rememberNavController())
    }
}
*/