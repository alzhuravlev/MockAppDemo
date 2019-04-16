package com.crane.mockappdemo.main

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crane.mockappdemo.BaseViewModel
import com.crane.mockappdemo.ProgressState
import com.crane.mockappdemo.sample1.Activity1
import com.crane.mockappdemo.sample1.Activity2

data class MainItem(
    val activity: Class<out AppCompatActivity>,
    val decription: String
)

class MainViewModel(application: Application) : BaseViewModel(application) {

    companion object {

        private val _data = listOf<MainItem>(
            MainItem(
                Activity1::class.java,
                """
                    `MockAppActivity` do a lot of useful things: coloring status and nav bars,
                    controls full screen mode, inflate bottom sheet and nav drawer for you,
                    apply themes. At this moment it is very recommend to inherit your activity from it.
                    But you still able to inflate layout without it with several lines of code (see Inflate layout in general).

                    If you know project/layout name in advance you can use annotation `@MockAppLayout`
                """.trimIndent()
            ),
            MainItem(
                Activity2::class.java,
                """
                    `MockAppActivity` do a lot of useful things: coloring status and nav bars,
                    controls full screen mode, inflate bottom sheet and nav drawer for you,
                    apply themes. At this moment it is very recommend to inherit your activity from it.
                    But you still able to inflate layout without it with several lines of code (see Inflate layout in general).

                    If you know project/layout name in advance you can use annotation `@MockAppLayout`
                """.trimIndent()
            )
        )

    }

    private val _samples = MutableLiveData<List<MainItem>>()
    val samples: LiveData<List<MainItem>>
        get() = _samples

    fun onLoadSamples() {
        launch {
            _samples.postValue(_data)
        }
    }

}