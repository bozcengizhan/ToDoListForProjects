package com.example.todolistforprojects.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
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
        val sdf = java.text.SimpleDateFormat("dd MMMM yyyy, HH:mm")
        sdf.format(date)
    } ?: "No Date Data"

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAA0A0)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ModernTopBar7(task.name)

            Spacer(modifier= Modifier.weight(1.5f))

            ModernCardBar3(task.description)

            Spacer(modifier= Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.DeleteForever,
                contentDescription = "Logout",
                tint = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(start =10.dp,end = 10.dp)
                    .size(28.dp)
                    .clickable {
                        viewModel.deleteFailedTask(task)
                        navController.popBackStack()
                    }
                    .shadow(12.dp, RoundedCornerShape(10.dp))

            )
            Spacer(modifier= Modifier.weight(0.5f))
            Text("${task.creatorEmail}, " + formattedDate, fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.titleMedium, fontFamily = FontFamily.SansSerif, fontStyle = FontStyle.Italic, color = Color(0xFF7C5E5C))
        }
    }
}

@Composable
fun ModernTopBar7(title: String) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 20.dp, vertical = 4.dp)
            .shadow(100.dp, RoundedCornerShape(5.dp))  ,// gölge + yuvarlak köşe),

        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color(0xFFFFF8E7),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize * 1.8f // %20 daha büyük
            ),
            modifier = Modifier.padding(6.dp), textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ModernCardBar3(title: String) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 10.dp)
            .shadow(12.dp, RoundedCornerShape(5.dp))  // gölge + yuvarlak köşe
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFFF8E7),
                        Color(0xFFFDF3DB),
                        Color(0xFFFFF8E7)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color(0xFFFAA0A0),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize * 1.1f // %20 daha büyük
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}
