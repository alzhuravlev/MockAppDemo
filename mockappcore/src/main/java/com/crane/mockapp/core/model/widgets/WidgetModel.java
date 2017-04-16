package com.crane.mockapp.core.model.widgets;

import android.content.Context;

/**
 * Created by crane2002 on 1/4/2017.
 */
public abstract class WidgetModel {

    protected final Context context;

    WidgetModel(Context context) {
        this.context = context;
    }

    abstract public void assignDefaults(Context context, Object view);

    abstract protected Object doNewViewInstance(Object parent);

    abstract public String getTitle();

    public final Object newViewInstance(Object parent) {
        return doNewViewInstance(parent);
    }

    public WidgetModelCategory getCategory() {
        return WidgetModelCategory.CUSTOM;
    }
}
