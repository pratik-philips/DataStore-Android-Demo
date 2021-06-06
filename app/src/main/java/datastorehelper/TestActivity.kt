package datastorehelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.datastorehelper.R
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.runBlocking

class TestActivity : AppCompatActivity() {

    private val dataStoreHelper: AppPreference by lazy { AppPreference(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        dataStoreHelper.syncDataStore(lifecycleScope)
        setCountry(dataStoreHelper.getCountry())
        dataStoreHelper.getCounter().observe(this, Observer {
            setCounter(it)
        })
        dataStoreHelper.isEnabled().observe(this, Observer {
            textView.append(", isEnabled: $it")
        })

        button.setOnClickListener {
            incrementCounter()
        }
        textView.text = dataStoreHelper.getCountry()
    }

    private fun setCounter(count: Int) {
        textView.text = "Count: $count"
    }

    private fun setCountry(country: String) {
        textView2.text = "Country: $country"
    }

    private fun incrementCounter() {
        runBlocking {
            dataStoreHelper.incrementCounter()
            dataStoreHelper.setCountry("Aus")
        }
    }
}