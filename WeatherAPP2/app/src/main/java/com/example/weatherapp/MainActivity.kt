package com.example.weatherapp

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.ui.theme.WeatherAPPTheme
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.ui.Alignment
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.data.network.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val apiKey = "590ac60ebf684886a2d171910250511"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppCompose()
        }
    }

    @Composable
    fun WeatherAppCompose() {
        var city by remember { mutableStateOf("Almaty") }
        var weatherText by remember { mutableStateOf("") }
        var isLoading by remember { mutableStateOf(false) }
        var error by remember { mutableStateOf<String?>(null) }

        val scope = rememberCoroutineScope()

        WeatherAPPTheme {
            Scaffold { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "WeatherApp",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("Enter City") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                error = null
                                weatherText = ""
                                try {
                                    val response = RetrofitClient.api.getCurrentWeather(apiKey, city)
                                    weatherText = """
                                    ${response.location.name}
                                    🌡️ ${response.current.temp_c}°C
                                    🌬️ ${response.current.condition.text}
                                """.trimIndent()
                                } catch (e: Exception) {
                                    error = e.message
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Get Weather")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    if (weatherText.isNotEmpty() || error != null || isLoading) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                when {
                                    isLoading -> CircularProgressIndicator()
                                    error != null -> Text(
                                        text = "Error: $error",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                    else -> Text(
                                        text = weatherText,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
