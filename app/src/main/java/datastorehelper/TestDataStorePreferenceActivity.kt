package datastorehelper

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.datastorehelper.R
import kotlinx.android.synthetic.main.activity_test.*

class TestDataStorePreferenceActivity : AppCompatActivity() {
    private val viewModel: DataStorePreferenceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        viewModel.counterLiveData.observe(this, {
            setCounter(it)
        })
        viewModel.countryLiveData.observe(this, {
            setCountry(it)
        })
        viewModel.isEnabled.observe(this, {
            incrementCounter.isEnabled = it
            setString.isEnabled = it
            resetCounter.isEnabled = it
            setEnableButtonText(it)
        })

        incrementCounter.setOnClickListener {
            viewModel.incrementCounter()
        }

        enablePref.setOnClickListener {
            viewModel.isEnabled.value?.let { it1 -> viewModel.toggle(!it1) }
        }

        setString.setOnClickListener {
            val view = View.inflate(this, R.layout.view_edittext, null)
            val etCountry = view.findViewById<EditText>(R.id.etCountry)
            etCountry.setText(viewModel.countryLiveData.value ?: "")

            val dialog = AlertDialog.Builder(this).setTitle("Set Country").setPositiveButton(
                "Save"
            ) { _, _ ->
                viewModel.storeCountry(etCountry.text.toString().trim())
            }.setNegativeButton("Cancel") { _, _ -> }.create()

            dialog.setView(view)
            dialog.show()
        }

        clearPref.setOnClickListener { viewModel.clearPreferences() }
        resetCounter.setOnClickListener { viewModel.resetCounter() }
    }

    private fun setEnableButtonText(enable: Boolean) {
        enablePref.text = if (enable) "Disable Pref" else "Enable Pref"
    }

    @SuppressLint("SetTextI18n")
    private fun setCounter(count: Int) {
        textView.text = "Count: $count"
    }

    @SuppressLint("SetTextI18n")
    private fun setCountry(country: String) {
        textView2.text = "Country: $country"
    }
}