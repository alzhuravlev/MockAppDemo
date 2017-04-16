package crane.com.mockappdemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.crane.mockapp.core.ImageProvider;
import com.crane.mockapp.core.ThemeUtils;
import com.crane.mockapp.core.Utils;
import com.crane.mockapp.core.model.layouts.LayoutDescriptor;
import com.crane.mockapp.core.model.layouts.ProjectServiceFactory;
import com.crane.mockapp.core.model.theme.ThemeModel;
import com.crane.mockapp.core.model.theme.ThemeModelServiceFactory;

public class MainActivity extends AppCompatActivity implements ImageProvider {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup container = (ViewGroup) findViewById(R.id.container);

        LayoutDescriptor layoutDescriptor = ProjectServiceFactory.getInstace(this).loadLayoutByName("MessengerApp", "MainLayout");

        if (layoutDescriptor != null && layoutDescriptor.getLayout() != null) {
            int primaryThemeId = layoutDescriptor.getPrimaryThemeId();
            int accentThemeId = layoutDescriptor.getAccentThemeId();
            Object view = com.crane.mockapp.core.model.layouts.LayoutInflater.inflate(this, layoutDescriptor.getLayout(), container, true);
            ThemeModel themeModel = ThemeModelServiceFactory.getInstace(this).buildTheme(primaryThemeId, accentThemeId);
            ThemeUtils.applyThemeToViewHierarchy(this, false, this, view, themeModel, false);
        }
    }

    @Override
    public Bitmap loadImage(String imageFileName, int reqWidth, int reqHeight) {
        return ProjectServiceFactory.getInstace(this).loadImage(null, imageFileName, reqWidth, reqHeight);
    }
}
