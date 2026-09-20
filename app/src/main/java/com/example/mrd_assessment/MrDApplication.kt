package com.example.mrd_assessment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Inject

@HiltAndroidApp
class MrDApplication : Application() {

    @Inject
    lateinit var mockWebServer: MockWebServer

    override fun onTerminate() {
        super.onTerminate()
        CoroutineScope(Dispatchers.IO).launch {
            runCatching { mockWebServer.shutdown() }
        }
    }
}
