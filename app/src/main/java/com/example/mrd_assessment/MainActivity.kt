package com.example.mrd_assessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mrd_assessment.core.navigation.HomeRoute
import com.example.mrd_assessment.core.navigation.RestaurantDetailRoute
import com.example.mrd_assessment.core.ui.theme.MrD_AssessmentTheme
import com.example.mrd_assessment.feature.home.home.HomeScreen
import com.example.mrd_assessment.feature.restaurant.detail.RestaurantDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MrD_AssessmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        composable<HomeRoute> {
            HomeScreen(
                onRestaurantClick = { restaurantId ->
                    navController.navigate(RestaurantDetailRoute(restaurantId = restaurantId))
                }
            )
        }

        composable<RestaurantDetailRoute> { backStackEntry ->
            val route: RestaurantDetailRoute = backStackEntry.toRoute()
            RestaurantDetailScreen(
                restaurantId = route.restaurantId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
