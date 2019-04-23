package com.crane.mockappdemo.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.crane.mockapp.annotations.MockAppLayout
import com.crane.mockapp.annotations.MockAppView
import com.crane.mockapp.core.MockAppActivity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.startActivity


@MockAppLayout(projectName = "mockappdemo", layoutName = "main")
class MainActivity : MockAppActivity() {

    @MockAppView
    private lateinit var recyclerView: RecyclerView

    @MockAppView
    private lateinit var viewOnGitHubButton: View

    @MockAppView
    private lateinit var installMockAppButton: View

    private lateinit var mainViewModel: MainViewModel

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.samples.observe(this, Observer { samples ->
            updateSamples(samples)
        })

        mainAdapter = MainAdapter(this)
        recyclerView.adapter = mainAdapter

        viewOnGitHubButton.setOnClickListener {
            viewUrl("https://github.com/alzhuravlev/MockAppDemo")
        }

        installMockAppButton.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=com.crane.mockapp")
                )
            )
        }

        mainViewModel.onLoadSamples()
    }

    override fun onStart() {
        super.onStart()
        if (isAppInstalled("com.crane.mockapp"))
            installMockAppButton.visibility = View.GONE
        else {
            installMockAppButton.visibility = View.VISIBLE
        }
    }

    fun updateSamples(samples: List<MainItem>) {
        mainAdapter.update(samples)
    }

    fun viewUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun isAppInstalled(uri: String): Boolean {
        val pm = packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return false
    }

}