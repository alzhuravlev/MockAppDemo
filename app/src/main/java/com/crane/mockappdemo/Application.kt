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


class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        // Optionally you can override location of the local project's storage.
        // Default is /sdcard/Documents/MockApp
        val path =
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "MockApp"
            ).path
        MockApp.init(this, path)
    }
}