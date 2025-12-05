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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.ui.theme.ToDoListForProjectsTheme
import com.example.todolistforprojects.viewmodel.TaskViewModel
import com.google.gson.Gson

@Composable
fun toDoListScreen(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
)
{

    // ---- Buradaki kullanım doğru: StateFlow.collectAsState ile Compose uyumu
    val tasks by viewModel.taskList.collectAsState()


    Scaffold(
        bottomBar = {
            BottomBar(
                onAddClick = {
                    navController.navigate("addTask")
                },
                onSettingsClick = {
                    // Ayarlar sayfası yoksa eklemene yardımcı olurum
                },
                onFinishedClick = {
                    navController.navigate("completedTasks")
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.padding(25.dp))

            Text(
                text = "To Do List",
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.padding(40.dp))

            LazyRow(modifier = Modifier
                .height(250.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(5.dp)
            ) {
                // tasks bir List<Task> — items çalışacaktır
                items(tasks) { task ->
                    TaskRow(task = task, navController = navController)
                }
            }

            Spacer(modifier = Modifier.padding(50.dp))

        }

    }
}

@Composable
fun BottomBar(onAddClick: () -> Unit, onSettingsClick: () -> Unit, onFinishedClick: () -> Unit) {
    androidx.compose.material3.NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { onFinishedClick() },
            icon = { Text("✔") },
            label = { Text("Bitenler") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onAddClick() },
            icon = { Text("➕") },
            label = { Text("Ekle") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { onSettingsClick() },
            icon = { Text("⚙️") },
            label = { Text("Ayarlar") }
        )
    }
}


@Composable
fun TaskRow(task: Task, navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .padding(end = 5.dp)
            .clickable {
                navController.navigate("taskDetailScreen/${Gson().toJson(task)}")
            }
            .padding(5.dp).width(150.dp)
    ) {
        Text(task.name, textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Kalan Gün: ${task.totalDays}", color = if (task.totalDays == 0) Color.Red else Color.Green, textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Ekleyen: ${task.creatorEmail}")

    }
}


@Preview(showBackground = true)
@Composable
fun toDoListFakePreview() {
    val fakeTasks = listOf(
        Task(
            id = "1",
            name = "Toplantı Hazırlığı",
            description = "Yarınki toplantı için sunum hazırla",
            totalDays = 3,
            startDate = com.google.firebase.Timestamp.now(),
            creatorEmail = "ali@example.com"
        ),
        Task(
            id = "2",
            name = "Rapor Yazımı",
            description = "Haftalık satış raporunu hazırla",
            totalDays = 0,
            startDate = com.google.firebase.Timestamp.now(),
            creatorEmail = "ayse@example.com"
        ),
        Task(
            id = "3",
            name = "Kod İncelemesi",
            description = "Takım arkadaşlarının PR'larını incele",
            totalDays = 5,
            startDate = com.google.firebase.Timestamp.now(),
            creatorEmail = "mehmet@example.com"
        )
    )

    // Fake ViewModel yerine direkt olarak task listesi geçiyoruz
    ToDoListForProjectsTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        onAddClick = {},
                        onSettingsClick = {},
                        onFinishedClick = {}
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.padding(25.dp))

                    Text(
                        text = "To Do List",
                        style = MaterialTheme.typography.displayMedium
                    )

                    Spacer(modifier = Modifier.padding(40.dp))

                    LazyRow(
                        modifier = Modifier.height(250.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        contentPadding = PaddingValues(5.dp)
                    ) {
                        items(fakeTasks) { task ->
                            TaskRow(task = task, navController = NavController(LocalContext.current))
                        }
                    }

                    Spacer(modifier = Modifier.padding(50.dp))
                }
            }
        }
    }
}
