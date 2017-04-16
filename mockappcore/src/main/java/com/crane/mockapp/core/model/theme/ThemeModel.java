package com.crane.mockapp.core.model.theme;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crane2002 on 1/8/2017.
 */

public class ThemeModel {

    private static final int FALLBACK_COLOR = 0x00000000;

    private Map<ThemeModelColor, Integer> colorMap;
    private Map<ThemeModelColor, ThemeModelBrightness> foregroundBrightnessMap;
    private int id;
    private int titleResId;

    ThemeModel(int id, int titleResId) {
        this.id = id;
        this.titleResId = titleResId;
    }

    public ThemeModel(ThemeModel... themeModels) {
        if (themeModels != null)
            for (ThemeModel themeModel : themeModels)
                addTheme(themeModel);
    }

    public int getColor(ThemeModelColor themeColor) {
        if (colorMap == null)
            return FALLBACK_COLOR;
        Integer color = colorMap.get(themeColor);
        return color == null ? FALLBACK_COLOR : color;
    }

    public boolean isForegroundLight(ThemeModelColor themeColor) {
        return getForegroundBrightness(themeColor) == ThemeModelBrightness.LIGHT;
    }

    public ThemeModelBrightness getForegroundBrightness(ThemeModelColor themeColor) {
        if (foregroundBrightnessMap != null)
            return foregroundBrightnessMap.get(themeColor);
        return null;
    }

    void addColor(ThemeModelColor themeModelColor, int color) {
        if (colorMap == null)
            colorMap = new HashMap<>();
        colorMap.put(themeModelColor, color);
    }

    void addColor(ThemeModelColor themeModelColor, int color, ThemeModelBrightness foregroundBrightness) {
        addColor(themeModelColor, color);
        if (foregroundBrightnessMap == null)
            foregroundBrightnessMap = new HashMap<>();
        foregroundBrightnessMap.put(themeModelColor, foregroundBrightness);

    }

    void addTheme(ThemeModel themeModel) {
        if (themeModel == null)
            return;

        if (themeModel.colorMap != null) {
            if (colorMap == null)
                colorMap = new HashMap<>();
            this.colorMap.putAll(themeModel.colorMap);
        }

        if (themeModel.foregroundBrightnessMap != null) {
            if (foregroundBrightnessMap == null)
                foregroundBrightnessMap = new HashMap<>();
            this.foregroundBrightnessMap.putAll(themeModel.foregroundBrightnessMap);
        }
    }

    public int getTitleResId() {
        return titleResId;
    }

    public int getId() {
        return id;
    }

}
