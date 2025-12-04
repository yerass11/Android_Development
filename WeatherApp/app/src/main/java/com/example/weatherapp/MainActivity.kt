package com.example.weatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.network.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val apiKey = "590ac60ebf684886a2d171910250511"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp()
        }
    }

    @Composable
    fun WeatherApp() {

        var isLoading by remember { mutableStateOf(true) }
        var error by remember { mutableStateOf<String?>(null) }
        var weatherText by remember { mutableStateOf("") }

        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            scope.launch {
                try {
                    val response = RetrofitClient.api.getCurrentWeather(apiKey, "Almaty")
                    weatherText = """
                        🌆 City: ${response.location.name}
                        🌡️ Temperature: ${response.current.temp_c}°C
                        🌬️ Condition: ${response.current.condition.text}
                    """.trimIndent()
                } catch (e: Exception) {
                    error = e.message
                } finally {
                    isLoading = false
                }
            }
        }

        // UI
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }
                error != null -> {
                    Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
                }
                else -> {
                    Text(
                        text = weatherText,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}