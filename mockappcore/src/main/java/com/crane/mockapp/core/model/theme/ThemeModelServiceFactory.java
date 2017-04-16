package com.crane.mockapp.core.model.theme;

import android.content.Context;

/**
 * Created by crane2002 on 1/4/2017.
 */

public class ThemeModelServiceFactory {
    private static ThemeModelService service;

    public static ThemeModelService getInstace(Context context) {
        if (service == null)
            service = new ThemeModelServiceImpl(context);
        return service;
    }
}
