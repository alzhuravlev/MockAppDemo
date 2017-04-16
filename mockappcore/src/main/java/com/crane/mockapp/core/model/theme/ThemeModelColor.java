package com.crane.mockapp.core.model.theme;

/**
 * Created by crane2002 on 1/8/2017.
 */

public enum ThemeModelColor {
    P50,
    P100,
    P200,
    P300,
    P400,
    P500,
    P600,
    P700,
    P800,
    P900,

    A100,
    A200,
    A400,
    A700,

    TRANSPARENT,
    RIPPLE,

    TEXT_PRIMARY,
    TEXT_SECONDARY,
    TEXT_DISABLED,
    DIVIDER,
    ICON(false),
    ICON_ACTIVE,
    ICON_INACTIVE,

    TEXT_DARK_PRIMARY(false),
    TEXT_DARK_SECONDARY(false),
    TEXT_DARK_DISABLED(false),

    TEXT_LIGHT_PRIMARY(false),
    TEXT_LIGHT_SECONDARY(false),
    TEXT_LIGHT_DISABLED(false),

    DIVIDER_DARK(false),
    DIVIDER_LIGHT(false),

    ICON_LIGHT_ACTIVE(false),
    ICON_LIGHT_INACTIVE(false),
    ICON_DARK_ACTIVE(false),
    ICON_DARK_INACTIVE(false),

    Pink,
    LightPink,
    HotPink,
    DeepPink,
    PaleVioletRed,
    MediumVioletRed,

    LightSalmon,
    Salmon,
    DarkSalmon,
    LightCoral,
    IndianRed,
    Crimson,
    FireBrick,
    DarkRed,
    Red,

    OrangeRed,
    Tomato,
    Coral,
    DarkOrange,
    Orange,

    Yellow,
    LightYellow,
    LemonChiffon,
    LightGoldenrodYellow,
    PapayaWhip,
    Moccasin,
    PeachPuff,
    PaleGoldenrod,
    Khaki,
    DarkKhaki,
    Gold,

    Cornsilk,
    BlanchedAlmond,
    Bisque,
    NavajoWhite,
    Wheat,
    BurlyWood,
    Tan,
    RosyBrown,
    SandyBrown,
    Goldenrod,
    DarkGoldenrod,
    Peru,
    Chocolate,
    SaddleBrown,
    Sienna,
    Brown,
    Maroon,

    DarkOliveGreen,
    Olive,
    OliveDrab,
    YellowGreen,
    LimeGreen,
    Lime,
    LawnGreen,
    Chartreuse,
    GreenYellow,
    SpringGreen,
    MediumSpringGreen,
    LightGreen,
    PaleGreen,
    DarkSeaGreen,
    MediumAquamarine,
    MediumSeaGreen,
    SeaGreen,
    ForestGreen,
    Green,
    DarkGreen,

    Aqua,
    Cyan,
    LightCyan,
    PaleTurquoise,
    Aquamarine,
    Turquoise,
    MediumTurquoise,
    DarkTurquoise,
    LightSeaGreen,
    CadetBlue,
    DarkCyan,
    Teal,

    LightSteelBlue,
    PowderBlue,
    LightBlue,
    SkyBlue,
    LightSkyBlue,
    DeepSkyBlue,
    DodgerBlue,
    CornflowerBlue,
    SteelBlue,
    RoyalBlue,
    Blue,
    MediumBlue,
    DarkBlue,
    Navy,
    MidnightBlue,

    Lavender,
    Thistle,
    Plum,
    Violet,
    Orchid,
    Fuchsia,
    Magenta,
    MediumOrchid,
    MediumPurple,
    BlueViolet,
    DarkViolet,
    DarkOrchid,
    DarkMagenta,
    Purple,
    Indigo,
    DarkSlateBlue,
    SlateBlue,
    MediumSlateBlue,

    White,
    Snow,
    Honeydew,
    MintCream,
    Azure,
    AliceBlue,
    GhostWhite,
    WhiteSmoke,
    Seashell,
    Beige,
    OldLace,
    FloralWhite,
    Ivory,
    AntiqueWhite,
    Linen,
    LavenderBlush,
    MistyRose,

    Gainsboro,
    LightGray,
    Silver,
    DarkGray,
    Gray,
    DimGray,
    LightSlateGray,
    SlateGray,
    DarkSlateGray,
    Black;


    private boolean visible;

    ThemeModelColor() {
        this.visible = true;
    }

    ThemeModelColor(boolean visible) {
        this.visible = visible;
    }

    public static boolean isRealColor(ThemeModelColor themeColor) {
        switch (themeColor) {
            case TEXT_PRIMARY:
            case TEXT_SECONDARY:
            case TEXT_DISABLED:
            case ICON:
            case ICON_ACTIVE:
            case ICON_INACTIVE:
            case DIVIDER:
                return false;
        }
        return true;
    }

    public static ThemeModelColor getRealColor(ThemeModelColor themeColor, boolean light) {
        if (themeColor == null)
            return null;
        switch (themeColor) {
            case TEXT_PRIMARY:
                return light ? ThemeModelColor.TEXT_LIGHT_PRIMARY : ThemeModelColor.TEXT_DARK_PRIMARY;
            case TEXT_SECONDARY:
                return light ? ThemeModelColor.TEXT_LIGHT_SECONDARY : ThemeModelColor.TEXT_DARK_SECONDARY;
            case TEXT_DISABLED:
                return light ? ThemeModelColor.TEXT_LIGHT_DISABLED : ThemeModelColor.TEXT_DARK_DISABLED;
            case ICON:
            case ICON_ACTIVE:
                return light ? ThemeModelColor.ICON_LIGHT_ACTIVE : ThemeModelColor.ICON_DARK_ACTIVE;
            case ICON_INACTIVE:
                return light ? ThemeModelColor.ICON_LIGHT_INACTIVE : ThemeModelColor.ICON_DARK_INACTIVE;
            case DIVIDER:
                return light ? ThemeModelColor.DIVIDER_LIGHT : ThemeModelColor.DIVIDER_DARK;
        }
        return themeColor;
    }

    public boolean isVisible() {
        return visible;
    }
}
