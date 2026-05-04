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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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


            ToDoListForProjectsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        val currentUser = auth.currentUser

                        NavHost(navController = navController,startDestination = if (currentUser != null) "toDoListScreen" else "mainScreen"){
                            composable("mainScreen"){
                                MainScreen(
                                    onRegister = { email, password ->
                                        registerUser(email, password)
                                    },
                                    onLogin = { email, password ->
                                        loginUser(email, password, navController)
                                    }
                                )
                            }
                            composable("toDoListScreen"){
                                taskOlustur()
                                toDoListScreen(viewModel = TaskViewModel(), navController = navController)
                            }
                            composable("taskDetailScreen/{secilenTask}", arguments = listOf(navArgument("secilenTask"){
                                type = NavType.StringType
                            })){
                                val taskString = remember {
                                    it.arguments?.getString("secilenTask")
                                }
                                val secilenTask = Gson(). fromJson(taskString, Task::class.java)
                                taskDetailScreen(task = secilenTask)
                            }
                            composable("addTask") {
                                AddTaskScreen(navController)
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





    fun taskOlustur(){

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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(20.dp))

        Text(
            text = "Şirket İsmi",
            style = MaterialTheme.typography.displayLarge,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.padding(90.dp))

        OutlinedTextField(
            modifier = Modifier
                .background(Color.Cyan, shape = cornerShape)
                .border(5.dp, shape = cornerShape, color = Color.Black),
            value = kullaniciMail.value,
            onValueChange = { kullaniciMail.value = it },
            placeholder = {
                Text(
                    text = "Mailinizi Giriniz...",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    fontStyle = FontStyle.Italic
                )
            },
            shape = cornerShape
        )

        Spacer(modifier = Modifier.padding(25.dp))

        OutlinedTextField(
            modifier = Modifier
                .background(Color.Cyan, shape = cornerShape)
                .border(5.dp, shape = cornerShape, color = Color.Black),
            value = kullaniciSifre.value,
            onValueChange = { kullaniciSifre.value = it },
            placeholder = {
                Text(
                    text = "Şifrenizi Giriniz...",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    fontStyle = FontStyle.Italic
                )
            },
            shape = cornerShape,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.padding(25.dp))

        // 🔹 Giriş Yap BUTONU
        Button(onClick = {
            onLogin(kullaniciMail.value, kullaniciSifre.value)
        }) {
            Text(text = "Giriş Yap", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.padding(25.dp))

        // 🔹 Şirket hesabı oluştur BUTONU
        Button(onClick = {
            onRegister(kullaniciMail.value, kullaniciSifre.value)
        }) {
            Text(text = "Şirket hesabı oluştur", fontSize = 18.sp)
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