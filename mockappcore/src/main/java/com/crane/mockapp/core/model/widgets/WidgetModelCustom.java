package com.crane.mockapp.core.model.widgets;

import android.content.Context;

/**
 * Created by azhuravlev on 3/1/2017.
 */

public class WidgetModelCustom extends WidgetModel {
    public WidgetModelCustom(Context context) {
        super(context);
    }

    @Override
    public void assignDefaults(Context context, Object view) {
    }

    @Override
    protected Object doNewViewInstance(Object parent) {
        return null;
    }

    @Override
    public String getTitle() {
        return context.getResources().getString(WidgetModelCategory.CUSTOM.getTitleResource());
    }

    @Override
    public WidgetModelCategory getCategory() {
        return WidgetModelCategory.CUSTOM;
    }
}
