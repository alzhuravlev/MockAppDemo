package com.crane.mockapp.core.model.props;

import com.crane.mockapp.core.R;

/**
 * Created by crane2002 on 1/4/2017.
 */

public enum PropModelCategory {
    PROP(-1),
    ICON(R.string.prop_model_category_icon),
    IMAGE(R.string.prop_model_category_image),
    TEXT(R.string.prop_model_category_text),
    TEXT_STYLE(R.string.prop_model_category_text_style),
    SIZE(R.string.prop_model_category_size),
    MARGINS(R.string.prop_model_category_margins),
    PADDINGS(R.string.prop_model_category_paddings),
    GRAVITY(R.string.prop_model_category_gravity),
    BACKGROUND(R.string.prop_model_category_background),
    DATA(R.string.prop_model_category_data),
    ON_CLICK(R.string.prop_model_category_onclick),
    ON_LONG_CLICK(R.string.prop_model_category_onlongclick),
    OUTLINE(R.string.prop_model_category_outline),
    VISIBILITY(R.string.prop_model_category_visibility),
    ANIMATION(R.string.prop_model_category_animation),
    GENERAL(R.string.prop_model_category_general);

    private int titleResource;

    PropModelCategory(int titleResource) {
        this.titleResource = titleResource;
    }

    public int getTitleResource() {
        return titleResource;
    }

    public static PropModel getEventPropModel(PropModelCategory category) {
        if (category != null)
            switch (category) {
                case ON_CLICK:
                    return PropModel.ON_CLICK;
                case ON_LONG_CLICK:
                    return PropModel.ON_LONG_CLICK;
            }
        return null;
    }
}
