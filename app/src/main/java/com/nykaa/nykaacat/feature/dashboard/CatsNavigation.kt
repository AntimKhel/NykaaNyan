package com.nykaa.nykaacat.feature.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val CATS_DASHBOARD_ROUTE = "cats_dashboard_route"

fun NavGraphBuilder.catsDashboardScreen() {
    composable(route = CATS_DASHBOARD_ROUTE) {
        CatsScreen()
    }
}