package com.crane.mockapp.core.model.icons;

import android.content.Context;

/**
 * Created by crane2002 on 1/1/2017.
 */

public class IconServiceFactory {
    private static IconService service;

    public static IconService getInstace(Context context) {
        if (service == null)
            service = new IconServiceImpl(context);
        return service;
    }
}
