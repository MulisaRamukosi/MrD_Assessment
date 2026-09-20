package com.example.mrd_assessment

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.mrd_assessment.core.navigation.HomeRoute
import com.example.mrd_assessment.core.navigation.RestaurantDetailRoute
import com.example.mrd_assessment.core.ui.theme.MrD_AssessmentTheme
import com.example.mrd_assessment.feature.home.home.HomeScreen
import com.example.mrd_assessment.feature.restaurant.detail.RestaurantDetailScreen
import com.example.mrd_assessment.feature.restaurant.detail.RestaurantDetailScreenVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var onNewIntentListener: ((Intent) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        setContent {
            MrD_AssessmentTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    DisposableEffect(navController) {
                        onNewIntentListener = { newIntent ->
                            newIntent.data?.let { uri ->
                                val request = NavDeepLinkRequest.Builder
                                    .fromUri(uri)
                                    .build()

                                navController.navigate(
                                    request = request,
                                    navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
                                )
                            }
                        }
                        onDispose {
                            onNewIntentListener = null
                        }
                    }

                    LaunchedEffect(navController) {
                        intent?.let { currentIntent ->
                            onNewIntent(currentIntent)
                        }
                    }

                    AppNavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        onNewIntentListener?.invoke(intent)
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

        composable<RestaurantDetailRoute>(
            deepLinks = listOf(
                navDeepLink<RestaurantDetailRoute>(basePath = "mrd://restaurants"),
            )
        ) { backStackEntry ->
            val route: RestaurantDetailRoute = backStackEntry.toRoute()

            RestaurantDetailScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = hiltViewModel<RestaurantDetailScreenVM, RestaurantDetailScreenVM.Factory> { factory ->
                    factory.create(route.restaurantId)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
