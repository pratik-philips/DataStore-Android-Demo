package datastorehelper

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.datastorehelper.R
import kotlinx.android.synthetic.main.proto_data_store_activity.*
import kotlin.math.absoluteValue

class TestProtoDataStoreActivity: AppCompatActivity() {

    private val viewModel: SettingsDataStoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.proto_data_store_activity)
        attachLiveDataObservers()
        attachOnClickFunctions()
    }

    private fun attachLiveDataObservers() {
        viewModel.counterLiveData.observe(this, {
            updateText(it.absoluteValue)
        })
    }

    private fun attachOnClickFunctions() {
        incrementCounter.setOnClickListener {
            viewModel.incrementCounter()
        }

        reset_counter.setOnClickListener {
            viewModel.resetCounter()
        }

        syncValueButton.setOnClickListener {
            updateSyncedValue()
        }

        clearDataStore.setOnClickListener {
            clearDataStore()
        }
    }

    private fun updateText(value: Int) {
        counter_value.text = value.toString()
    }

    private fun updateSyncedValue() {
        syncValue.text = viewModel.getCounterSync().toString()
    }

    private fun clearDataStore() {
        viewModel.clearDataStore()
    }

}