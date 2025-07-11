package com.serhiihrabas.indicado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.serhiihrabas.core.presentation.theme.IndicadoTheme
import com.serhiihrabas.indicado.navigation.IndicadoController
import com.serhiihrabas.indicado.navigation.Route

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IndicadoTheme {
                IndicadoController(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    startDestination = Route.HomeScreen,
                    controller = rememberNavController()
                )
            }
        }
    }
}