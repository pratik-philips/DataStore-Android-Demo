package datastorehelper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.datastorehelper.R
import kotlinx.android.synthetic.main.home_activity.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        start_preference_storage.setOnClickListener {
            startActivity(Intent(this, TestDataStorePreferenceActivity::class.java))
        }

        start_proto_data_store.setOnClickListener {
            startActivity(Intent(this, TestProtoDataStoreActivity::class.java))
        }

        start_proto_data_store_with_user_data.setOnClickListener {
            startActivity(Intent(this, TestProtoDataStoreUserData::class.java))
        }
    }

}