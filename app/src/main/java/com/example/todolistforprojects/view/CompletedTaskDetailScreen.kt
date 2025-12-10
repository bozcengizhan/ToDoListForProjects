package com.example.todolistforprojects.view

import android.widget.ToggleButton
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.ui.theme.ToDoListForProjectsTheme
import com.example.todolistforprojects.viewmodel.TaskViewModel
import kotlinx.coroutines.NonCancellable.isCompleted
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun completedTaskDetailScreen(
    task: Task,
    viewModel: TaskViewModel = viewModel(),
    navController: NavController
){
    val formattedDate = task.startDate?.toDate()?.let { date ->
        val sdf = java.text.SimpleDateFormat("dd MMMM yyyy, HH:mm")
        sdf.format(date)
    } ?: "No Date Data"

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFBFFCBF)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            ModernTopBar6(title = task.name)
            Spacer(modifier= Modifier.weight(1.5f))

            ModernCardBar2(title = task.description)

            Spacer(modifier= Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.DeleteForever,
                contentDescription = "Logout",
                tint = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(start =10.dp,end = 10.dp)
                    .size(28.dp)
                    .clickable {
                        viewModel.deleteCompletedTask(task)
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
fun ModernTopBar6(title: String) {
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
fun ModernCardBar2(title: String) {
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
            color = Color(0xFF8AFC8A),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize * 1.1f // %20 daha büyük
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}
