package ru.thstdio.tetamtscompousel2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.thstdio.tetamtscompousel2.ui.theme.TetaMtsCompouseL2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TetaMtsCompouseL2Theme {
                // A surface container using the 'background' color from the theme

                var page by remember() { mutableStateOf(UnitPage.Units) }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Crossfade(targetState = page) {
                        when (it) {
                            UnitPage.Units -> ScreenUnits{ page = it }
                            UnitPage.Basic -> ScreenBasic()
                            UnitPage.Advance -> ScreenAdvance()
                        }
                    }
                }
            }
        }
    }
}

enum class UnitPage {
    Units, Basic, Advance
}

@Composable
fun ScreenUnits(onClick: (UnitPage) -> Unit) {
    Column() {
        Button(onClick = { onClick(UnitPage.Basic) }) {
            Text(text = "Unit1")
        }
        Button(onClick = { onClick(UnitPage.Advance) }) {
            Text(text = "Unit2")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TetaMtsCompouseL2Theme {
        Greeting("Android")
    }
}