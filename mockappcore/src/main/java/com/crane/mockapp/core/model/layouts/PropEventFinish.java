package com.crane.mockapp.core.model.layouts;

import android.app.Activity;
import android.content.Context;

import com.crane.mockapp.core.model.theme.ThemeModel;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Created by crane2002 on 3/12/2017.
 */

public class PropEventFinish extends PropEventValue {

    @JsonCreator
    public PropEventFinish() {
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    protected void doExecute(Context context, Object view, ThemeModel themeModel) {
        if (context instanceof Activity)
            ((Activity) context).finish();
    }

    @Override
    public PropEventFinish clone() {
        return new PropEventFinish();
    }
}
