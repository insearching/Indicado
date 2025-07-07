package com.serhiihrabas.indicado.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiihrabas.core.presentation.components.IndicadoButton
import com.serhiihrabas.indicado.R

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToTernopilOblEnergy: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clip(
                    RoundedCornerShape(
                        topStart = 15.dp,
                        topEnd = 15.dp
                    )
                )
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                )
                .consumeWindowInsets(WindowInsets.navigationBars),
            contentAlignment = Alignment.Center
        ) {
            IndicadoButton(
                text = stringResource(R.string.send_electricity_indicators_title),
                onClick = navigateToTernopilOblEnergy
            )
        }
    }
}