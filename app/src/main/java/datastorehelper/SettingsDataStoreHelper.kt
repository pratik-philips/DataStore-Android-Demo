package datastorehelper

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.InputStream
import java.io.OutputStream


val Context.settingsDataStore: DataStore<Settings> by dataStore(
    fileName = "settings.proto",
    serializer = SettingsSerializer
)

class SettingsDataStoreHelper(private val context: Context) {

    private val defaultValue = 0

    val getCounter: LiveData<Int> = context.settingsDataStore.data
        .map { settings ->
            settings.exampleCounter
        }.asLiveData()

    val getUserName: LiveData<String> = context.settingsDataStore.data.
    map { settings ->
        settings.userName
    }.asLiveData()

    val getPreferredUserName: LiveData<String> = context.settingsDataStore.data.
    map { settings ->
        settings.preferredName
    }.asLiveData()

    val getUserMailId: LiveData<String> = context.settingsDataStore.data.
    map { settings ->
        settings.mailId
    }.asLiveData()

    val getIsUserVerified: LiveData<Boolean> = context.settingsDataStore.data.
    map { settings ->
        settings.isUserVerified
    }.asLiveData()

    fun getCounterSync() = runBlocking { context.settingsDataStore.data.first().exampleCounter }

    suspend fun incrementCounter(by: Int = 1) {
        context.settingsDataStore.updateData {
            it.toBuilder().setExampleCounter(it.exampleCounter + by).build()
        }
    }

    suspend fun updateUserData(data: UserData) {
        context.settingsDataStore.updateData {
            with(it.toBuilder()) {
                setUserName(data.userName).build()
                setPreferredName(data.userPreferredName).build()
                setMailId(data.mailId).build()
                setIsUserVerified(data.isUserVerified).build()
            }
        }
    }

    suspend fun resetCounter() {
        context.settingsDataStore.updateData {
            it.toBuilder().setExampleCounter(defaultValue).build()
        }
    }

    suspend fun syncSettings() {
        context.settingsDataStore.data.first()
    }

    suspend fun clearSettings() {
        context.settingsDataStore.updateData { settings ->
            settings.toBuilder().clearExampleCounter().build()
            settings.toBuilder().clearUserName().build()
            settings.toBuilder().clearPreferredName().build()
            settings.toBuilder().clearMailId().build()
            settings.toBuilder().clearIsUserVerified().build()
        }
    }
}

@Suppress("BlockingMethodInNonBlockingContext")
object SettingsSerializer : Serializer<Settings> {

    override suspend fun readFrom(input: InputStream): Settings {
        try {
            return Settings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Settings, output: OutputStream) {
        t.writeTo(output)
    }

    override val defaultValue: Settings
        get() = Settings.getDefaultInstance()
}