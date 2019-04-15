package crane.com.mockappdemo

import android.app.Application
import android.os.Environment
import com.crane.mockapp.core.model.layouts.ProjectServiceFactory
import java.io.File

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        // Optionally you can override location of the local project's storage.
        // Default is /sdcard/Documents/MockApp
        val path =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "MockAppDemo").path
        ProjectServiceFactory.init(this, path)
    }
}