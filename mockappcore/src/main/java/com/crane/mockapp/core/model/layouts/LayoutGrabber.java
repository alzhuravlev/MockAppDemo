package com.crane.mockapp.core.model.layouts;

import android.content.Context;

import com.crane.mockapp.core.Utils;
import com.crane.mockapp.core.model.props.PropModel;

/**
 * Created by crane2002 on 1/1/2017.
 */

public class LayoutGrabber {

    private static void processProps(final Context context, final Object view, final Layout layout) {
        PropModel.iterateProps(view, new PropModel.PropIterator() {
                    @Override
                    public void processProp(PropModel propModel, Object originalValue, Object modifiedValue) {
                        final boolean isInCustomLayout = Utils.hasCustomLayoutInParents(view);

                        if (isInCustomLayout && modifiedValue == NOT_SET)
                            return;

                        final Object value = modifiedValue == NOT_SET ? originalValue : modifiedValue;

                        switch (propModel) {

                            case IMAGE_DRAWABLE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.IMAGE_DRAWABLE, value);
                                break;

                            case IMAGE_SCALE_TYPE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.IMAGE_SCALE_TYPE, value);
                                break;

                            case ICON_CONTOUR_COLOR:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.ICON_CONTOUR_COLOR, value);
                                break;

                            case ICON_CONTOUR_SIZE:
                                if (value != null) {
                                    int sizeDp = Utils.pxToDp(context, (Integer) value);
                                    layout.addProperty(Layout.LayoutProperty.ICON_CONTOUR_SIZE, sizeDp);
                                }
                                break;

                            case ICON_COLOR:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.ICON_COLOR, value);
                                break;

                            case ICON_DRAWABLE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.ICON_DRAWABLE, String.valueOf(value));
                                break;

                            case NAV_ICON_DRAWABLE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.NAV_ICON_DRAWABLE, String.valueOf(value));
                                break;

                            case LOGO_ICON_DRAWABLE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.LOGO_ICON_DRAWABLE, String.valueOf(value));
                                break;

                            case ACTION_ICON_DRAWABLE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.ACTION_ICON_DRAWABLE, String.valueOf(value));
                                break;

                            case TAB_ICON_DRAWABLE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TAB_ICON_DRAWABLE, String.valueOf(value));
                                break;

                            case ICON_SIZE:
                                if (value != null) {
                                    int sizeDp = Utils.pxToDp(context, (Integer) value);
                                    layout.addProperty(Layout.LayoutProperty.ICON_SIZE, sizeDp);
                                }
                                break;

                            case LAYOUT_GRAVITY:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.LAYOUT_GRAVITY, value);
                                break;

                            case LAYOUT_WIDTH:
                                if (value != null) {
                                    int width = Utils.pxToDpLayoutSize(context, (Integer) value);
                                    layout.addProperty(Layout.LayoutProperty.LAYOUT_WIDTH, width);
                                }
                                break;

                            case LAYOUT_HEIGHT:
                                if (value != null) {
                                    int height = Utils.pxToDpLayoutSize(context, (Integer) value);
                                    layout.addProperty(Layout.LayoutProperty.LAYOUT_HEIGHT, height);
                                }
                                break;

                            case LAYOUT_MIN_WIDTH:
                                if (value != null) {
                                    int width = Utils.pxToDp(context, (Integer) value);
                                    layout.addProperty(Layout.LayoutProperty.LAYOUT_MIN_WIDTH, width);
                                }
                                break;

                            case LAYOUT_MIN_HEIGHT:
                                if (value != null) {
                                    int width = Utils.pxToDp(context, (Integer) value);
                                    layout.addProperty(Layout.LayoutProperty.LAYOUT_MIN_HEIGHT, width);
                                }
                                break;

                            case LAYOUT_WEIGHT:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.LAYOUT_WEIGHT, value);
                                break;

                            case LAYOUT_ORIENTATION:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.LAYOUT_ORIENTATION, value);
                                break;

                            case LAYOUT_MARGIN_START:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.LAYOUT_MARGIN_START, Utils.pxToDp(context, (Integer) value));
                                break;

                            case LAYOUT_MARGIN_TOP:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.LAYOUT_MARGIN_TOP, Utils.pxToDp(context, (Integer) value));
                                break;

                            case LAYOUT_MARGIN_END:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.LAYOUT_MARGIN_END, Utils.pxToDp(context, (Integer) value));
                                break;

                            case LAYOUT_MARGIN_BOTTOM:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.LAYOUT_MARGIN_BOTTOM, Utils.pxToDp(context, (Integer) value));
                                break;

                            case PADDING_START:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.PADDING_START, Utils.pxToDp(context, (Integer) value));
                                break;

                            case PADDING_TOP:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.PADDING_TOP, Utils.pxToDp(context, (Integer) value));
                                break;

                            case PADDING_END:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.PADDING_END, Utils.pxToDp(context, (Integer) value));
                                break;

                            case PADDING_BOTTOM:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.PADDING_BOTTOM, Utils.pxToDp(context, (Integer) value));
                                break;

                            case RECYCLER_VIEW_ITEM_COUNT:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.RECYCLER_VIEW_ITEM_COUNT, value);
                                break;

                            case RECYCLER_VIEW_ITEM_LAYOUT:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.RECYCLER_VIEW_ITEM_LAYOUT, value);
                                break;

                            case RECYCLER_VIEW_LAYOUT_ORIENTATION:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.RECYCLER_VIEW_LAYOUT_ORIENTATION, value);
                                break;

                            case RECYCLER_VIEW_LAYOUT_SPANS:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.RECYCLER_VIEW_LAYOUT_SPANS, value);
                                break;

                            case RECYCLER_VIEW_ITEM_PADDING:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.RECYCLER_VIEW_ITEM_PADDING, value);
                                break;

                            case TOOLBAR_TITLE_TEXT:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TOOLBAR_TITLE_TEXT, value);
                                break;

                            case TOOLBAR_SUBTITLE_TEXT:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TOOLBAR_SUBTITLE_TEXT, value);
                                break;

                            case TEXT:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TEXT, value);
                                break;

                            case TEXT_COLOR:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TEXT_COLOR, value);
                                break;

                            case TEXT_GRAVITY:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TEXT_GRAVITY, value);
                                break;

                            case TEXT_SIZE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TEXT_SIZE, Utils.pxToDp(context, (Integer) value));
                                break;

                            case TEXT_STYLE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TEXT_STYLE, value);
                                break;

                            case TEXT_MIN_LINES:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TEXT_MIN_LINES, value);
                                break;

                            case TEXT_MAX_LINES:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TEXT_MAX_LINES, value);
                                break;

                            case ON_CLICK:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.ON_CLICK, value);
                                break;

                            case ON_LONG_CLICK:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.ON_LONG_CLICK, value);
                                break;

                            case VISIBILITY:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.VISIBILITY, value);
                                break;

                            case ELEVATION:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.ELEVATION, value);
                                break;

                            case OUTLINE_ENABLED:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.OUTLINE_ENABLED, value);
                                break;

                            case OUTLINE_CLIP_TO_OUTLINE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.OUTLINE_CLIP_TO_OUTLINE, value);
                                break;

                            case OUTLINE_CLIP_TO_PADDING:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.OUTLINE_CLIP_TO_PADDING, value);
                                break;

                            case OUTLINE_OFFSET:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.OUTLINE_OFFSET, Utils.pxToDp(context, (Integer) value));
                                break;

                            case OUTLINE_RADIUS:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.OUTLINE_RADIUS, Utils.pxToDp(context, (Integer) value));
                                break;

                            case LAYOUT_ANIMATION:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.LAYOUT_ANIMATION, value);
                                break;

                            case BACKGROUND_COLOR:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.BACKGROUND_COLOR, value);
                                break;

                            case BACKGROUND_ALPHA:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.BACKGROUND_ALPHA, value);
                                break;

                            case BACKGROUND_IMAGE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.BACKGROUND_IMAGE, value);
                                break;

                            case BACKGROUND_IMAGE_TILE_MODE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.BACKGROUND_IMAGE_TILE_MODE, value);
                                break;

                            case TAB_LAYOUT_TAB_MODE:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TAB_LAYOUT_TAB_MODE, value);
                                break;

                            case TAB_LAYOUT_INDICATOR_COLOR:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TAB_LAYOUT_INDICATOR_COLOR, value);
                                break;

                            case TAB_LAYOUT_INDICATOR_HEIGHT:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TAB_LAYOUT_INDICATOR_HEIGHT, Utils.pxToDp(context, (Integer) value));
                                break;

                            case TAB_LAYOUT_TEXT_COLORS:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TAB_LAYOUT_TEXT_COLORS, value);
                                break;

                            case TAB_LAYOUT_VIEW_PAGER_REF:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TAB_LAYOUT_VIEW_PAGER_REF, value);
                                break;

                            case TAB_LAYOUT_DEFAULT_TAB_INDEX:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TAB_LAYOUT_DEFAULT_TAB_INDEX, value);
                                break;

                            case TOOLBAR_ACTION_TEXT_COLOR:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TOOLBAR_ACTION_TEXT_COLOR, value);
                                break;

                            case TOOLBAR_ACTION_ICON_COLOR:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TOOLBAR_ACTION_ICON_COLOR, value);
                                break;

                            case TOOLBAR_TITLE_TEXT_COLOR:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TOOLBAR_TITLE_TEXT_COLOR, value);
                                break;

                            case TOOLBAR_SUBTITLE_TEXT_COLOR:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TOOLBAR_SUBTITLE_TEXT_COLOR, value);
                                break;

                            case TOOLBAR_MENU_ITEM_SHOW_AS_ACTION:
                                if (value != null)
                                    layout.addProperty(Layout.LayoutProperty.TOOLBAR_MENU_ITEM_SHOW_AS_ACTION, value);
                                break;
                        }
                    }
                }
        );
    }

    private static Layout createLayoutForView(Context context, Object view) {

        PropLayoutValue layoutValue = PropModel.CUSTOM_LAYOUT.getPropAs(view, PropLayoutValue.class);

        Layout layout;
        if (layoutValue != null && layoutValue.getProjectId() != null && layoutValue.getLayoutId() != null)
            layout = new Layout(layoutValue.getProjectId(), layoutValue.getLayoutId());
        else
            layout = new Layout(view.getClass().getName());

        String viewId = PropModel.VIEW_ID.getPropAsString(view);
        if (viewId != null)
            layout.addProperty(Layout.LayoutProperty.VIEW_ID, viewId);

        processProps(context, view, layout);

        return layout;
    }

    private static Layout doGrab(final Context context, Object view) {
        final Layout layout = createLayoutForView(context, view);

        Utils.iterateChildren(view, new Utils.ViewChildrenIterator() {
            @Override
            public Object processChild(Object parent, Object child, int position) {
                Layout childLayout = doGrab(context, child);
                layout.addChild(childLayout);
                return null;
            }
        });

        return layout;
    }

    public static Layout grab(Context context, Object view) {
        return doGrab(context, view);
    }
}
