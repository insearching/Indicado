package com.serhiihrabas.indicado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.serhiihrabas.core.presentation.theme.IndicadoTheme
import com.serhiihrabas.indicado.presentation.TernopilOblEnergyScreenRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IndicadoTheme {
                TernopilOblEnergyScreenRoot()
            }
        }
    }
}