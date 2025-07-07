package com.serhiihrabas.indicado.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.serhiihrabas.indicado.presentation.TernopilOblEnergyScreenRoot
import com.serhiihrabas.indicado.screens.HomeScreen

@Composable
fun IndicadoController(
    modifier: Modifier = Modifier,
    startDestination: Route,
    controller: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = controller,
        startDestination = startDestination,
    ) {
        composable<Route.HomeScreen> {
            HomeScreen(
                modifier = modifier.fillMaxSize(),
                navigateToTernopilOblEnergy = {
                    controller.navigate(Route.TernopilOblEnergyScreen)
                }
            )
        }
        composable<Route.TernopilOblEnergyScreen> {
            TernopilOblEnergyScreenRoot()
        }
    }
}
