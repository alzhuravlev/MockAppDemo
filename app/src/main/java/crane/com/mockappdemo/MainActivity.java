package crane.com.mockappdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crane.mockapp.core.MockApp;
import com.crane.mockapp.core.model.layouts.LayoutDescriptor;
import com.crane.mockapp.core.model.layouts.LayoutInflater;
import com.crane.mockapp.core.model.layouts.ProjectService;
import com.crane.mockapp.core.model.layouts.ProjectServiceFactory;
import com.crane.mockapp.core.model.props.PropModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;

public class MainActivity extends Activity {

    private DownloadTask downloadTask;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        refreshWithCheck();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int grantResult : grantResults)
            if (grantResult != PackageManager.PERMISSION_GRANTED)
                return;
        permissionGranted();
    }

    private void permissionGranted() {
        refresh(true);
    }

    private void refreshWithCheck() {
        List<String> permissionsToAsk = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                permissionsToAsk.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                permissionsToAsk.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permissionsToAsk.size() > 0)
                requestPermissions(permissionsToAsk.toArray(new String[permissionsToAsk.size()]), 0);
            else
                permissionGranted();
        } else
            permissionGranted();
    }

    private void refresh(boolean download) {
        ViewGroup container = findViewById(R.id.container);
        LayoutDescriptor layoutDescriptor = ProjectServiceFactory.getInstance(this).loadLayoutByName("Cards", "LargeMedia_1_1");
        final Object view = LayoutInflater.inflate(this, layoutDescriptor, container, true);
//        final Object view = LayoutInflater.inflate(this, "Cards", "LargeMedia_1_1", container, true);
        if (view != null) {
            Object buttonObject3 = MockApp.findViewByViewIdPath(view, "FrameLayout_0/LinearLayout_2/ActionFlatButtons_Expand_4/Button_3");
            if (buttonObject3 != null) {
                Button button = (Button) buttonObject3;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "on click!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else if (download)
            downloadProject("https://onedrive.live.com/download?cid=E6A1EF25023FBD20&resid=E6A1EF25023FBD20%21814&authkey=AHz88qd2lREE4Ns");
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
            File zipFile;
            try {
                zipFile = downloadUrl(new URL(params[0]));
            } catch (Exception e) {
                e.printStackTrace();
                return "Unable to download zip";
            }

            try {
                ProjectServiceFactory.getInstance(MainActivity.this).deleteProjectByName("Cards");
                ProjectServiceFactory.getInstance(MainActivity.this).importZip(zipFile.getPath());
            } catch (Exception e) {
                e.printStackTrace();
                return "Unable to import project";
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
            if (errorMessage == null)
                refresh(false);
            else
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
        }

        private File downloadUrl(URL url) throws IOException {
            InputStream stream = null;
            HttpsURLConnection connection = null;
            File result = new File(ProjectServiceFactory.getInstance(MainActivity.this).getLocalTmpFolder(), "Cards.zip");
            if (result.exists())
                return result;

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
