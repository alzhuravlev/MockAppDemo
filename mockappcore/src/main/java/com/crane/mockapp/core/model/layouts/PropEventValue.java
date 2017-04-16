package com.crane.mockapp.core.model.layouts;

import android.content.Context;

import com.crane.mockapp.core.model.theme.ThemeModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by crane2002 on 1/28/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class PropEventValue {

    public abstract boolean isEmpty();

    protected abstract void doExecute(Context context, Object view, ThemeModel themeModel);

    public final void execute(Context context, Object view, ThemeModel themeModel) {
        doExecute(context, view, themeModel);
    }

    @Override
    public abstract Object clone();
}
