package crane.com.mockappdemo;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crane.mockapp.core.ImageProvider;
import com.crane.mockapp.core.ThemeUtils;
import com.crane.mockapp.core.model.layouts.LayoutDescriptor;
import com.crane.mockapp.core.model.layouts.ProjectServiceFactory;
import com.crane.mockapp.core.model.theme.ThemeModel;
import com.crane.mockapp.core.model.theme.ThemeModelServiceFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements ImageProvider {

    private DownloadTask downloadTask;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        refresh(true);
    }

    @Override
    protected void onStop() {
        if (downloadTask != null) {
            downloadTask.cancel(true);
            downloadTask = null;
        }
        super.onStop();
    }

    @Override
    public Bitmap loadImage(String imageFileName, int reqWidth, int reqHeight) {
        return ProjectServiceFactory.getInstance(this).loadImage(null, imageFileName, reqWidth, reqHeight);
    }

    private void refresh(boolean download) {
        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        LayoutDescriptor layoutDescriptor = ProjectServiceFactory.getInstance(this).loadLayoutByName("Cards", "LargeMedia_1_1");
        if (layoutDescriptor != null && layoutDescriptor.getLayout() != null) {
            int primaryThemeId = layoutDescriptor.getPrimaryThemeId();
            int accentThemeId = layoutDescriptor.getAccentThemeId();
            Object view = com.crane.mockapp.core.model.layouts.LayoutInflater.inflate(this, layoutDescriptor.getLayout(), container, true);
            ThemeModel themeModel = ThemeModelServiceFactory.getInstace(this).buildTheme(primaryThemeId, accentThemeId);
            ThemeUtils.applyThemeToViewHierarchy(this, false, this, view, themeModel, false);
        } else if (download)
            downloadProject("https://rkpmww-dm2305.files.1drv.com/y4mbwF3JT-r5vI4a3FOLQMi_GjJJuDcLl06n-lKIx6I54UNYRXsoLJ_2X_AvmyqRp2fWnr_iTRSQY_t6x4xMBJIVjdfH3NM8IU9is2xJPE-Ewxb_4QR44jKAZVx6qsay-6HLrZzy2oIVC7ndXGYbKQskDB7NhLRJ4axEQhnLmmDs91wrymjrXz-5983etqp6exJ/Cards.zip?download&psid=1");
        else
            Toast.makeText(this, "Unable to inflate layout", Toast.LENGTH_LONG).show();
    }

    private void downloadProject(String url) {
        if (downloadTask != null)
            return;

        downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    private class DownloadTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                File zipFile = downloadUrl(new URL(params[0]));
                if (zipFile != null) {
                    ProjectServiceFactory.getInstance(MainActivity.this).deleteProjectByName("Cards");
                    ProjectServiceFactory.getInstance(MainActivity.this).importZip(zipFile.getPath());
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Unable to download zip";
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String errorMessage) {
            progressBar.setVisibility(View.GONE);
            if (errorMessage == null)
                refresh(false);
            else
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
        }

        private File downloadUrl(URL url) throws IOException {
            InputStream stream = null;
            HttpsURLConnection connection = null;
            File result = new File(ProjectServiceFactory.getInstance(MainActivity.this).getLocalTmpFolder(), "Cards.zip");
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                publishProgress("CONNECT_SUCCESS");

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                stream = connection.getInputStream();

                publishProgress("GET_INPUT_STREAM_SUCCESS");

                copy(stream, new FileOutputStream(result));

            } finally {
                if (stream != null) {
                    stream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        private void copy(InputStream is, OutputStream os) {
            byte[] buf = new byte[2048];
            int l;

            try (InputStream is_ = is; OutputStream os_ = os) {
                while ((l = is_.read(buf)) != -1)
                    os_.write(buf, 0, l);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
