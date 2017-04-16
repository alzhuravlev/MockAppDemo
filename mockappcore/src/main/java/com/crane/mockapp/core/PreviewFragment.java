package com.crane.mockapp.core;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.crane.mockapp.core.Arguments;
import com.crane.mockapp.core.ImageProvider;
import com.crane.mockapp.core.ThemeUtils;
import com.crane.mockapp.core.Utils;
import com.crane.mockapp.core.model.layouts.LayoutDescriptor;
import com.crane.mockapp.core.model.layouts.ProjectServiceFactory;
import com.crane.mockapp.core.model.theme.ThemeModel;
import com.crane.mockapp.core.model.theme.ThemeModelServiceFactory;

/**
 * Created by crane2002 on 3/13/2017.
 */

public class PreviewFragment extends DialogFragment implements ImageProvider {

    private int primaryThemeId;
    private int accentThemeId;

    private String getProjectIdArgument() {
        Bundle arguments = getArguments();
        if (arguments == null)
            return null;
        return arguments.getString(Arguments.ARGUMENT_PROJECT_ID);
    }

    private String getLayoutIdArgument() {
        Bundle arguments = getArguments();
        if (arguments == null)
            return null;
        return arguments.getString(Arguments.ARGUMENT_LAYOUT_ID);
    }

    private String getLayoutDescriptorArgument() {
        Bundle arguments = getArguments();
        if (arguments == null)
            return null;
        return arguments.getString(Arguments.ARGUMENT_LAYOUT_DESCRIPTOR);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        LayoutDescriptor layoutDescriptor = Utils.loadLayoutDescriptor(getContext(), getLayoutDescriptorArgument(), getProjectIdArgument(), getLayoutIdArgument());
        if (layoutDescriptor != null && layoutDescriptor.getLayout() != null) {
            primaryThemeId = layoutDescriptor.getPrimaryThemeId();
            accentThemeId = layoutDescriptor.getAccentThemeId();
            Object view = com.crane.mockapp.core.model.layouts.LayoutInflater.inflate(getContext(), layoutDescriptor.getLayout(), container, false);
            if (view instanceof View)
                return (View) view;
        }
        return null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), getTheme());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ThemeModel themeModel = ThemeModelServiceFactory.getInstace(getContext()).buildTheme(primaryThemeId, accentThemeId);
        ThemeUtils.applyThemeToViewHierarchy(getContext(), false, this, view, themeModel, false);
    }

    @Override
    public Bitmap loadImage(String imageFileName, int reqWidth, int reqHeight) {
        return ProjectServiceFactory.getInstace(getContext()).loadImage(getProjectIdArgument(), imageFileName, reqWidth, reqHeight);
    }
}
