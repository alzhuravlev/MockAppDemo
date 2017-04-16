package com.crane.mockapp.core.model.widgets;

import android.content.Context;

/**
 * Created by crane2002 on 1/4/2017.
 */

public class WidgetModelServiceFactory {
    private static WidgetModelService modelService;

    public static WidgetModelService getInstace(Context context) {
        if (modelService == null)
            modelService = new WidgetModelServiceImpl(context);
        return modelService;
    }
}
