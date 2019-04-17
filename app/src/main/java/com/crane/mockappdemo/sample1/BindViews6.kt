package com.crane.mockappdemo.sample1

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.crane.mockapp.annotations.MockAppLayout
import com.crane.mockapp.annotations.MockAppView
import com.crane.mockapp.core.MockApp
import com.crane.mockapp.core.MockAppActivity

@MockAppLayout(projectName = "icountries", layoutName = "page_settings")
class BindViews6 : MockAppActivity() {

    @MockAppView("titleText:TextView")
    lateinit var titleText: TextView

    @MockAppView
    lateinit var aboutText: TextView

    @MockAppView
    lateinit var aboutButton: View

    lateinit var feedbackText: TextView

    lateinit var feedbackButton: View

    override fun bindMockAppLayout(view: Any?) {
        super.bindMockAppLayout(view)

        feedbackText = MockApp.findViewWithCustomTag(view, "feedbackText:TextView")
        feedbackButton = MockApp.findViewWithCustomTag(view, "feedbackButton:View")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        titleText.text = "titleText"

        aboutText.text = "aboutText"
        aboutButton.setOnClickListener {
            Toast.makeText(this, "aboutButton", Toast.LENGTH_LONG).show()
        }

        feedbackText.text = "feedbackText"
        feedbackButton.setOnClickListener {
            Toast.makeText(this, "feedbackButton", Toast.LENGTH_LONG).show()
        }
    }
}