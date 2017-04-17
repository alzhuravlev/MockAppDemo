package crane.com.mockappdemo;

import com.crane.mockapp.core.model.layouts.ProjectServiceFactory;

import java.io.File;

/**
 * Created by azhuravlev on 4/17/2017.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String path = new File(getApplicationContext().getFilesDir(), "MockApp").getPath();
        ProjectServiceFactory.init(getApplicationContext(), path);
    }
}
