package com.crane.mockappdemo.sample1

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.crane.mockapp.annotations.MockAppLayout
import com.crane.mockapp.core.MockApp
import com.crane.mockapp.core.MockAppActivity
import com.crane.mockapp.core.MockAppFragment
import com.crane.mockappdemo.R
import java.lang.IllegalStateException

@MockAppLayout(projectName = "basic_sample", layoutName = "TabLayout")
class Fragment3 : MockAppActivity() {

    lateinit var pager: ViewPager

    /**
     * Override this method to make a custom binding.
     * In general it is much easier to use @MockAppView annotation to let library
     * bind views by CustomTag (see Binding Views section in the doc)
     */
    override fun bindMockAppLayout(view: Any?) {
        pager = MockApp.findViewByViewIdPath(view, "Linear1/Pager9")!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // id is required by PagerAdapter
        pager.id = R.id.VIEW_PAGER

        pager.adapter = Adapter(supportFragmentManager)
    }
}

/**
 * Just like extending MockAppActivity you have two options:
 * @MockAppLayout or override getProjectId/getProjectName and getLayoutId/getLayoutName
 */
@MockAppLayout(projectName = "basic_sample", layoutName = "FrameLayout")
class MyFragment1 : MockAppFragment() {
}

@MockAppLayout(projectName = "basic_sample", layoutName = "RecyclerView")
class MyFragment2 : MockAppFragment() {
}

@MockAppLayout(projectName = "basic_sample", layoutName = "Tile")
class MyFragment3 : MockAppFragment() {
}

class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MyFragment1()
            1 -> MyFragment2()
            2 -> MyFragment3()
            else -> throw IllegalStateException("Illegal position: $position")
        }
    }

    override fun getCount(): Int = 3
}