package com.crane.mockapp.core.model.props;

import android.content.Context;

/**
 * Created by crane2002 on 1/4/2017.
 */

public class PropModelServiceFactory {
    private static PropModelService service;

    public static PropModelService getInstace(Context context) {
        if (service == null)
            service = new PropModelServiceImpl(context);
        return service;
    }
}
