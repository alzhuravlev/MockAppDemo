package com.crane.mockapp.core.model.layouts;

import android.content.Context;

/**
 * Created by crane2002 on 1/1/2017.
 */

public class ProjectServiceFactory {
    private static ProjectService service;

    public static ProjectService getInstance(Context context) {
        if (service == null)
            service = new ProjectServiceImpl(context);
        return service;
    }

    public static void init(Context context, String path) {
        service = new ProjectServiceImpl(context, path);
    }
}
