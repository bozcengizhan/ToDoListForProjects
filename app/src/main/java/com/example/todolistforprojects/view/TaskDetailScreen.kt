package com.example.todolistforprojects.view

import android.widget.ToggleButton
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun taskDetailScreen(
    task: Task,
    viewModel: TaskViewModel = viewModel(),
    navController: NavController
){
    var isClicked by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val formattedDate = task.startDate?.toDate()?.let { date ->
        val sdf = java.text.SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("tr"))
        sdf.format(date)
    } ?: "Tarih yok"

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFC0BE)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            ModernTopBar5(title = task.name)

            Spacer(modifier= Modifier.weight(0.01f))
            Switch(
                checked = isClicked,
                onCheckedChange = { checked ->

                    isClicked = checked

                    if (checked) {
                        coroutineScope.launch {
                            delay(1000)
                            viewModel.completeTask(task)
                            navController.popBackStack()
                        }
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green,      // Tıklandıktan sonraki renk
                    uncheckedThumbColor = Color.Red,      // Tıklanmadan önceki renk
                    checkedTrackColor = Color(0x8032CD32),
                    uncheckedTrackColor = Color(0x80FF0000),
                    uncheckedBorderColor = Color(0xFFF1B6B2),
                    checkedBorderColor = Color(0xFFF1B6B2)
                )
            )
            Spacer(modifier= Modifier.weight(0.35f))

            ModernCardBar(title = task.description)

            Spacer(modifier= Modifier.weight(0.75f))

            Text("Kalan Gün: ${task.totalDays}", fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.headlineSmall, color = Color(0xFF00E166))

            Spacer(modifier= Modifier.weight(0.1f))


            Text(formattedDate, fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.headlineMedium, fontFamily = FontFamily.SansSerif, fontStyle = FontStyle.Italic, color = Color(0xFFFFF8E7))

        }

    }

}


@Composable
fun ModernTopBar5(title: String) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color(0xFFFFF8E7),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize * 1.8f // %20 daha büyük
            ),
            modifier = Modifier.padding(6.dp)
        )
    }
}

@Composable
fun ModernCardBar(title: String) {
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
            color = Color(0xFFF1B6B2),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize * 1.2f // %20 daha büyük
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}




