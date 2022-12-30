package ru.vadim.onlinesabetta

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import ru.vadim.onlinesabetta.ui.theme.OnlineSabettaTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnlineSabettaTheme {

                val dateTimeNow = remember {
                    mutableStateOf("")
                }


                LaunchedEffect(Unit) {
                    while(true) {
                        delay(1.seconds)
                        getDateTimeNow(dateTimeNow)
                    }
                }






                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting(dateTimeNow.value)
                }
            }
        }
    }

    private fun getDateTimeNow(state: MutableState<String>) {
        val millis = Date().time / 1000
val ddd = Instant.ofEpochSecond(millis).atZone(ZoneId.of("Europe/Moscow"))
        val dateMoscow = ddd.toLocalDateTime().format(DateTimeFormatter.ofPattern("EEEE, dd.MM HH:mm:ss"))

        state.value = ddd.toString()
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OnlineSabettaTheme {
        Greeting("Android")
    }
}

