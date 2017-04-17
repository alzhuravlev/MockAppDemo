package com.crane.mockapp.core;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.StateSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crane.mockapp.core.model.layouts.LayoutDescriptor;
import com.crane.mockapp.core.model.layouts.ProjectServiceFactory;
import com.crane.mockapp.core.model.layouts.PropEventValue;
import com.crane.mockapp.core.model.layouts.PropLayoutValue;
import com.crane.mockapp.core.model.props.PropModel;
import com.crane.mockapp.core.model.theme.ThemeModel;
import com.crane.mockapp.core.model.theme.ThemeModelColor;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by crane2002 on 1/8/2017.
 */

public class ThemeUtils {

    private static int resolveColorObject(Object view, Object colorObject, ThemeModel themeModel) {
        int color;
        if (colorObject == null)
            color = resolveColor(view, null, themeModel);
        else if (colorObject instanceof ThemeModelColor)
            color = resolveColor(view, (ThemeModelColor) colorObject, themeModel);
        else if (colorObject instanceof Integer)
            color = (Integer) colorObject;
        else
            color = resolveColor(view, null, themeModel);
        return color;
    }

    public static int resolveColor(Object view, ThemeModelColor themeModelColor,
                                   ThemeModel themeModel) {
        boolean lightForeground = themeModel.isForegroundLight(getBackgroundThemeModelColor(view));
        return themeModel.getColor(ThemeModelColor.getRealColor(themeModelColor, lightForeground));
    }

    public static int resolveColor(ThemeModelColor themeModelColor, ThemeModel themeModel) {
        return themeModel.getColor(themeModelColor);
    }

    public static ThemeModelColor getBackgroundThemeModelColor(Object view) {
        while (!Utils.isRootView(view)) {
            ThemeModelColor backColor = PropModel.BACKGROUND_COLOR.getPropAs(view, ThemeModelColor.class);
            if (backColor != null && backColor != ThemeModelColor.TRANSPARENT && backColor != ThemeModelColor.RIPPLE)
                return backColor;
            view = Utils.getParent(view);
        }
        return null;
    }

    private static boolean isRippleDrawable(Drawable drawable) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            return drawable instanceof RippleDrawable;
        return false;
    }

    public static void applyThemeToViewHierarchy(final Context context, final boolean designTime, final ImageProvider imageProvider, Object view, final ThemeModel themeModel,
                                                 final boolean force) {
        applyThemeToView(context, designTime, imageProvider, view, themeModel, force);
        Utils.iterateChildren(view, new Utils.ViewChildrenIterator() {
            @Override
            public Object processChild(Object parent, Object child, int position) {
                applyThemeToViewHierarchy(context, designTime, imageProvider, child, themeModel, force);
                return null;
            }
        });
    }

    private static void applyEvents(final Context context, boolean designTime, final Object view, final ThemeModel themeModel) {
        if (!designTime) {

            final PropEventValue propEventValueOnClick = PropModel.ON_CLICK.getPropAs(view, PropEventValue.class);
            if (propEventValueOnClick != null && !propEventValueOnClick.isEmpty()) {
                Utils.setOnClickListener(view, new Runnable() {
                    @Override
                    public void run() {
                        propEventValueOnClick.execute(context, view, themeModel);
                    }
                });
            }

            final PropEventValue propEventValueOnLongClick = PropModel.ON_LONG_CLICK.getPropAs(view, PropEventValue.class);
            if (propEventValueOnLongClick != null && !propEventValueOnLongClick.isEmpty()) {
                Utils.setOnLongClickListener(view, new Runnable() {
                    @Override
                    public void run() {
                        propEventValueOnLongClick.execute(context, view, themeModel);
                    }
                });
            }
        }
    }

    private static void applyVisibility(boolean designTime, Object view) {
        Integer visible = PropModel.VISIBILITY.getPropAsInteger(view);
        if (visible != null)
            if (!designTime) {
                if (Utils.getVisibility(view) != visible) {
                    Utils.setVisibility(view, visible);
                    if (visible == View.VISIBLE)
                        Utils.setAlpha(view, 1.0f);
                }
            } else {
                float currAlpha = Utils.getAlpha(view);
                float newAlpha = visible == View.VISIBLE ? 1.0f : 0.2f;
                if (currAlpha != newAlpha)
                    Utils.setAlpha(view, newAlpha);
            }
    }

    private static void applyLayoutAnimation(boolean designTime, Object view) {
        if (!designTime) {
            if ((view instanceof LinearLayout || view instanceof FrameLayout)) {
                Boolean layoutAnimation = PropModel.LAYOUT_ANIMATION.getPropAsBoolean(view);
                if (layoutAnimation != null)
                    ((ViewGroup) view).setLayoutTransition(layoutAnimation ? new LayoutTransition() : null);
            }
        }
    }

    private static void applyBackground(Context context, boolean designTime, ImageProvider imageProvider, Object view, final ThemeModel themeModel) {
        String backgroundImageFileName = PropModel.BACKGROUND_IMAGE.getPropAsString(view);
        if (backgroundImageFileName != null) {

            String currentBackgroundImageFileName = (String) Utils.getTag(view, R.id.PROP_CURRENT_BACKGROUND_IMAGE_DRAWABLE_TAG);
            if (!StringUtils.equals(currentBackgroundImageFileName, backgroundImageFileName)) {
                Bitmap bitmap = imageProvider == null ? null : imageProvider.loadImage(backgroundImageFileName, Utils.getWidth(view), Utils.getHeight(view));
                if (bitmap != null) {
                    Utils.setBackground(view, new BitmapDrawable(context.getResources(), bitmap));
                    Utils.requestLayout(view);
                    Utils.setTag(view, R.id.PROP_CURRENT_BACKGROUND_IMAGE_DRAWABLE_TAG, backgroundImageFileName);
                } else
                    Utils.setBackground(view, null);
            }

            Shader.TileMode tileMode = PropModel.BACKGROUND_IMAGE_TILE_MODE.getPropAs(view, Shader.TileMode.class);
            if (tileMode != null) {
                Drawable drawable = Utils.getBackground(view);
                if (drawable != null && drawable instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    if (bitmapDrawable.getTileModeX() != tileMode) {
                        bitmapDrawable.setTileModeXY(tileMode, tileMode);
                        Utils.invalidate(view);
                    }
                }
            }

        } else {
            ThemeModelColor backgroundColor = PropModel.BACKGROUND_COLOR.getPropAs(view, ThemeModelColor.class);
            if (backgroundColor != null) {
                if (backgroundColor == ThemeModelColor.RIPPLE) {
                    if (designTime) {
                        Utils.setBackground(view, null);
                    } else {
                        int[] attrs = new int[]{android.support.v7.appcompat.R.attr.selectableItemBackground};
                        TypedArray ta = context.obtainStyledAttributes(attrs);
                        Drawable drawableFromTheme = ta.getDrawable(0);
                        ta.recycle();
                        if (drawableFromTheme != null)
                            Utils.setBackground(view, drawableFromTheme);
                    }
                } else {
                    Drawable drawable = Utils.getBackground(view);
                    int color = resolveColor(view, backgroundColor, themeModel);
                    if (backgroundColor != ThemeModelColor.TRANSPARENT) {
                        int backAlpha = PropModel.BACKGROUND_ALPHA.getPropAsInt(view);
                        int colorAlpha = (color & 0xff000000) >>> 24;
                        float backAlphaF = backAlpha / 100.0f;
                        float colorAlphaF = colorAlpha / 255.0f;
                        int alpha = (int) (255.0f * backAlphaF * colorAlphaF);
                        alpha = Math.max(0, Math.min(alpha, 255));
                        color &= (0x00ffffff | alpha << 24);
                    }
                    if (drawable == null || drawable instanceof ColorDrawable || isRippleDrawable(drawable) || drawable instanceof BitmapDrawable)
                        Utils.setBackground(view, new ColorDrawable(color));
                    else {
                        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                        Utils.invalidate(view);
                    }
                }
            } else {
                Integer color = PropModel.BACKGROUND_COLOR.getPropAs(view, Integer.class);
                if (color != null) {
                    Drawable drawable = Utils.getBackground(view);
                    if (drawable == null || drawable instanceof ColorDrawable || isRippleDrawable(drawable) || drawable instanceof BitmapDrawable)
                        Utils.setBackground(view, new ColorDrawable(color));
                    else {
                        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                        Utils.invalidate(view);
                    }
                }
            }
        }
    }

    private static void applyOutline(Object view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Boolean outlineEnabled = PropModel.OUTLINE_ENABLED.getPropAsBoolean(view);
            if (outlineEnabled != null && outlineEnabled) {

                Boolean clipToOutline = PropModel.OUTLINE_CLIP_TO_OUTLINE.getPropAsBoolean(view);
                Utils.setClipToOutline(view, clipToOutline != null && clipToOutline);

                int offset = PropModel.OUTLINE_OFFSET.getPropAsInt(view);
                int radius = PropModel.OUTLINE_RADIUS.getPropAsInt(view);

                ViewOutlineProvider viewOutlineProvider = Utils.getOutlineProvider(view);
                if (viewOutlineProvider == null || !(viewOutlineProvider instanceof OutlineProvider)) {
                    OutlineProvider outlineProvider = new OutlineProvider(offset, radius);
                    Utils.setOutlineProvider(view, outlineProvider);
                    if (Build.VERSION.SDK_INT <= 22)
                        Utils.requestLayout(view);
                } else {
                    OutlineProvider outlineProvider = (OutlineProvider) Utils.getOutlineProvider(view);
                    final boolean outlineChanged = offset != outlineProvider.getOffset() || radius != outlineProvider.getRadius();
                    outlineProvider.setOffset(offset);
                    outlineProvider.setRadius(radius);
                    if (outlineChanged) {
                        Utils.invalidateOutline(view);
                        if (Build.VERSION.SDK_INT <= 22)
                            Utils.requestLayout(view);
                    }
                }
            } else {
                if (Utils.getOutlineProvider(view) != ViewOutlineProvider.BACKGROUND) {
                    Utils.setOutlineProvider(view, ViewOutlineProvider.BACKGROUND);
                    if (Build.VERSION.SDK_INT <= 22)
                        Utils.requestLayout(view);
                }
            }

            final Boolean clipToPadding = PropModel.OUTLINE_CLIP_TO_PADDING.getPropAsBoolean(view);
            if (clipToPadding != null)
                if (clipToPadding != Utils.getClipToPadding(view))
                    Utils.setClipToPadding(view, clipToPadding);
        }
    }

    private static void applyTextColor(Object view, final ThemeModel themeModel) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            ThemeModelColor textColor = PropModel.TEXT_COLOR.getPropAs(view, ThemeModelColor.class);
            if (textColor != null) {
                textView.setTextColor(resolveColor(view, textColor, themeModel));
            } else {
                Integer color = PropModel.TEXT_COLOR.getPropAs(view, Integer.class);
                if (color != null)
                    textView.setTextColor(color);
            }
        }
    }

    private static final int WHAT_NAV_ICON = 1;
    private static final int WHAT_LOGO_ICON = 2;

    private static void setDrawable(Object view, Drawable drawable, int what) {
        if (view instanceof ImageView)
            ((ImageView) view).setImageDrawable(drawable);
        else if (view instanceof Utils.Tab) {
            Utils.Tab tab = (Utils.Tab) view;
            tab.drawable = drawable;
            TabLayout.Tab realTab = Utils.findRealTabForTab(tab);
            if (realTab != null)
                realTab.setIcon(drawable);
        } else if (view instanceof Utils.MenuItem) {
            Utils.MenuItem menuItem = (Utils.MenuItem) view;
            menuItem.drawable = drawable;
            MenuItem menuItemView = Utils.findRealMenuItem(menuItem);
            if (menuItemView != null)
                menuItemView.setIcon(drawable);
        } else if (view instanceof Toolbar) {
            switch (what) {
                case WHAT_NAV_ICON:
                    ((Toolbar) view).setNavigationIcon(drawable);
                    break;
                case WHAT_LOGO_ICON:
                    ((Toolbar) view).setLogo(drawable);
                    break;
            }
        }
    }

    private static Drawable getDrawable(Object view) {
        if (view instanceof ImageView)
            return ((ImageView) view).getDrawable();
        else if (view instanceof Utils.Tab) {
            TabLayout.Tab realTab = Utils.findRealTabForTab((Utils.Tab) view);
            if (realTab != null)
                return realTab.getIcon();
        } else if (view instanceof Utils.MenuItem) {
            MenuItem menuItem = Utils.findRealMenuItem((Utils.MenuItem) view);
            if (menuItem != null)
                return menuItem.getIcon();
        } else if (view instanceof Toolbar) {
            return ((Toolbar) view).getNavigationIcon();
        }
        return null;
    }

    private static void applyIcon(Context context, Object view, ThemeModel themeModel,
                                  String iconId, Object colorObject, int iconSizePx,
                                  Object contourColorObject, int contourSizePx) {
        applyIcon(context, view, themeModel, iconId, colorObject, iconSizePx, contourColorObject, contourSizePx, 0);
    }

    private static void applyIcon(Context context, Object view, ThemeModel themeModel,
                                  String iconId, Object colorObject, int iconSizePx,
                                  Object contourColorObject, int contourSizePx, int what) {

        if (iconId == null || iconSizePx == 0) {
            setDrawable(view, null, what);
            return;
        }

        ColorStateList colorStateList = null;
        int color = 0;

        if (colorObject != null && colorObject instanceof ThemeModelColor[]) {

            ThemeModelColor[] themeModelColors = (ThemeModelColor[]) colorObject;
            if (themeModelColors.length == 2) {

                final int[][] states = new int[2][];
                final int[] colors = new int[2];
                int i = 0;

                states[i] = new int[]{android.R.attr.state_selected};
                colors[i] = resolveColor(view, themeModelColors[0], themeModel);
                i++;

                // Default enabled state
                states[i] = StateSet.NOTHING;
                colors[i] = resolveColor(view, themeModelColors[1], themeModel);

                colorStateList = new ColorStateList(states, colors);
            }
        } else
            color = resolveColorObject(view, colorObject, themeModel);

        int contourColor = resolveColorObject(view, contourColorObject, themeModel);

        IconicsDrawable iconicsDrawable = new IconicsDrawable(context).sizePx(iconSizePx).color(color).icon(iconId);
        if (contourSizePx == 0)
            iconicsDrawable.drawContour(false);
        else {
            iconicsDrawable.contourWidthPx(contourSizePx);
            iconicsDrawable.contourColor(contourColor);
        }

        if (colorStateList != null) {
            iconicsDrawable.setTintList(colorStateList);
            iconicsDrawable.setTintMode(PorterDuff.Mode.MULTIPLY);
            iconicsDrawable.color(0xffffffff);
        } else if (color != iconicsDrawable.getColor())
            iconicsDrawable.color(color);

        setDrawable(view, iconicsDrawable, what);
    }

    private static void applyDrawable(Context context, boolean designTime, ImageProvider imageProvider, Object view, final ThemeModel themeModel) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            String imageFileName = PropModel.IMAGE_DRAWABLE.getPropAsString(view);

            if (imageFileName != null) {

                String currentImageFileName = (String) imageView.getTag(R.id.PROP_CURRENT_IMAGE_DRAWABLE_TAG);
                if (!StringUtils.equals(currentImageFileName, imageFileName)) {
                    Bitmap bitmap = imageProvider == null ? null : imageProvider.loadImage(imageFileName, Utils.getWidth(view), Utils.getHeight(view));
                    if (bitmap != null) {
                        imageView.setAlpha(designTime ? 0.35f : 1.0f);
                        imageView.setImageDrawable(new BitmapDrawable(context.getResources(), bitmap));
                        imageView.requestLayout();
                        imageView.setTag(R.id.PROP_CURRENT_IMAGE_DRAWABLE_TAG, imageFileName);
                    } else
                        imageView.setImageDrawable(null);
                }

                ImageView.ScaleType scaleType = PropModel.IMAGE_SCALE_TYPE.getPropAs(view, ImageView.ScaleType.class);
                if (scaleType != null) {
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(scaleType);
                }

            } else {
                imageView.setTag(R.id.PROP_CURRENT_IMAGE_DRAWABLE_TAG, null);
                applyIcon(context, view, themeModel,
                        PropModel.ICON_DRAWABLE.getPropAsString(view),
                        PropModel.ICON_COLOR.getProp(view),
                        PropModel.ICON_SIZE.getPropAsInt(view),
                        PropModel.ICON_CONTOUR_COLOR.getProp(view),
                        PropModel.ICON_CONTOUR_SIZE.getPropAsInt(view));
            }
        } else if (view instanceof Utils.Tab) {
            applyIcon(context, view, themeModel,
                    PropModel.TAB_ICON_DRAWABLE.getPropAsString(view),
                    PropModel.TAB_LAYOUT_TEXT_COLORS.getProp(Utils.getParent(view)),
                    Utils.dpToPx(context, 24.0f), null, 0);
        } else if (view instanceof Utils.MenuItem) {
            applyIcon(context, view, themeModel,
                    PropModel.ACTION_ICON_DRAWABLE.getPropAsString(view),
                    PropModel.TOOLBAR_ACTION_ICON_COLOR.getProp(Utils.getParent(view)),
                    Utils.dpToPx(context, 24.0f), null, 0);
        } else if (view instanceof Toolbar) {
            applyIcon(context, view, themeModel,
                    PropModel.NAV_ICON_DRAWABLE.getPropAsString(view),
                    PropModel.TOOLBAR_ACTION_ICON_COLOR.getProp(view),
                    Utils.dpToPx(context, 24.0f), null, 0, WHAT_NAV_ICON);
            applyIcon(context, view, themeModel,
                    PropModel.LOGO_ICON_DRAWABLE.getPropAsString(view),
                    PropModel.TOOLBAR_ACTION_ICON_COLOR.getProp(view),
                    Utils.dpToPx(context, 24.0f), null, 0, WHAT_LOGO_ICON);
        }
    }

    private static void applyRecyclerView(Context context, boolean designTime, ImageProvider imageProvider, Object view, final ThemeModel themeModel, boolean force) {
        if (view instanceof RecyclerView) {

            RecyclerView recyclerView = (RecyclerView) view;

            int spans = PropModel.RECYCLER_VIEW_LAYOUT_SPANS.getPropAsInt(view);
            spans = spans <= 0 ? 1 : spans;
            final int orientation = PropModel.RECYCLER_VIEW_LAYOUT_ORIENTATION.getPropAsInt(view);

            GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            if (layoutManager == null || layoutManager.getSpanCount() != spans || layoutManager.getOrientation() != orientation)
                recyclerView.setLayoutManager(new GridLayoutManager(context, spans, orientation, false));

            final int paddingPx = PropModel.RECYCLER_VIEW_ITEM_PADDING.getPropAsInt(view);

            PaddingItemDecoration paddingItemDecoration = (PaddingItemDecoration) recyclerView.getTag(R.id.PROP_RECYCLER_VIEW_CURRENT_ITEM_DECORATOR_TAG);
            if (paddingItemDecoration == null) {
                paddingItemDecoration = new PaddingItemDecoration(orientation, paddingPx, spans, recyclerView);
                recyclerView.addItemDecoration(paddingItemDecoration);
                recyclerView.setTag(R.id.PROP_RECYCLER_VIEW_CURRENT_ITEM_DECORATOR_TAG, paddingItemDecoration);
            } else {
                boolean changed = paddingItemDecoration.getColumnCount() != spans ||
                        paddingItemDecoration.getPadding() != paddingPx ||
                        paddingItemDecoration.getOrientation() != orientation;
                paddingItemDecoration.setColumnCount(spans);
                paddingItemDecoration.setOrientation(orientation);
                paddingItemDecoration.setPadding(paddingPx);
                if (changed)
                    recyclerView.invalidateItemDecorations();
            }

            PropLayoutValue layoutValue = PropModel.RECYCLER_VIEW_ITEM_LAYOUT.getPropAs(view, PropLayoutValue.class);
            RecyclerViewLayoutAdapter adapter = (RecyclerViewLayoutAdapter) recyclerView.getAdapter();

            if (layoutValue != null && (adapter == null || force)) {
                LayoutDescriptor layoutDescriptor = ProjectServiceFactory.getInstance(context).loadLayout(layoutValue.getProjectId(), layoutValue.getLayoutId());
                if (layoutDescriptor != null && layoutDescriptor.getLayout() != null) {
                    adapter = new RecyclerViewLayoutAdapter(layoutDescriptor.getLayout());
                    recyclerView.setAdapter(adapter);
                }
            }

            if (adapter != null) {
                int count = PropModel.RECYCLER_VIEW_ITEM_COUNT.getPropAsInt(view);
                adapter.update(designTime, imageProvider, count, themeModel);
            }
        }
    }

    private static void applyTabLayoutIndicatorColor(Object view, final ThemeModel themeModel) {
        if (view instanceof TabLayout) {
            TabLayout tabLayout = (TabLayout) view;
            ThemeModelColor textColor = PropModel.TAB_LAYOUT_INDICATOR_COLOR.getPropAs(view, ThemeModelColor.class);
            if (textColor != null) {
                tabLayout.setSelectedTabIndicatorColor(resolveColor(view, textColor, themeModel));
            } else {
                Integer color = PropModel.TAB_LAYOUT_INDICATOR_COLOR.getPropAs(view, Integer.class);
                if (color != null)
                    tabLayout.setSelectedTabIndicatorColor(color);
            }
        }
    }

    private static void applyTabLayoutTextColors(Object view, final ThemeModel themeModel) {
        if (view instanceof TabLayout) {
            TabLayout tabLayout = (TabLayout) view;
            ThemeModelColor[] textColors = PropModel.TAB_LAYOUT_TEXT_COLORS.getPropAs(view, ThemeModelColor[].class);
            if (textColors != null) {
                int activeColor = resolveColor(view, textColors[0], themeModel);
                int inactiveColor = resolveColor(view, textColors[1], themeModel);
                tabLayout.setTabTextColors(inactiveColor, activeColor);
            } else {
                Integer[] colors = PropModel.TAB_LAYOUT_INDICATOR_COLOR.getPropAs(view, Integer[].class);
                if (colors != null) {
                    int activeColor = colors[0];
                    int inactiveColor = colors[1];
                    tabLayout.setTabTextColors(inactiveColor, activeColor);
                }
            }
        }
    }

    private static void applyTabLayoutDefaultTabIndex(Object view) {
        if (view instanceof TabLayout) {
            Boolean applied = (Boolean) Utils.getTag(view, R.id.VIEW_TAB_DEFAULT_INDEX_APPLIED_TAG);
            if (applied == null) {
                Utils.setTag(view, R.id.VIEW_TAB_DEFAULT_INDEX_APPLIED_TAG, Boolean.TRUE);
                Integer idx = PropModel.TAB_LAYOUT_DEFAULT_TAB_INDEX.getPropAsInteger(view);
                if (idx != null) {
                    TabLayout tabLayout = (TabLayout) view;
                    if (0 <= idx && idx < tabLayout.getTabCount()) {
                        TabLayout.Tab tab = tabLayout.getTabAt(idx);
                        if (tab != null)
                            tab.select();
                    }
                }
            }
        }
    }

    private static void applyTabLayoutViewPagerRef(Object view) {
        if (view instanceof TabLayout) {
            TabLayout tabLayout = (TabLayout) view;
            String viewPagerViewIdPath = PropModel.TAB_LAYOUT_VIEW_PAGER_REF.getPropAsString(view);
            String viewPagerViewIdPathCurrent = (String) tabLayout.getTag(R.id.PROP_TAB_LAYOUT_CURRENT_VIEW_PAGER_REF_TAG);
            if (!StringUtils.equals(viewPagerViewIdPath, viewPagerViewIdPathCurrent)) {

                tabLayout.setTag(R.id.PROP_TAB_LAYOUT_CURRENT_VIEW_PAGER_REF_TAG, viewPagerViewIdPath);

                if (viewPagerViewIdPath != null) {
                    Object root = Utils.getRootView(view);
                    Object viewPagerObject = Utils.findViewByViewIdPath(root, viewPagerViewIdPath);
                    if (viewPagerObject != null && viewPagerObject instanceof ViewPager) {
                        ViewPager viewPager = (ViewPager) viewPagerObject;
                        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                    } else {
                        tabLayout.clearOnTabSelectedListeners();
                    }
                } else {
                    tabLayout.clearOnTabSelectedListeners();
                }
            }
        }
    }

    private static void applyToolbarActionIconColor(Object view, final ThemeModel themeModel) {
        if (view instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) view;

            ColorStateList color = ColorStateList.valueOf(resolveColorObject(view, PropModel.TOOLBAR_ACTION_ICON_COLOR.getProp(view), themeModel));

            ImageView imageView;

            imageView = Utils.getMoreIconViewForToolbar(toolbar);
            if (imageView != null) {
                imageView.setImageTintList(color);
                imageView.setImageTintMode(PorterDuff.Mode.MULTIPLY);
            }
        }
    }

    private static void applyToolbarActionTextColor(Object view, final ThemeModel themeModel) {
        if (view instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) view;

            if (toolbar.getChildCount() > 0) {

                ViewGroup buttonsViewGroup = null;

                for (int i = 0; i < toolbar.getChildCount(); i++) {
                    View child = toolbar.getChildAt(i);
                    if (child instanceof ActionMenuView) {
                        buttonsViewGroup = (ViewGroup) child;
                        break;
                    }
                }

                if (buttonsViewGroup != null && buttonsViewGroup.getChildCount() > 0) {
                    int color = resolveColorObject(view, PropModel.TOOLBAR_ACTION_TEXT_COLOR.getProp(view), themeModel);
                    for (int i = 0; i < buttonsViewGroup.getChildCount(); i++) {
                        View child = buttonsViewGroup.getChildAt(i);
                        if (child instanceof TextView) {
                            TextView textView = (TextView) child;
                            textView.setTextColor(color);
                        }
                    }
                }
            }
        }
    }

    private static void applyToolbarTitleTextColor(Object view, final ThemeModel themeModel) {
        if (view instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) view;
            int color = resolveColorObject(view, PropModel.TOOLBAR_TITLE_TEXT_COLOR.getProp(view), themeModel);
            toolbar.setTitleTextColor(color);
        }
    }

    private static void applyToolbarSubtitleTextColor(Object view, final ThemeModel themeModel) {
        if (view instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) view;
            int color = resolveColorObject(view, PropModel.TOOLBAR_SUBTITLE_TEXT_COLOR.getProp(view), themeModel);
            toolbar.setSubtitleTextColor(color);
        }
    }

    public static void applyThemeToView(Context context, boolean designTime, ImageProvider imageProvider, Object view, final ThemeModel themeModel, boolean force) {
        applyEvents(context, designTime, view, themeModel);
        applyVisibility(designTime, view);
        applyLayoutAnimation(designTime, view);
        applyBackground(context, designTime, imageProvider, view, themeModel);
        applyOutline(view);
        applyTextColor(view, themeModel);
        applyDrawable(context, designTime, imageProvider, view, themeModel);
        applyRecyclerView(context, designTime, imageProvider, view, themeModel, force);
        applyTabLayoutIndicatorColor(view, themeModel);
        applyTabLayoutTextColors(view, themeModel);
        applyTabLayoutViewPagerRef(view);
        applyTabLayoutDefaultTabIndex(view);
        applyToolbarActionIconColor(view, themeModel);
        applyToolbarActionTextColor(view, themeModel);
        applyToolbarTitleTextColor(view, themeModel);
        applyToolbarSubtitleTextColor(view, themeModel);
    }
}