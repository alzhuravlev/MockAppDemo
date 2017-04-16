package com.crane.mockapp.core.model.widgets;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crane.mockapp.core.R;
import com.crane.mockapp.core.Utils;
import com.crane.mockapp.core.model.props.PropModel;
import com.crane.mockapp.core.model.theme.ThemeModelColor;

/**
 * Created by crane2002 on 1/8/2017.
 */
class WidgetModelViewClass extends WidgetModel {
    private Class viewClass;

    WidgetModelViewClass(Context context, Class viewClass) {
        super(context);
        this.viewClass = viewClass;
    }

    @Override
    public void assignDefaults(Context context, Object view) {

        PropModel.BACKGROUND_COLOR.setProp(view, ThemeModelColor.TRANSPARENT, PropModel.PropModifyAction.CLEAR_MODIFY);

        PropModel.LAYOUT_MIN_WIDTH.setProp(view, Utils.dpToPx(context, 48.0f), PropModel.PropModifyAction.CLEAR_MODIFY);
        PropModel.LAYOUT_MIN_HEIGHT.setProp(view, Utils.dpToPx(context, 48.0f), PropModel.PropModifyAction.CLEAR_MODIFY);

        PropModel.LAYOUT_WIDTH.setProp(view, ViewGroup.LayoutParams.WRAP_CONTENT, PropModel.PropModifyAction.CLEAR_MODIFY);
        PropModel.LAYOUT_HEIGHT.setProp(view, ViewGroup.LayoutParams.WRAP_CONTENT, PropModel.PropModifyAction.CLEAR_MODIFY);

        if (view instanceof TextView) {
            PropModel.TEXT_COLOR.setProp(view, ThemeModelColor.TEXT_PRIMARY, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.TEXT_MIN_LINES.setProp(view, 1, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.TEXT_MAX_LINES.setProp(view, 50, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.LAYOUT_MIN_HEIGHT.setProp(view, Utils.dpToPx(context, 0.0f), PropModel.PropModifyAction.CLEAR_MODIFY);
        }

        if (view instanceof ImageView) {
            PropModel.ICON_COLOR.setProp(view, ThemeModelColor.ICON_ACTIVE, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.ICON_SIZE.setProp(view, Utils.dpToPx(context, 24.0f), PropModel.PropModifyAction.CLEAR_MODIFY);
        }

        if (view instanceof Utils.Tab) {
            PropModel.TEXT.setProp(view, context.getString(R.string.tab_new_text), PropModel.PropModifyAction.CLEAR_MODIFY);
        }

        if (view instanceof Utils.MenuItem) {
            PropModel.TEXT.setProp(view, context.getString(R.string.menu_item_new_text), PropModel.PropModifyAction.CLEAR_MODIFY);
        }

        if (view instanceof ViewGroup) {
            PropModel.LAYOUT_WIDTH.setProp(view, ViewGroup.LayoutParams.MATCH_PARENT, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.LAYOUT_HEIGHT.setProp(view, ViewGroup.LayoutParams.MATCH_PARENT, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.OUTLINE_CLIP_TO_PADDING.setProp(view, false, PropModel.PropModifyAction.CLEAR_MODIFY);
        }

        if (view.getClass().equals(View.class)) {
            PropModel.LAYOUT_WIDTH.setProp(view, Utils.dpToPx(context, 48.0f), PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.LAYOUT_HEIGHT.setProp(view, Utils.dpToPx(context, 48.0f), PropModel.PropModifyAction.CLEAR_MODIFY);
        }

        if (view instanceof Toolbar) {
            PropModel.LAYOUT_WIDTH.setProp(view, ViewGroup.LayoutParams.MATCH_PARENT, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.LAYOUT_HEIGHT.setProp(view, Utils.dpToPx(context, 56.0f), PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.ELEVATION.setProp(view, Utils.dpToPx(context, 4.0f), PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.BACKGROUND_COLOR.setProp(view, ThemeModelColor.P500, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.LAYOUT_MIN_WIDTH.setProp(view, Utils.dpToPx(context, 24.0f), PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.LAYOUT_MIN_HEIGHT.setProp(view, Utils.dpToPx(context, 56.0f), PropModel.PropModifyAction.CLEAR_MODIFY);

            PropModel.TOOLBAR_ACTION_TEXT_COLOR.setProp(view, ThemeModelColor.TEXT_PRIMARY, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.TOOLBAR_ACTION_ICON_COLOR.setProp(view, ThemeModelColor.ICON_ACTIVE, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.TOOLBAR_TITLE_TEXT_COLOR.setProp(view, ThemeModelColor.TEXT_PRIMARY, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.TOOLBAR_SUBTITLE_TEXT_COLOR.setProp(view, ThemeModelColor.TEXT_SECONDARY, PropModel.PropModifyAction.CLEAR_MODIFY);
        }

        if (view instanceof Button) {
            PropModel.LAYOUT_WIDTH.setProp(view, ViewGroup.LayoutParams.WRAP_CONTENT, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.LAYOUT_HEIGHT.setProp(view, Utils.dpToPx(context, 36.0f), PropModel.PropModifyAction.CLEAR_MODIFY);

            PropModel.PADDING_TOP.setProp(view, 0, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.PADDING_BOTTOM.setProp(view, 0, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.PADDING_START.setProp(view, Utils.dpToPx(context, 8.0f), PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.PADDING_END.setProp(view, Utils.dpToPx(context, 8.0f), PropModel.PropModifyAction.CLEAR_MODIFY);
        }

        if (view instanceof TabLayout) {
            PropModel.TAB_LAYOUT_INDICATOR_HEIGHT.setProp(view, Utils.dpToPx(context, 2.0f), PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.TAB_LAYOUT_INDICATOR_COLOR.setProp(view, ThemeModelColor.A400, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.TAB_LAYOUT_TAB_MODE.setProp(view, TabLayout.MODE_FIXED, PropModel.PropModifyAction.CLEAR_MODIFY);
            PropModel.TAB_LAYOUT_TEXT_COLORS.setProp(view, new ThemeModelColor[]{ThemeModelColor.TEXT_PRIMARY, ThemeModelColor.TEXT_SECONDARY}, PropModel.PropModifyAction.CLEAR_MODIFY);
        }
    }

    @Override
    public Object doNewViewInstance(Object parent) {
        return Utils.createView(context, viewClass, parent);
    }

    @Override
    public String getTitle() {
        return viewClass.getSimpleName();
    }

    @Override
    public WidgetModelCategory getCategory() {
        return ViewGroup.class.isAssignableFrom(viewClass) ? WidgetModelCategory.CONTAINERS : WidgetModelCategory.WIDGETS;
    }
}
