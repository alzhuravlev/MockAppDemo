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
import com.crane.mockapp.core.OperationCallback;
import com.crane.mockapp.core.ThemeUtils;
import com.crane.mockapp.core.model.layouts.LayoutDescriptor;
import com.crane.mockapp.core.model.layouts.ProjectModel;
import com.crane.mockapp.core.model.layouts.ProjectServiceFactory;
import com.crane.mockapp.core.model.theme.ThemeModel;
import com.crane.mockapp.core.model.theme.ThemeModelServiceFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
        LayoutDescriptor layoutDescriptor = ProjectServiceFactory.getInstance(this).loadLayoutByName("Cards", "LargeMedia_16_9");
        if (layoutDescriptor != null && layoutDescriptor.getLayout() != null) {
            int primaryThemeId = layoutDescriptor.getPrimaryThemeId();
            int accentThemeId = layoutDescriptor.getAccentThemeId();
            Object view = com.crane.mockapp.core.model.layouts.LayoutInflater.inflate(this, layoutDescriptor.getLayout(), container, true);
            ThemeModel themeModel = ThemeModelServiceFactory.getInstace(this).buildTheme(primaryThemeId, accentThemeId);
            ThemeUtils.applyThemeToViewHierarchy(this, false, this, view, themeModel, false);
        } else if (download)
            downloadProject("https://1drv.ms/u/s!AiC9PwIl76HmhTMm_qg1jWaNR50c");
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
                ProjectServiceFactory.getInstance(MainActivity.this).deleteProjectByName("Cards", new OperationCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                    }

                    @Override
                    public void onFail(String message) {
                    }
                });
                ProjectServiceFactory.getInstance(MainActivity.this).importZip(zipFile.getPath(), new OperationCallback<ProjectModel>() {
                    @Override
                    public void onSuccess(ProjectModel result) {
                    }

                    @Override
                    public void onFail(String message) {
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String errorMessage) {
            progressBar.setVisibility(View.GONE);
            refresh(false);
        }

        private File downloadUrl(URL url) throws IOException {
            InputStream stream = null;
            HttpsURLConnection connection = null;
            String result = null;
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
//                if (stream != null) {
//                    result = readStream(stream, 500);
//                }
            } finally {
                if (stream != null) {
                    stream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }
    }
}
