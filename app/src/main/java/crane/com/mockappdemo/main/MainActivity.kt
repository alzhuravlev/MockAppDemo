package crane.com.mockappdemo.main

import androidx.recyclerview.widget.RecyclerView
import com.crane.mockapp.annotations.MockAppLayout
import com.crane.mockapp.annotations.MockAppView
import com.crane.mockapp.core.MockAppActivity

@MockAppLayout(projectName = "mockappdemo", layoutName = "main")
class MainActivity : MockAppActivity() {

    @MockAppView
    lateinit var recyclerView: RecyclerView

}