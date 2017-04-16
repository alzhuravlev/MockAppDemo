package com.crane.mockapp.core.model.layouts;

import com.crane.mockapp.core.JsonUtils;
import com.crane.mockapp.core.model.theme.ThemeModelColor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by crane2002 on 2/26/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class LayoutDescriptor {
    @JsonProperty("layout")
    private Layout layout;

    @JsonProperty("primaryThemeId")
    private int primaryThemeId = 108;

    @JsonProperty("accentThemeId")
    private int accentThemeId = 103;

    @JsonProperty("lastViewIndex")
    private int lastViewIndex;

    @JsonProperty("fullScreen")
    private boolean fullScreen = true;

    @JsonProperty("navDrawerLayout")
    private PropLayoutValue navDrawerLayout;

    @JsonProperty("statusBarColor")
    private ThemeModelColor statusBarColor = ThemeModelColor.P700;

    @JsonCreator
    LayoutDescriptor(@JsonProperty("layout") Layout layout,
                     @JsonProperty("primaryThemeId") int primaryThemeId,
                     @JsonProperty("accentThemeId") int accentThemeId,
                     @JsonProperty("lastViewIndex") int lastViewIndex,
                     @JsonProperty("fullScreen") Boolean fullScreen,
                     @JsonProperty("navDrawerLayout") PropLayoutValue navDrawerLayout,
                     @JsonProperty("statusBarColor") ThemeModelColor statusBarColor
    ) {
        this.layout = layout;
        this.primaryThemeId = primaryThemeId;
        this.accentThemeId = accentThemeId;
        this.lastViewIndex = lastViewIndex;
        this.fullScreen = fullScreen != null ? fullScreen : false;
        this.navDrawerLayout = navDrawerLayout;
        this.statusBarColor = statusBarColor;
    }

    public LayoutDescriptor() {
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public int getPrimaryThemeId() {
        return primaryThemeId;
    }

    public void setPrimaryThemeId(int primaryThemeId) {
        this.primaryThemeId = primaryThemeId;
    }

    public int getAccentThemeId() {
        return accentThemeId;
    }

    public void setAccentThemeId(int accentThemeId) {
        this.accentThemeId = accentThemeId;
    }

    public int getLastViewIndex() {
        return lastViewIndex;
    }

    public void setLastViewIndex(int lastViewIndex) {
        this.lastViewIndex = lastViewIndex;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public PropLayoutValue getNavDrawerLayout() {
        return navDrawerLayout;
    }

    public void setNavDrawerLayout(PropLayoutValue navDrawerLayout) {
        this.navDrawerLayout = navDrawerLayout;
    }

    public ThemeModelColor getStatusBarColor() {
        return statusBarColor;
    }

    public void setStatusBarColor(ThemeModelColor statusBarColor) {
        this.statusBarColor = statusBarColor;
    }

    public static String asString(LayoutDescriptor layoutDescriptor) {
        if (layoutDescriptor == null)
            return null;
        return JsonUtils.toString(layoutDescriptor, false);
    }

    public static LayoutDescriptor fromString(String s) {
        if (s == null)
            return null;
        return JsonUtils.fromString(s, LayoutDescriptor.class);
    }

}
