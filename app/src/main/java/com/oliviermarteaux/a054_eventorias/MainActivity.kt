package com.oliviermarteaux.a054_eventorias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.oliviermarteaux.a054_eventorias.ui.theme.A054_EventoriasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A054_EventoriasTheme {
                EventoriasApp()
            }
        }
    }
}