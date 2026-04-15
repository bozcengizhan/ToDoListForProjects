package com.example.todolistforprojects.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todolistforprojects.ui.theme.ToDoListForProjectsTheme
import com.example.todolistforprojects.viewmodel.TaskViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var totalDays by remember { mutableStateOf("") } // Gün sayısı

    Scaffold (containerColor = Color(0xFFCDB5FC)) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(15.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            val maxLength = 60
            val maxDescriptionLength = 325


            ModernTopBar4(title = "New Task")

            Spacer(modifier = Modifier.weight(0.15f))

            OutlinedTextField(
                value = name,
                onValueChange = {
                    if (it.length <= maxLength) {
                        name = it
                    }
                },
                label = { Text("Task Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF8600BD),
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color(0xFF8600BD),
                    unfocusedLabelColor = Color(0xFFFFF8E7),
                    focusedPrefixColor = Color(0xFF8600BD),
                    unfocusedPrefixColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = description,
                onValueChange = {
                    if (it.length <= maxDescriptionLength) {
                        description = it
                    }
                },
                label = { Text("Task Description") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF8600BD),
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color(0xFF8600BD),
                    unfocusedLabelColor = Color(0xFFFFF8E7),
                    focusedPrefixColor = Color(0xFF8600BD),
                    unfocusedPrefixColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(10.dp))


            OutlinedTextField(
                value = totalDays,
                onValueChange = { input ->
                    // Sadece rakam girişine izin veriyoruz
                    if (input.all { it.isDigit() }) totalDays = input
                },
                label = { Text("Duration (Days)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF8600BD),
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color(0xFF8600BD),
                    unfocusedLabelColor = Color(0xFFFFF8E7),
                    focusedPrefixColor = Color(0xFF8600BD),
                    unfocusedPrefixColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.weight(0.5f))

            Button(
                onClick = {
                    val currentUserEmail = Firebase.auth.currentUser?.email ?: ""
                    if (name.isNotBlank() && description.isNotBlank() && totalDays.isNotBlank()) {
                        viewModel.addTask(
                            name = name,
                            description = description,
                            totalDays = totalDays.toInt(),
                            creatorEmail = currentUserEmail
                        )
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFB57AD2)),
                modifier = Modifier.align(Alignment.CenterHorizontally).size(125.dp,50.dp)
                    .border(3.dp,color = Color.Black, shape = RoundedCornerShape(30.dp))
                    .shadow(12.dp, RoundedCornerShape(10.dp))  // gölge + yuvarlak köşe
                , shape = RoundedCornerShape(30.dp)
            ) {
                Text("Add", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
        }
    }
}



@Composable
fun ModernTopBar4(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 70.dp)
            .shadow(12.dp, RoundedCornerShape(10.dp))  // gölge + yuvarlak köşe
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFB57AD2),
                        Color(0xFFB272D2),
                        Color(0xFFB069D3),
                        Color(0xFFB069D3),
                        Color(0xFFB069D3),
                        Color(0xFFB272D2),
                        Color(0xFFB57AD2)
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
                fontSize = MaterialTheme.typography.headlineMedium.fontSize * 1.5f // %20 daha büyük
            )
        )
    }
}



@Preview(showBackground = true)
@Composable
fun AddTaskScreenPreview() {
    ToDoListForProjectsTheme {
        AddTaskScreen(navController = NavController(LocalContext.current))
    }
}