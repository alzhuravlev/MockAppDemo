package com.crane.mockappdemo.main

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.crane.mockappdemo.BaseViewModel

data class MainItem(
    val activity: Class<out AppCompatActivity>,
    val decription: String
)

class MainViewModel(application: Application) : BaseViewModel(application) {
}