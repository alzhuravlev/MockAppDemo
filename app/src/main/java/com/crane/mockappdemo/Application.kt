package com.crane.mockappdemo

import android.app.Application
import android.os.Environment
import com.crane.mockapp.core.MockApp
import java.io.File
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import android.content.Intent
import android.media.session.PlaybackState.ACTION_PLAY
import android.R
import android.graphics.Paint.Align
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.crane.mockapp.core.model.layouts.AssetsProjectSource
import com.crane.mockapp.core.model.layouts.LocalProjectSource
import com.crane.mockapp.core.model.layouts.ProjectSource


class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        // this allows to inflate layouts from assets folder of your application
        // just copy folder of your project under /assets/MockApp/
        val assetsProjectSource = AssetsProjectSource(this)

        val localProjectSource = LocalProjectSource(
            this,
            // Optionally you can override location of the local project's storage.
            // Default is /sdcard/Documents/MockApp
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "MockAppDemo"
            )
        )

        // Initialize several sources. Order is matter. Projects will be
        // searched among all sources in order you pass it into init method
        ProjectSource.init(localProjectSource, assetsProjectSource)
    }
}