package com.crane.mockappdemo.main

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crane.mockappdemo.BaseViewModel
import com.crane.mockappdemo.ProgressState
import com.crane.mockappdemo.sample1.*

data class MainItem(
    val activity: Class<out AppCompatActivity>,
    val decription: String
)

class MainViewModel(application: Application) : BaseViewModel(application) {

    companion object {

        private val _data = listOf(
            MainItem(
                Activity1::class.java,
                """
                    This sample illustrates how inflate Activity's contentView using @MockAppLayout annotation.
                    This is easiest way to inflate in you know projectName/layoutName in advance
                """.trimIndent()
            ),
            MainItem(
                Activity2::class.java,
                """
                    To let your code resolve what project/layout to infate in runtime just override
                    (`getProjectId` OR `getProjectName`) AND (`getLayoutId` OR `getLayoutName`)

                    If layout could not be inflated for any reason you have a chance to take care
                    of it overriding `inflateDefaultLayout`
                """.trimIndent()
            ),
            MainItem(
                Fragment3::class.java,
                """
                    Just like extending MockAppActivity you have two options:
                    @MockAppLayout or override getProjectId/getProjectName and getLayoutId/getLayoutName
                """.trimIndent()
            ),
            MainItem(
                GeneralCase4::class.java,
                """
                    Inflating a layout in general case include 3 steps:
                    1. Load LayoutDescriptor from *.json file located somewhere (or just a string)
                    2. Inflate a layout to get a View using variation of LayoutInflater.inflate...
                    3. Bind views from inflated layout (see Binding Views for more details)
                """.trimIndent()
            ),
            MainItem(
                RecyclerView5::class.java,
                """
                    This sample illustrates how to create ready-to-use ViewHolder
                """.trimIndent()
            ),
            MainItem(
                BindViews6::class.java,
                """
                    This sample illustrates how to bind views (see Binding Views in the doc)
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