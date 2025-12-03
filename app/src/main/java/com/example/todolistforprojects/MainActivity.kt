package com.example.todolistforprojects

import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.core.widgets.Rectangle
import com.example.todolistforprojects.model.Task
import com.example.todolistforprojects.ui.theme.ToDoListForProjectsTheme



class MainActivity : ComponentActivity() {

    private val taskList = ArrayList<Task>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListForProjectsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        MainScreen()

                    }
                }
            }
        }
    }

    fun taskOlustur(){

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


    }
}


@Composable
fun MainScreen(){
    val cornerShape = RoundedCornerShape(12.dp)
    var kullaniciMail = remember { mutableStateOf("") }
    var kullaniciSifre = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(20.dp))
        Text(style = MaterialTheme.typography.displayLarge,fontFamily = FontFamily.Monospace,text="Şirket İsmi", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
        Spacer(modifier = Modifier.padding(90.dp))
        OutlinedTextField(modifier = Modifier.background(Color.Cyan, shape = cornerShape).border(5.dp, shape = cornerShape, color = Color.Black),value = kullaniciMail.value, onValueChange = {kullaniciMail.value = it}, placeholder = { Text(text = "Mailinizi Giriniz...", fontWeight = FontWeight.Bold, fontSize = 22.sp, fontStyle = FontStyle.Italic)}, shape = cornerShape)
        Spacer(modifier = Modifier.padding(25.dp))
        OutlinedTextField(modifier = Modifier.background(Color.Cyan, shape = cornerShape).border(5.dp, shape = cornerShape, color = Color.Black),value = kullaniciSifre.value, onValueChange = {kullaniciSifre.value = it}, placeholder = { Text(text = "Şifrenizi Giriniz...", fontWeight = FontWeight.Bold, fontSize = 22.sp, fontStyle = FontStyle.Italic)}, shape = cornerShape)
        Spacer(modifier = Modifier.padding(25.dp))
        Button(onClick = { println(kullaniciMail.value + " " + kullaniciSifre.value) }) {
            Text(text="Giriş Yap", fontSize = 18.sp)
        }

    }

}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoListForProjectsTheme {
        MainScreen()
    }
}