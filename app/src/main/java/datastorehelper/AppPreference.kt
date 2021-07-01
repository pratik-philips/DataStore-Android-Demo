package datastorehelper

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.lifecycle.LiveData

class AppPreference(context: Context) : DataStorePreferenceHelper(context) {

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

    fun isEnabled(): LiveData<Boolean> {
        return getValue(KEY_ENABLE, false)
    }

    suspend fun setEnable(enable: Boolean) {
        setValue(KEY_ENABLE, enable)
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

    fun getCountry(): LiveData<String> {
        return getValue(KEY_COUNTRY, "")
    }

    suspend fun resetCounter() {
        remove(KEY_COUNTER)
    }
}