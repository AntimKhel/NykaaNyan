package com.nykaa.nykaacat.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nykaa.nykaacat.feature.dashboard.CATS_DASHBOARD_ROUTE
import com.nykaa.nykaacat.feature.dashboard.catsDashboardScreen

@Composable
fun NykaaNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = CATS_DASHBOARD_ROUTE,
) {
    NavHost(
        navController = rememberNavController(),
        startDestination = startDestination,
        modifier = modifier,
    ) {
        catsDashboardScreen()
    }
}