package com.example.mrd_assessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
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
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    AppNavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
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
                modifier = Modifier.fillMaxSize(),
                onRestaurantClick = { restaurantId ->
                    navController.navigate(route = RestaurantDetailRoute(restaurantId = restaurantId))
                }
            )
        }

        composable<RestaurantDetailRoute> { backStackEntry ->
            val route: RestaurantDetailRoute = backStackEntry.toRoute()
            RestaurantDetailScreen(
                modifier = Modifier.fillMaxSize(),
                restaurantId = route.restaurantId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
