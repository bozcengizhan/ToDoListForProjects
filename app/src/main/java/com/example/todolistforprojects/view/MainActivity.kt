package com.example.todolistforprojects.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.ui.theme.ToDoListForProjectsTheme
import com.example.todolistforprojects.viewmodel.TaskViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.gson.Gson


class MainActivity : ComponentActivity() {

    private val taskList = ArrayList<Task>()
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            auth = Firebase.auth

            // 🔥 Start destination'ı Compose başlamadan belirliyoruz
            val startDestination = if (auth.currentUser != null) {
                "toDoListScreen"
            } else {
                "mainScreen"
            }

            ToDoListForProjectsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    Box(modifier = Modifier.padding(padding)) {

                        NavHost(
                            navController = navController,
                            startDestination = startDestination
                        ) {
                            composable("mainScreen") {
                                MainScreen(
                                    onRegister = { email, password ->
                                        registerUser(email, password)
                                    },
                                    onLogin = { email, password ->
                                        loginUser(email, password, navController)
                                    }
                                )
                            }

                            composable("toDoListScreen") {
                                toDoListScreen(
                                    viewModel = TaskViewModel(),
                                    navController = navController
                                )
                            }
                            composable("taskDetailScreen/{secilenTask}", arguments = listOf(navArgument("secilenTask"){
                                type = NavType.StringType
                            })){
                                val taskString = remember {
                                    it.arguments?.getString("secilenTask")
                                }
                                val secilenTask = Gson(). fromJson(taskString, Task::class.java)
                                taskDetailScreen(navController = navController,task = secilenTask)
                            }
                            composable("addTask") {
                                AddTaskScreen(navController)
                            }
                            composable("completedTasks") {
                                CompletedTasksScreen(navController)
                            }

                            composable("completedTaskDetailScreen/{secilenTask}", arguments = listOf(navArgument("secilenTask"){
                                type = NavType.StringType
                            })){
                                val taskString = remember {
                                    it.arguments?.getString("secilenTask")
                                }
                                val secilenTask = Gson(). fromJson(taskString, Task::class.java)
                                completedTaskDetailScreen(navController = navController,task = secilenTask)
                            }
                            composable("failedTasks") {
                                failedTaskScreen(navController)
                            }
                            composable("failedTaskDetailScreen/{secilenTask}", arguments = listOf(navArgument("secilenTask") {
                                type = NavType.StringType
                            })) {
                                val taskString = remember {
                                    it.arguments?.getString("secilenTask")
                                }
                                val secilenTask = Gson().fromJson(taskString, Task::class.java)
                                failedTaskDetailScreen(navController = navController, task = secilenTask)
                            }



                        }


                    }
                }
            }
        }
    }


    fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Kayıt hata: ${task.exception?.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun loginUser(email: String, password: String, navController: NavController) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Giriş başarılı!", Toast.LENGTH_SHORT).show()
                    navController.navigate("toDoListScreen") {
                        popUpTo("mainScreen") { inclusive = true }
                    }
                } else {
                    Toast.makeText(this, "Giriş hata: ${task.exception?.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
    }





}


@Composable
fun MainScreen(
    onRegister: (String, String) -> Unit,
    onLogin: (String, String) -> Unit
) {
    val cornerShape = RoundedCornerShape(12.dp)
    var kullaniciMail = remember { mutableStateOf("") }
    var kullaniciSifre = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFAFDAE7)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(10.dp))


        ModernTopBar5("WorkSync")

        Spacer(modifier = Modifier.padding(85.dp))


        TextField(
            modifier = Modifier.padding(horizontal = 40.dp).background(Color(0xFF7AD0EA), shape = RoundedCornerShape(10.dp)).border(3.dp, color = Color.Black, shape = RoundedCornerShape(10.dp)).fillMaxWidth(),
            value = kullaniciMail.value,
            onValueChange = { kullaniciMail.value = it },
            label = { Text("User Email") },
            shape = cornerShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF006BBD),
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color(0xFF006BBD),
                unfocusedLabelColor = Color.White,
                focusedPrefixColor = Color(0xFF006BBD),
                unfocusedPrefixColor = Color.Black,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
        )

        Spacer(modifier = Modifier.padding(20.dp))

        TextField(
            modifier = Modifier.padding(horizontal = 40.dp).background(Color(0xFF7AD0EA), shape = RoundedCornerShape(10.dp)).border(3.dp, color = Color.Black, shape = RoundedCornerShape(10.dp)).fillMaxWidth(),
            value = kullaniciSifre.value,
            onValueChange = { kullaniciSifre.value = it },
            label = { Text("User Password") },
            shape = cornerShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF006BBD),
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color(0xFF006BBD),
                unfocusedLabelColor = Color.White,
                focusedPrefixColor = Color(0xFF006BBD),
                unfocusedPrefixColor = Color.Black,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.padding(80.dp))

        // 🔹 Giriş Yap BUTONU
        Button(onClick = {
            onLogin(kullaniciMail.value, kullaniciSifre.value)
        }, colors = ButtonDefaults.buttonColors(Color(0xFF7AD0EA)), modifier = Modifier.border(3.dp, color = Color.Black, shape = RoundedCornerShape(30.dp)).shadow(12.dp, RoundedCornerShape(10.dp)), shape = RoundedCornerShape(30.dp)) {
            Text(text = "Giriş Yap", fontSize = 18.sp)
        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoListForProjectsTheme {
        MainScreen(
            onRegister = { email, password ->},
            onLogin = { email, password ->}
        )
    }
}