package com.example.mrd_assessment

import androidx.navigation.navDeepLink
import com.example.mrd_assessment.core.navigation.RestaurantDetailRoute
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun deepLink_isCorrect() {
        val deepLink = navDeepLink<RestaurantDetailRoute>(basePath = "mrd://restaurants")
        println("Generated uriPattern: ${deepLink.uriPattern}")
        assertNotNull(deepLink.uriPattern)
    }
}