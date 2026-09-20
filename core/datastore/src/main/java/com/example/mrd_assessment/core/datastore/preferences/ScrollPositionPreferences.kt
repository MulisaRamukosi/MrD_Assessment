package com.example.mrd_assessment.core.datastore.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "restaurant_scroll_position")

data class ScrollPosition(
    val index: Int = 0,
    val offset: Int = 0
)

@Singleton
class ScrollPositionPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val scrollIndexKey = intPreferencesKey("restaurant_scroll_index")
    private val scrollOffsetKey = intPreferencesKey("restaurant_scroll_offset")

    val scrollPosition: Flow<ScrollPosition> = context.dataStore.data.map { preferences ->
        ScrollPosition(
            index = preferences[scrollIndexKey] ?: 0,
            offset = preferences[scrollOffsetKey] ?: 0
        )
    }

    suspend fun saveScrollPosition(index: Int, offset: Int) {
        context.dataStore.edit { preferences ->
            preferences[scrollIndexKey] = index
            preferences[scrollOffsetKey] = offset
        }
    }

    suspend fun clearStoredScrollPosition() {
        context.dataStore.edit { preferences ->
            preferences.remove(scrollIndexKey)
            preferences.remove(scrollOffsetKey)
        }
    }
}
