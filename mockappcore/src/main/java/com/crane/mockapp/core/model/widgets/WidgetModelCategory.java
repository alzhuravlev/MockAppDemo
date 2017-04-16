package com.crane.mockapp.core.model.widgets;

import com.crane.mockapp.core.R;

/**
 * Created by crane2002 on 1/4/2017.
 */

public enum WidgetModelCategory {

    WIDGETS(R.string.widget_model_category_widgets),
    CONTAINERS(R.string.widget_model_category_containers),
    CUSTOM(R.string.widget_model_category_custom);

    private int titleResource;

    WidgetModelCategory(int titleResource) {
        this.titleResource = titleResource;
    }

    public int getTitleResource() {
        return titleResource;
    }
}
