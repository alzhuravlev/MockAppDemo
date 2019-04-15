package crane.com.mockappdemo.main

import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.crane.mockapp.annotations.MockAppLayout
import com.crane.mockapp.annotations.MockAppView
import com.crane.mockapp.core.MockAppActivity
import com.crane.mockapp.core.PaddingItemDecoration
import com.google.android.material.appbar.AppBarLayout

@MockAppLayout(projectName = "mockappdemo", layoutName = "main")
class MainActivity : MockAppActivity() {

    @MockAppView
    lateinit var recyclerView: RecyclerView

    @MockAppView
    lateinit var appBarLayout: AppBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.postDelayed({
            appBarLayout.getChildAt(0).minimumHeight = appBarLayout.getChildAt(0).height
        }, 10)
    }
}