package datastorehelper

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.datastorehelper.R
import kotlinx.android.synthetic.main.user_data_activity.*

class TestProtoDataStoreUserData: AppCompatActivity() {

    private val viewModel: SettingsDataStoreViewModel by viewModels()
    private var name: String = ""
    private var prefname: String = ""
    private var mailId: String = ""
    private var isUserVerified: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_data_activity)
        attachLiveDataObservers()
        attachOnCLick()
    }

    private fun attachLiveDataObservers() {
        viewModel.userName.observe(this, {
            name = "Name: $it"
            updateCard()
        })

        viewModel.isUserVerified.observe(this, {
            isUserVerified = it
            updateCard()
        })

        viewModel.mailID.observe(this, {
            mailId = "Mail id: $it"
            updateCard()
        })

        viewModel.preferredName.observe(this, {
            prefname = "Preferred Name: $it"
            updateCard()
        })
    }

    private fun attachOnCLick() {
        clearData.setOnClickListener {
            viewModel.clearDataStore()
        }
        verify_user.setOnClickListener {
            isUserVerified = true
        }

        submitData.setOnClickListener {
            viewModel.updateUserData(
                UserData(
                    edit_name.text.toString(),
                    edit_preferred_name.text.toString(),
                    edit_mailId.text.toString(),
                    isUserVerified)
            )
        }
    }

    private fun updateCard() {
        userName.text = name
        userMailId.text = mailId
        userPreferredName.text = prefname
        if(isUserVerified) {
            iconIsUserVerified.setImageResource(R.drawable.verified_user)
        }
        else {
            iconIsUserVerified.setImageResource(R.drawable.not_verified)
        }
    }

}