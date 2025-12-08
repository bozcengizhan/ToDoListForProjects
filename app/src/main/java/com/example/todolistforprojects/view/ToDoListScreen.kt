package com.example.todolistforprojects.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import org.w3c.dom.Text

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
                onFailsClick = {
                    navController.navigate("failedTasks")
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
                .background(Color(0xFFFFECC8)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.1f))


            ModernTopBar(title ="To Do List")

            Spacer(modifier = Modifier.weight(0.7f))

            LazyRow(modifier = Modifier
                .height(350.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(5.dp)
            ) {
                // tasks bir List<Task> — items çalışacaktır
                items(tasks) { task ->
                    TaskRow(task = task, navController = navController)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

        }

    }
}
@Composable
fun BottomBar(
    onAddClick: () -> Unit,
    onFailsClick: () -> Unit,
    onFinishedClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFF986F),
                        Color(0xFFFFA96F),
                        Color(0xFFFF9E71),
                        Color(0xFFFF9671),
                        Color(0xFFFF9671),
                        Color(0xFFFFA16F),
                        Color(0xFFFF986F)
                        )
                ),
                shape = RoundedCornerShape(topStart = 50.dp,topEnd = 50.dp)
            ).border(3.dp, Color.Black, RoundedCornerShape(topStart = 50.dp,topEnd = 50.dp))

    ) {

        // Üste çok hafif vertical karartma koy — banding tamamen yok olur
        Box(
            modifier = Modifier
                .matchParentSize()
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            BottomBarButton(
                title = "Completed",
                icon = Icons.Default.Check,
                onClick = onFinishedClick
            )

            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(2.dp)
                    .background(Color.Black.copy(alpha = 0.3f))
            )

            BottomBarButton(
                title = "Add",
                icon = Icons.Default.Add,
                onClick = onAddClick
            )

            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(2.dp)
                    .background(Color.Black.copy(alpha = 0.3f))
            )

            BottomBarButton(
                title = "Fails",
                icon = Icons.Default.Close,
                onClick = onFailsClick
            )
        }
    }
}

@Composable
fun BottomBarButton(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
fun TaskRow(task: Task, navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color(0xFFEEC3C1), RoundedCornerShape(10.dp)).border(3.dp, Color.Black, RoundedCornerShape(10.dp))
            .padding(5.dp)
            .clickable {
                navController.navigate("taskDetailScreen/${Gson().toJson(task)}")
            }
            .width(250.dp),
    ) {
        Text(task.name, textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineMedium, maxLines = 4, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Kalan Gün: ${task.totalDays}", color = Color(0xFFFFECC8), textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
    }
}

@Composable
fun ModernTopBar(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 40.dp)
            .shadow(12.dp, RoundedCornerShape(10.dp))  // gölge + yuvarlak köşe
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFF986F),
                        Color(0xFFFFA96F),
                        Color(0xFFFF9E71),
                        Color(0xFFFF9671),
                        Color(0xFFFF9671),
                        Color(0xFFFFA16F),
                        Color(0xFFFF986F)
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
                fontSize = MaterialTheme.typography.headlineMedium.fontSize * 1.8f, // %20 daha büyük
            ),
            modifier = Modifier.padding(6.dp)

        )
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
                        onFailsClick = {},
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

                    Spacer(modifier = Modifier.weight(0.25f))

                    Text(
                        text = "To Do List",
                        style = MaterialTheme.typography.displayMedium
                    )

                    Spacer(modifier = Modifier.weight(1f))

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

                    Spacer(modifier = Modifier.weight(1.5f))
                }
            }
        }
    }
}
