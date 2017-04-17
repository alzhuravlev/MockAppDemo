package com.crane.mockapp.core.model.layouts;

import android.content.Context;
import android.graphics.Shader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crane.mockapp.core.R;
import com.crane.mockapp.core.Utils;
import com.crane.mockapp.core.model.props.PropModel;
import com.crane.mockapp.core.model.theme.ThemeModelColor;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by crane2002 on 1/1/2017.
 */

public class LayoutInflater {

    private static void assignProperties(final Context context, final Object view, final Layout layoutRoot, final Layout layout, final Deque<String> customLayouts) {

        final PropModel.PropModifyAction propModifyAction = layoutRoot == layout.getRoot() && Utils.hasCustomLayoutInParents(view) ? PropModel.PropModifyAction.FORCE_MODIFY : PropModel.PropModifyAction.CLEAR_MODIFY;

        layout.iterateProperties(new Layout.PropertyIterator() {
            @Override
            public void operate(Layout.LayoutProperty property) {

                switch (property) {

                    case VIEW_ID:
                        PropModel.VIEW_ID.setProp(view, layout.getViewId());
                        break;

                    case IMAGE_DRAWABLE:
                        PropModel.IMAGE_DRAWABLE.setProp(view, layout.getPropertyAsString(property), propModifyAction);
                        break;

                    case IMAGE_SCALE_TYPE:
                        PropModel.IMAGE_SCALE_TYPE.setProp(view, layout.getPropertyAs(ImageView.ScaleType.class, property), propModifyAction);
                        break;

                    case ICON_DRAWABLE:
                        PropModel.ICON_DRAWABLE.setProp(view, layout.getPropertyAsString(property), propModifyAction);
                        break;

                    case NAV_ICON_DRAWABLE:
                        PropModel.NAV_ICON_DRAWABLE.setProp(view, layout.getPropertyAsString(property), propModifyAction);
                        break;

                    case LOGO_ICON_DRAWABLE:
                        PropModel.LOGO_ICON_DRAWABLE.setProp(view, layout.getPropertyAsString(property), propModifyAction);
                        break;

                    case ACTION_ICON_DRAWABLE:
                        PropModel.ACTION_ICON_DRAWABLE.setProp(view, layout.getPropertyAsString(property), propModifyAction);
                        break;

                    case TAB_ICON_DRAWABLE:
                        PropModel.TAB_ICON_DRAWABLE.setProp(view, layout.getPropertyAsString(property), propModifyAction);
                        break;

                    case ICON_COLOR:
                        PropModel.ICON_COLOR.setProp(view, layout.getPropertyAs(ThemeModelColor.class, property), propModifyAction);
                        break;

                    case ICON_CONTOUR_COLOR:
                        PropModel.ICON_CONTOUR_COLOR.setProp(view, layout.getPropertyAs(ThemeModelColor.class, property), propModifyAction);
                        break;

                    case ICON_SIZE:
                        PropModel.ICON_SIZE.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case ICON_CONTOUR_SIZE:
                        PropModel.ICON_CONTOUR_SIZE.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case LAYOUT_GRAVITY:
                        PropModel.LAYOUT_GRAVITY.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case LAYOUT_WIDTH:
                        PropModel.LAYOUT_WIDTH.setProp(view, Utils.dpToPxLayoutSize(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case LAYOUT_HEIGHT:
                        PropModel.LAYOUT_HEIGHT.setProp(view, Utils.dpToPxLayoutSize(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case LAYOUT_MIN_WIDTH:
                        PropModel.LAYOUT_MIN_WIDTH.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case LAYOUT_MIN_HEIGHT:
                        PropModel.LAYOUT_MIN_HEIGHT.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case LAYOUT_WEIGHT:
                        PropModel.LAYOUT_WEIGHT.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case LAYOUT_ORIENTATION:
                        PropModel.LAYOUT_ORIENTATION.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case TOOLBAR_TITLE_TEXT:
                        PropModel.TOOLBAR_TITLE_TEXT.setProp(view, layout.getPropertyAsString(property), propModifyAction);
                        break;

                    case TOOLBAR_SUBTITLE_TEXT:
                        PropModel.TOOLBAR_SUBTITLE_TEXT.setProp(view, layout.getPropertyAsString(property), propModifyAction);
                        break;

                    case TEXT:
                        PropModel.TEXT.setProp(view, layout.getPropertyAsString(property), propModifyAction);
                        break;

                    case TEXT_COLOR:
                        PropModel.TEXT_COLOR.setProp(view, layout.getPropertyAs(ThemeModelColor.class, property), propModifyAction);
                        break;

                    case TEXT_GRAVITY:
                        PropModel.TEXT_GRAVITY.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case TEXT_SIZE:
                        PropModel.TEXT_SIZE.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case TEXT_STYLE:
                        PropModel.TEXT_STYLE.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case TEXT_MIN_LINES:
                        PropModel.TEXT_MIN_LINES.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case TEXT_MAX_LINES:
                        PropModel.TEXT_MAX_LINES.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case PADDING_START:
                        PropModel.PADDING_START.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case PADDING_TOP:
                        PropModel.PADDING_TOP.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case PADDING_END:
                        PropModel.PADDING_END.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case PADDING_BOTTOM:
                        PropModel.PADDING_BOTTOM.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case LAYOUT_MARGIN_START:
                        PropModel.LAYOUT_MARGIN_START.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case LAYOUT_MARGIN_TOP:
                        PropModel.LAYOUT_MARGIN_TOP.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case LAYOUT_MARGIN_END:
                        PropModel.LAYOUT_MARGIN_END.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case LAYOUT_MARGIN_BOTTOM:
                        PropModel.LAYOUT_MARGIN_BOTTOM.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case RECYCLER_VIEW_ITEM_COUNT:
                        PropModel.RECYCLER_VIEW_ITEM_COUNT.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case RECYCLER_VIEW_ITEM_LAYOUT: {
                        PropLayoutValue value = layout.getPropertyAs(PropLayoutValue.class, property);
                        PropModel.RECYCLER_VIEW_ITEM_LAYOUT.setProp(view, value, propModifyAction);
                    }
                    break;

                    case RECYCLER_VIEW_LAYOUT_ORIENTATION:
                        PropModel.RECYCLER_VIEW_LAYOUT_ORIENTATION.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case RECYCLER_VIEW_LAYOUT_SPANS:
                        PropModel.RECYCLER_VIEW_LAYOUT_SPANS.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case RECYCLER_VIEW_ITEM_PADDING:
                        PropModel.RECYCLER_VIEW_ITEM_PADDING.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case ON_CLICK: {
                        PropEventValue value = layout.getPropertyAs(PropEventValue.class, property);
                        PropModel.ON_CLICK.setProp(view, value, propModifyAction);
                    }
                    break;

                    case ON_LONG_CLICK: {
                        PropEventValue value = layout.getPropertyAs(PropEventValue.class, property);
                        PropModel.ON_LONG_CLICK.setProp(view, value, propModifyAction);
                    }
                    break;


                    case VISIBILITY:
                        PropModel.VISIBILITY.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case ELEVATION:
                        PropModel.ELEVATION.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case OUTLINE_ENABLED:
                        PropModel.OUTLINE_ENABLED.setProp(view, layout.getPropertyAsBoolean(property), propModifyAction);
                        break;

                    case OUTLINE_CLIP_TO_OUTLINE:
                        PropModel.OUTLINE_CLIP_TO_OUTLINE.setProp(view, layout.getPropertyAsBoolean(property), propModifyAction);
                        break;

                    case OUTLINE_CLIP_TO_PADDING:
                        PropModel.OUTLINE_CLIP_TO_PADDING.setProp(view, layout.getPropertyAsBoolean(property), propModifyAction);
                        break;

                    case OUTLINE_OFFSET:
                        PropModel.OUTLINE_OFFSET.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case OUTLINE_RADIUS:
                        PropModel.OUTLINE_RADIUS.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case LAYOUT_ANIMATION:
                        PropModel.LAYOUT_ANIMATION.setProp(view, layout.getPropertyAsBoolean(property), propModifyAction);
                        break;

                    case BACKGROUND_COLOR:
                        PropModel.BACKGROUND_COLOR.setProp(view, layout.getPropertyAs(ThemeModelColor.class, property), propModifyAction);
                        break;

                    case BACKGROUND_ALPHA:
                        PropModel.BACKGROUND_ALPHA.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case BACKGROUND_IMAGE:
                        PropModel.BACKGROUND_IMAGE.setProp(view, layout.getPropertyAsString(property), propModifyAction);
                        break;

                    case BACKGROUND_IMAGE_TILE_MODE:
                        PropModel.BACKGROUND_IMAGE_TILE_MODE.setProp(view, layout.getPropertyAs(Shader.TileMode.class, property), propModifyAction);
                        break;

                    case TAB_LAYOUT_TAB_MODE:
                        PropModel.TAB_LAYOUT_TAB_MODE.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case TAB_LAYOUT_INDICATOR_COLOR:
                        PropModel.TAB_LAYOUT_INDICATOR_COLOR.setProp(view, layout.getPropertyAs(ThemeModelColor.class, property), propModifyAction);
                        break;

                    case TAB_LAYOUT_INDICATOR_HEIGHT:
                        PropModel.TAB_LAYOUT_INDICATOR_HEIGHT.setProp(view, Utils.dpToPx(context, layout.getPropertyInt(property)), propModifyAction);
                        break;

                    case TAB_LAYOUT_TEXT_COLORS:
                        PropModel.TAB_LAYOUT_TEXT_COLORS.setProp(view, layout.getPropertyAs(ThemeModelColor[].class, property), propModifyAction);
                        break;

                    case TAB_LAYOUT_VIEW_PAGER_REF:
                        PropModel.TAB_LAYOUT_VIEW_PAGER_REF.setProp(view, layout.getPropertyAsString(property), propModifyAction);
                        break;

                    case TAB_LAYOUT_DEFAULT_TAB_INDEX:
                        PropModel.TAB_LAYOUT_DEFAULT_TAB_INDEX.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;

                    case TOOLBAR_ACTION_TEXT_COLOR:
                        PropModel.TOOLBAR_ACTION_TEXT_COLOR.setProp(view, layout.getPropertyAs(ThemeModelColor.class, property), propModifyAction);
                        break;

                    case TOOLBAR_ACTION_ICON_COLOR:
                        PropModel.TOOLBAR_ACTION_ICON_COLOR.setProp(view, layout.getPropertyAs(ThemeModelColor.class, property), propModifyAction);
                        break;

                    case TOOLBAR_TITLE_TEXT_COLOR:
                        PropModel.TOOLBAR_TITLE_TEXT_COLOR.setProp(view, layout.getPropertyAs(ThemeModelColor.class, property), propModifyAction);
                        break;

                    case TOOLBAR_SUBTITLE_TEXT_COLOR:
                        PropModel.TOOLBAR_SUBTITLE_TEXT_COLOR.setProp(view, layout.getPropertyAs(ThemeModelColor.class, property), propModifyAction);
                        break;

                    case TOOLBAR_MENU_ITEM_SHOW_AS_ACTION:
                        PropModel.TOOLBAR_MENU_ITEM_SHOW_AS_ACTION.setProp(view, layout.getPropertyInt(property), propModifyAction);
                        break;
                }
            }
        });
    }

    private static Object createView(Context context, Layout layoutRoot, Layout layout, Object root,
                                     Object parent, boolean attachToParent, Deque<String> customLayouts) {

        if (parent != null && parent != root && Utils.hasCustomLayoutInParents(parent))
            return Utils.findViewWithViewIdTag(parent, layout.getViewId());

        PropLayoutValue layoutValue = layout.getCustomLayout();
        String viewClassName = layout.getViewClassName();

        if (layoutValue != null)
            return createViewFromCustomLayout(context, layoutRoot, layoutValue, root, parent, attachToParent, customLayouts);
        else if (viewClassName != null)
            return createViewFromViewClassName(context, viewClassName, parent);

        return null;
    }


    private static Object createViewFromCustomLayout(Context context, Layout layoutRoot, PropLayoutValue layoutValue,
                                                     Object root, Object parent, boolean attachToParent,
                                                     Deque<String> customLayouts) {

        final String layoutKey = layoutValue.getProjectId() + "/" + layoutValue.getLayoutId();
        if (customLayouts.contains(layoutKey))
            return null;

        LayoutDescriptor layoutDescriptor = ProjectServiceFactory.getInstance(context).loadLayout(layoutValue.getProjectId(), layoutValue.getLayoutId());
        if (layoutDescriptor == null || layoutDescriptor.getLayout() == null && parent instanceof ViewGroup) {
            android.view.LayoutInflater layoutInflater = android.view.LayoutInflater.from(context);
            TextView textView = (TextView) layoutInflater.inflate(R.layout.error_custom_layout_not_found, (ViewGroup) parent, false);
            textView.setText(context.getString(R.string.error_custom_layout_not_found));
            PropModel.CUSTOM_LAYOUT.setProp(textView, layoutValue.clone());
            return textView;
        } else {
            customLayouts.push(layoutKey);
            Object view = inflateInternal(context, layoutRoot, layoutDescriptor.getLayout(), root, parent, attachToParent, customLayouts);
            PropModel.CUSTOM_LAYOUT.setProp(view, layoutValue.clone());
            customLayouts.pop();

            return view;
        }
    }

    private static Object createViewFromViewClassName(Context context, String viewClassName, Object parent) {

        Class<? extends View> viewClass;
        try {
            viewClass = (Class<? extends View>) Class.forName(viewClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        Object view = Utils.createView(context, viewClass, parent);
        return view;
    }

    private static Object inflateInternal(Context context, Layout layoutRoot, Layout layout, Object root, Object parent, boolean attachToParent, Deque<String> customLayouts) {
        Object view = createView(context, layoutRoot, layout, root, parent, attachToParent, customLayouts);

        if (view != null && view instanceof View && parent != null && Utils.getLayoutParams(view) == null) {
            try {
                Method method = ViewGroup.class.getDeclaredMethod("generateDefaultLayoutParams");
                method.setAccessible(true);
                ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) method.invoke(parent);
                Utils.setLayoutParams(view, layoutParams);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (Utils.isViewGroup(view) && layout.getChildren() != null) {
            for (Layout childLayout : layout.getChildren())
                inflateInternal(context, layoutRoot, childLayout, root, view, true, customLayouts);
        }

        if (attachToParent && parent != null && view != null && Utils.getParent(view) == null)
            view = Utils.addViewToParent(parent, view);

        if (view != null)
            assignProperties(context, view, layoutRoot, layout, customLayouts);

        return view;
    }

    public static Object inflate(Context context, Layout layout, Object parent, boolean attachToParent) {
        Object view = inflateInternal(context, layout, layout, parent, parent, false, new ArrayDeque<String>());
        if (attachToParent && parent != null && view != null && Utils.getParent(view) == null)
            Utils.addViewToParent(parent, view);
        return view;
    }
}
