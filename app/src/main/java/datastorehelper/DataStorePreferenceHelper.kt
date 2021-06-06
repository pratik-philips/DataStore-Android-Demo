package datastorehelper

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException

private val Context.dataStore by preferencesDataStore(name = DataStorePreferenceHelper.USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        // Since we're migrating from SharedPreferences, add a migration based on the
        // SharedPreferences name
        listOf(SharedPreferencesMigration(context, DataStorePreferenceHelper.USER_PREFERENCES_NAME))
    }
)

open class DataStorePreferenceHelper(private val context: Context, coroutineScope: CoroutineScope? = null) {

    companion object {
        const val USER_PREFERENCES_NAME = "user_preferences"
    }

    init {
        coroutineScope?.let { syncDataStore(it) }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun syncDataStore(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            context.dataStore.data.first()
        }
    }

    fun <T> getValue(key: Preferences.Key<T>, default: T): LiveData<T> {
        return context.dataStore.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                // return preference value, return default if not set
                preferences[key] ?: default
            }.asLiveData()
    }

    @Suppress("SameParameterValue")
    protected fun <T> getSyncValue(key: Preferences.Key<T>, default: T): T {
        return runBlocking {
            context.dataStore.data.first()[key] ?: default
        }
    }

    suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { settings ->
            settings[key] = value
        }
    }

    suspend fun incrementValue(key: Preferences.Key<Int>, by: Int = 1) {
        context.dataStore.edit { settings ->
            val currentValue = settings[key] ?: 0
            settings[key] = currentValue + by
        }
    }
}