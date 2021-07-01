package datastorehelper

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsDataStoreViewModel(application: Application): AndroidViewModel(application) {

    private val settingsDataStoreHelper: SettingsDataStoreHelper by lazy { SettingsDataStoreHelper(application) }
    val counterLiveData: LiveData<Int> by lazy { settingsDataStoreHelper.getCounter }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataStoreHelper.syncSettings()
        }
    }

    fun incrementCounter() {
        viewModelScope.launch {
            settingsDataStoreHelper.incrementCounter()
        }
    }

    fun resetCounter() {
        viewModelScope.launch {
            settingsDataStoreHelper.resetCounter()
        }
    }

    fun getCounterSync() = settingsDataStoreHelper.getCounterSync()

    fun clearDataStore() {
        viewModelScope.launch {
            settingsDataStoreHelper.clearSettings()
        }
    }
}