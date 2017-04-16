package com.crane.mockapp.core.model.layouts;

import com.crane.mockapp.core.JsonUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static com.crane.mockapp.core.model.layouts.Layout.LayoutProperty.CUSTOM_LAYOUT_LAYOUT;
import static com.crane.mockapp.core.model.layouts.Layout.LayoutProperty.VIEW_CLASS_NAME;
import static com.crane.mockapp.core.model.layouts.Layout.LayoutProperty.VIEW_ID;

/**
 * Created by crane2002 on 1/1/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Layout {

    public enum LayoutProperty {
        VIEW_CLASS_NAME,
        CUSTOM_LAYOUT_LAYOUT,

        LAYOUT_ORIENTATION,

        NAV_ICON_DRAWABLE,
        LOGO_ICON_DRAWABLE,
        ACTION_ICON_DRAWABLE,
        TAB_ICON_DRAWABLE,

        ICON_DRAWABLE,
        ICON_COLOR,
        ICON_SIZE,
        ICON_CONTOUR_COLOR,
        ICON_CONTOUR_SIZE,

        IMAGE_DRAWABLE,
        IMAGE_SCALE_TYPE,

        LAYOUT_ANIMATION,

        LAYOUT_WIDTH,
        LAYOUT_HEIGHT,
        LAYOUT_MIN_WIDTH,
        LAYOUT_MIN_HEIGHT,
        LAYOUT_WEIGHT,
        LAYOUT_GRAVITY,

        LAYOUT_MARGIN_START,
        LAYOUT_MARGIN_TOP,
        LAYOUT_MARGIN_END,
        LAYOUT_MARGIN_BOTTOM,

        PADDING_START,
        PADDING_TOP,
        PADDING_END,
        PADDING_BOTTOM,

        TOOLBAR_TITLE_TEXT,
        TOOLBAR_SUBTITLE_TEXT,
        TEXT,
        TEXT_COLOR,
        TEXT_SIZE,
        TEXT_STYLE,
        TEXT_GRAVITY,
        TEXT_MIN_LINES,
        TEXT_MAX_LINES,

        ON_CLICK,
        ON_LONG_CLICK,

        VISIBILITY,

        ELEVATION,

        OUTLINE_ENABLED,
        OUTLINE_CLIP_TO_OUTLINE,
        OUTLINE_RADIUS,
        OUTLINE_OFFSET,
        OUTLINE_CLIP_TO_PADDING,

        BACKGROUND_COLOR,
        BACKGROUND_ALPHA,
        BACKGROUND_IMAGE,
        BACKGROUND_IMAGE_TILE_MODE,

        ALPHA,

        RECYCLER_VIEW_ITEM_LAYOUT,
        RECYCLER_VIEW_ITEM_COUNT,
        RECYCLER_VIEW_LAYOUT_ORIENTATION,
        RECYCLER_VIEW_LAYOUT_SPANS,
        RECYCLER_VIEW_ITEM_PADDING,

        TAB_LAYOUT_TAB_MODE,
        TAB_LAYOUT_INDICATOR_COLOR,
        TAB_LAYOUT_INDICATOR_HEIGHT,
        TAB_LAYOUT_TEXT_COLORS,
        TAB_LAYOUT_VIEW_PAGER_REF,
        TAB_LAYOUT_DEFAULT_TAB_INDEX,

        TOOLBAR_ACTION_TEXT_COLOR,
        TOOLBAR_ACTION_ICON_COLOR,
        TOOLBAR_TITLE_TEXT_COLOR,
        TOOLBAR_SUBTITLE_TEXT_COLOR,
        TOOLBAR_MENU_ITEM_SHOW_AS_ACTION,

        // must be the last prop while iterate
        VIEW_ID,
    }


    public interface PropertyIterator {
        void operate(LayoutProperty property);
    }

    @JsonIgnore
    private Layout parent;

    @JsonProperty("children")
    private List<Layout> children;

    @JsonProperty("properties")
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    private Map<LayoutProperty, Object> properties;

    @JsonCreator
    Layout(@JsonProperty("children") List<Layout> children, @JsonProperty("properties") Map<LayoutProperty, Object> properties) {
        this.children = children;
        this.properties = properties;

        if (children != null && children.size() > 0)
            for (Layout child : children)
                child.parent = this;
    }

    Layout() {
    }

    Layout(String viewClassName) {
        addProperty(VIEW_CLASS_NAME, viewClassName);
    }

    Layout(String folder, String layoutId) {
        addProperty(CUSTOM_LAYOUT_LAYOUT, new PropLayoutValue(folder, layoutId));
    }

    Layout getRoot() {
        return parent != null ? parent.getRoot() : this;
    }

    Layout getParent() {
        return parent;
    }

    String getViewId() {
        return getPropertyAsString(VIEW_ID);
    }

    String getViewClassName() {
        return getPropertyAsString(VIEW_CLASS_NAME);
    }

    PropLayoutValue getCustomLayout() {
        return getPropertyAs(PropLayoutValue.class, CUSTOM_LAYOUT_LAYOUT);
    }

    List<Layout> getChildren() {
        return children;
    }

    void addChild(Layout layout) {
        if (layout == null)
            return;
        if (children == null)
            children = new ArrayList<>();
        layout.parent = this;
        children.add(layout);
    }

    void addProperty(LayoutProperty key, Object value) {
        if (properties == null)
            properties = new HashMap<>();
        properties.put(key, value);
    }

    void iterateProperties(PropertyIterator propertyIterator) {
        Set<LayoutProperty> set = new TreeSet<>(properties.keySet());
        if (propertyIterator != null && properties != null)
            for (LayoutProperty layoutProperty : set)
                propertyIterator.operate(layoutProperty);
    }

    boolean hasProperty(LayoutProperty key) {
        return properties != null && properties.containsKey(key);
    }

    private Object getProperty(LayoutProperty key) {
        if (hasProperty(key))
            return properties.get(key);
        return null;
    }

    Boolean getPropertyAsBoolean(LayoutProperty key) {
        Boolean value = getPropertyAs(Boolean.class, key);
        if (value != null)
            return value;
        return null;
    }

    String getPropertyAsString(LayoutProperty key) {
        return getPropertyAs(String.class, key);
    }

    int getPropertyInt(LayoutProperty key) {
        Integer value = getPropertyAs(Integer.class, key);
        if (value != null)
            return value;
        return 0;
    }

    <T> T getPropertyAs(Class<T> aClass, LayoutProperty key) {
        Object value = getProperty(key);
        if (value != null && aClass.isAssignableFrom(value.getClass()))
            return (T) value;
        return null;
    }

    public static String asString(Layout layout) {
        if (layout == null)
            return null;
        return JsonUtils.toString(layout, false);
    }

    public static Layout fromString(String s) {
        if (s == null)
            return null;
        return JsonUtils.fromString(s, Layout.class);
    }
}
