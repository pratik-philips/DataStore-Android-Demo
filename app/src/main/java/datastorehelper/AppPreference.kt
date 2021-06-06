package datastorehelper

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.lifecycle.LiveData

class AppPreference(context: Context) : DataStorePreferenceHelper(context){

    companion object {
        val KEY_COUNTER = intPreferencesKey("example_counter")
        val KEY_COUNTRY = stringPreferencesKey("key_country")
        val KEY_ENABLE = booleanPreferencesKey("key_enable")

        /**
         *
        doublePreferencesKey("key")
        booleanPreferencesKey("key")
        floatPreferencesKey("key")
        stringSetPreferencesKey("key")
         *
         */
    }

    fun getCounter(): LiveData<Int> {
        return getValue(KEY_COUNTER, 0)
    }

    fun getStoredCountry(): LiveData<String> {
        return getValue(KEY_COUNTRY, "NA")
    }

    fun isEnabled(): LiveData<Boolean> {
        return getValue(KEY_ENABLE, false)
    }

    suspend fun incrementCounter() {
        incrementValue(KEY_COUNTER)
    }

    suspend fun setCounter(value: Int) {
        setValue(KEY_COUNTER, value)
    }

    suspend fun setCountry(country: String) {
        setValue(KEY_COUNTRY, country)
    }

    fun getCountry(): String {
        return getSyncValue(KEY_COUNTRY, "India")
    }
}