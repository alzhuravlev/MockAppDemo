package com.crane.mockappdemo.sample1

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.crane.mockapp.annotations.MockAppLayout
import com.crane.mockapp.annotations.MockAppView
import com.crane.mockapp.core.MockApp
import com.crane.mockapp.core.MockAppActivity
import com.crane.mockapp.core.model.layouts.LayoutDescriptor
import com.crane.mockapp.core.model.layouts.LayoutInflater

class GeneralCase4 : AppCompatActivity() {

    @MockAppView
    lateinit var titleText: TextView

    @MockAppView("gameTitleText:TextView")
    lateinit var gameTitleText: TextView

    lateinit var progress: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Load LayoutDescriptor
        // use MockApp.loadLayoutDescriptor... variations for this
        val layoutDescriptor = MockApp.loadLayoutDescriptorByName(this, "icountries", "page_news")

        // 2. Inflate given LayoutDescriptor
        val view: View = LayoutInflater.inflate(this, layoutDescriptor, null, false)

        // 3. Bind views
        // MockApp.bindViews bind member fields to views by matching filed's '<field_name>:<field.class.simpleName>' and CustomTag
        // Take a look at page_news.tags.txt and CustomTag property in page_news.json
        // (see Binding Views section in the doc)
        MockApp.bindViews(this, this, view)

        // or bind view manually

        progress = MockApp.findViewWithCustomTag(view, "progress:View")

        setContentView(view)

        titleText.text = "It works!"
        gameTitleText.text = "It works too!!"

        progress.visibility = View.GONE
    }
}