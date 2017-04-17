package com.crane.mockapp.core;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.crane.mockapp.core.model.layouts.LayoutDescriptor;
import com.crane.mockapp.core.model.layouts.LayoutInflater;
import com.crane.mockapp.core.model.layouts.ProjectServiceFactory;
import com.crane.mockapp.core.model.theme.ThemeModel;
import com.crane.mockapp.core.model.theme.ThemeModelColor;
import com.crane.mockapp.core.model.theme.ThemeModelServiceFactory;

/**
 * Created by azhuravlev on 1/27/2017.
 */

public class PreviewActivity extends AppCompatActivity implements ImageProvider {

    private View statusBarBackgroundView;
    private ViewGroup navDrawerContainer;
    private DrawerLayout drawerLayout;

    private String getProjectIdArgument() {
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(Arguments.ARGUMENT_PROJECT_ID))
            return null;
        return intent.getStringExtra(Arguments.ARGUMENT_PROJECT_ID);
    }

    private String getLayoutIdArgument() {
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(Arguments.ARGUMENT_LAYOUT_ID))
            return null;
        return intent.getStringExtra(Arguments.ARGUMENT_LAYOUT_ID);
    }

    private String getLayoutDescriptorArgument() {
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(Arguments.ARGUMENT_LAYOUT_DESCRIPTOR))
            return null;
        return intent.getStringExtra(Arguments.ARGUMENT_LAYOUT_DESCRIPTOR);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Window window = getWindow();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(0x0);
//        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//// Disable status bar translucency (requires API 19)
//        window.getAttributes().flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// Set a color (requires API 21)

        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_activity);
        statusBarBackgroundView = findViewById(R.id.status_bar_background);
        navDrawerContainer = (ViewGroup) findViewById(R.id.nav_container);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        LayoutDescriptor layoutDescriptor = Utils.loadLayoutDescriptor(this, getLayoutDescriptorArgument(), getProjectIdArgument(), getLayoutIdArgument());
        ThemeModelColor statusBarThemeModelColor = layoutDescriptor.getStatusBarColor();
        if (statusBarThemeModelColor != null) {
            ThemeModel themeModel = ThemeModelServiceFactory.getInstace(this).buildTheme(layoutDescriptor.getPrimaryThemeId(), layoutDescriptor.getAccentThemeId());
            int statusBarColor = themeModel.getColor(statusBarThemeModelColor);
            statusBarBackgroundView.setBackgroundColor(statusBarColor);
        }

        if (layoutDescriptor.getNavDrawerLayout() != null) {
            LayoutDescriptor navDrawerLayoutDescriptor = Utils.loadLayoutDescriptor(this, null, layoutDescriptor.getNavDrawerLayout().getProjectId(), layoutDescriptor.getNavDrawerLayout().getLayoutId());
            if (navDrawerLayoutDescriptor != null && navDrawerLayoutDescriptor.getLayout() != null) {
                Object view = LayoutInflater.inflate(this, navDrawerLayoutDescriptor.getLayout(), navDrawerContainer, true);
                ThemeModel themeModel = ThemeModelServiceFactory.getInstace(this).buildTheme(navDrawerLayoutDescriptor.getPrimaryThemeId(), navDrawerLayoutDescriptor.getAccentThemeId());
                ThemeUtils.applyThemeToViewHierarchy(this, false, this, view, themeModel, false);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        }

        PreviewFragment previewFragment = new PreviewFragment();

        Bundle arguments = new Bundle();
        arguments.putString(Arguments.ARGUMENT_PROJECT_ID, getProjectIdArgument());
        arguments.putString(Arguments.ARGUMENT_LAYOUT_ID, getLayoutIdArgument());
        arguments.putString(Arguments.ARGUMENT_LAYOUT_DESCRIPTOR, getLayoutDescriptorArgument());
        previewFragment.setArguments(arguments);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, previewFragment);
        transaction.commit();
    }

    public void showDialog(String projectId, String layoutId) {
        PreviewFragment previewFragment = new PreviewFragment();

        Bundle arguments = new Bundle();
        arguments.putString(Arguments.ARGUMENT_PROJECT_ID, projectId);
        arguments.putString(Arguments.ARGUMENT_LAYOUT_ID, layoutId);
        previewFragment.setArguments(arguments);

        previewFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public Bitmap loadImage(String imageFileName, int reqWidth, int reqHeight) {
        return ProjectServiceFactory.getInstance(this).loadImage(getProjectIdArgument(), imageFileName, reqWidth, reqHeight);
    }

    public void openDrawer() {
        if (drawerLayout.getDrawerLockMode(Gravity.LEFT) == DrawerLayout.LOCK_MODE_UNLOCKED)
            drawerLayout.openDrawer(Gravity.START);
    }
}
