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

    private fun shortcutAdd(name: String, number: Int) {
        // Intent to be send, when shortcut is pressed by user ("launched")
        val shortcutIntent = Intent(applicationContext, Play::class.java)
        shortcutIntent.action = Constants.ACTION_PLAY

        // Create bitmap with number in it -> very default. You probably want to give it a more stylish look
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        paint.setColor(-0x7f7f80) // gray
        paint.setTextAlign(Paint.Align.CENTER)
        paint.setTextSize(50)
        Canvas(bitmap).drawText("" + number, 50f, 50f, paint)
        (findViewById(R.id.icon) as ImageView).setImageBitmap(bitmap)

        // Decorate the shortcut
        val addIntent = Intent()
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name)
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bitmap)

        // Inform launcher to create shortcut
        addIntent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
        applicationContext.sendBroadcast(addIntent)
    }

    private fun shortcutDel(name: String) {
        // Intent to be send, when shortcut is pressed by user ("launched")
        val shortcutIntent = Intent(applicationContext, Play::class.java)
        shortcutIntent.action = Constants.ACTION_PLAY

        // Decorate the shortcut
        val delIntent = Intent()
        delIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
        delIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name)

        // Inform launcher to remove shortcut
        delIntent.action = "com.android.launcher.action.UNINSTALL_SHORTCUT"
        applicationContext.sendBroadcast(delIntent)
    }
}