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

//        window.decorView.postDelayed({
        val lp = recyclerView.layoutParams
        if (lp is CoordinatorLayout.LayoutParams) {
            val behavior = lp.behavior
            if (behavior is AppBarLayout.ScrollingViewBehavior) {
                behavior.overlayTop = 70
                recyclerView.layoutParams = lp
                val decoration = recyclerView.getItemDecorationAt(0)
                if (decoration is PaddingItemDecoration) {
//                        decoration.paddingTopExtra = appBarLayout.height
//                        recyclerView.invalidateItemDecorations()
                }
            }
        }
//        }, 1000)
    }
}