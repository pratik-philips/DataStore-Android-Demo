package datastorehelper

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataStorePreferenceViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStoreHelper: AppPreference by lazy { AppPreference(application) }
    val counterLiveData: LiveData<Int> by lazy { dataStoreHelper.getCounter() }
    val stringPrefLiveData: LiveData<String> by lazy { dataStoreHelper.getStringPref() }
    val isEnabled: LiveData<Boolean> by lazy { dataStoreHelper.isEnabled() }

    init {
        dataStoreHelper.syncDataStore(viewModelScope)
    }

    fun incrementCounter() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreHelper.incrementCounter()
        }
    }

    fun toggle(enable: Boolean){
        viewModelScope.launch {
            dataStoreHelper.setEnable(enable)
        }
    }

    fun storeStringPref(str: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreHelper.setStringPref(str)
        }
    }

    fun resetCounter() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreHelper.resetCounter()
        }
    }

    fun clearPreferences() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreHelper.clearPreference()
        }
    }
}