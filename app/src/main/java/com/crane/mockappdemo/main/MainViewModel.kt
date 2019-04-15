package crane.com.mockappdemo.main

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import crane.com.mockappdemo.BaseViewModel

data class MainItem(
    val activity: Class<out AppCompatActivity>,
    val decription: String
)

class MainViewModel(application: Application) : BaseViewModel(application) {
}