package com.crane.mockapp.core.model.layouts;

import android.content.Context;

import com.crane.mockapp.core.PreviewActivity;
import com.crane.mockapp.core.model.theme.ThemeModel;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Created by crane2002 on 3/12/2017.
 */

public class PropEventOpenDrawer extends PropEventValue {

    @JsonCreator
    public PropEventOpenDrawer() {
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    protected void doExecute(Context context, Object view, ThemeModel themeModel) {
        if (context instanceof PreviewActivity)
            ((PreviewActivity) context).openDrawer();
    }

    @Override
    public PropEventOpenDrawer clone() {
        return new PropEventOpenDrawer();
    }
}
