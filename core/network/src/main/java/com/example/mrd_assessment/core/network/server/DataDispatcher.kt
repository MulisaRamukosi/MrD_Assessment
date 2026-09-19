package com.example.mrd_assessment.core.network.server

import com.example.mrd_assessment.core.network.api.GetRestaurantRequest
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.util.concurrent.TimeUnit

class DataDispatcher(
    private val json: Json = Json { ignoreUnknownKeys = true; coerceInputValues = true }
) : Dispatcher() {

    private val menuRegex = Regex("^/v1/restaurants/([^/]+)/menu$")
    private val favouriteRegex = Regex("^/v1/restaurants/([^/]+)/favourite$")

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.requestUrl?.encodedPath ?: ""

        val menuMatch = menuRegex.find(path)
        val favouriteMatch = favouriteRegex.find(path)

        return when {
            path == "/v1/restaurants" -> {
                val jsonString = request.body.readUtf8()
                val getRestaurantRequest = if (jsonString.isNotBlank()) {
                    runCatching { json.decodeFromString<GetRestaurantRequest>(jsonString) }.getOrNull()
                } else null

                val requestedPage = getRestaurantRequest?.page ?: 1
                val jsonResponse = getRestaurantsPageJson(requestedPage)

                MockResponse()
                    .setResponseCode(200)
                    .setHeader("Content-Type", "application/json")
                    .setBodyDelay(5, TimeUnit.SECONDS)
                    .setBody(jsonResponse)
            }

            favouriteMatch != null -> {
                MockResponse()
                    .setResponseCode(200)
                    .setHeader("Content-Type", "application/json")
                    .setBodyDelay(5, TimeUnit.SECONDS)
                    .setBody("true")
            }

            menuMatch != null -> {
                val restaurantId = menuMatch.groupValues[1]
                val menuJson = getMenuForRestaurant(restaurantId)

                if (menuJson != null) {
                    MockResponse()
                        .setResponseCode(200)
                        .setHeader("Content-Type", "application/json")
                        .setBodyDelay(5, TimeUnit.SECONDS)
                        .setBody(menuJson)
                } else {
                    MockResponse()
                        .setResponseCode(500)
                        .setHeader("Content-Type", "application/json")
                        .setBodyDelay(5, TimeUnit.SECONDS)
                        .setBody("""{"error":"Internal Server Error","message":"Menu unavailable for restaurant $restaurantId"}""")
                }
            }

            else -> {
                MockResponse().setResponseCode(404)
            }
        }
    }

    private fun getRestaurantsPageJson(page: Int): String {
        val resourceName = "data/restaurants_page_$page.json"
        val inputStream = javaClass.classLoader?.getResourceAsStream(resourceName)
            ?: Thread.currentThread().contextClassLoader?.getResourceAsStream(resourceName)

        return inputStream?.bufferedReader()?.use { it.readText() }
            ?: """{"restaurants":[],"page":$page,"has_more_pages":false}"""
    }

    private fun getMenuForRestaurant(restaurantId: String): String? {
        val resourceName = "data/menus/menu_$restaurantId.json"
        val inputStream = javaClass.classLoader?.getResourceAsStream(resourceName)
            ?: Thread.currentThread().contextClassLoader?.getResourceAsStream(resourceName)

        return inputStream?.bufferedReader()?.use { it.readText() }
    }
}
