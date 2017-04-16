package com.crane.mockapp.core.model.props;

import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crane.mockapp.core.R;
import com.crane.mockapp.core.Utils;
import com.crane.mockapp.core.model.theme.ThemeModelColor;
import com.crane.mockapp.core.model.layouts.PropLayoutValue;
import com.crane.mockapp.core.model.layouts.PropEventValue;
import com.crane.mockapp.core.model.layouts.LayoutDescriptor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crane2002 on 1/4/2017.
 */

public enum PropModel {

    VIEW_ID(null) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_VIEW_ID_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof String)
                Utils.setTag(view, R.id.PROP_VIEW_ID_TAG, value);
        }
    },

    VISIBILITY(PropModelCategory.VISIBILITY) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_VISIBILITY_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setTag(view, R.id.PROP_VISIBILITY_TAG, value);
        }
    },

    LAYOUT_ANIMATION(PropModelCategory.ANIMATION) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_LAYOUT_ANIMATION_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Boolean)
                Utils.setTag(view, R.id.PROP_LAYOUT_ANIMATION_TAG, value);
        }
    },

    LAYOUT_ORIENTATION(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof LinearLayout)
                return ((LinearLayout) view).getOrientation();
            return -1;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof LinearLayout && value != null && value instanceof Integer) {
                ((LinearLayout) view).setOrientation((Integer) value);
                Utils.requestLayout(view);
            }
        }
    },

    IMAGE_DRAWABLE(PropModelCategory.IMAGE) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_IMAGE_DRAWABLE_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof String)
                Utils.setTag(view, R.id.PROP_IMAGE_DRAWABLE_TAG, value);
            else
                Utils.setTag(view, R.id.PROP_IMAGE_DRAWABLE_TAG, null);
        }
    },

    IMAGE_SCALE_TYPE(PropModelCategory.IMAGE) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_IMAGE_SCALE_TYPE_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof ImageView.ScaleType)
                Utils.setTag(view, R.id.PROP_IMAGE_SCALE_TYPE_TAG, value);
        }
    },

    NAV_ICON_DRAWABLE(PropModelCategory.ICON) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_NAV_ICON_DRAWABLE_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof String)
                Utils.setTag(view, R.id.PROP_NAV_ICON_DRAWABLE_TAG, value);
            else
                Utils.setTag(view, R.id.PROP_NAV_ICON_DRAWABLE_TAG, null);
        }
    },

    LOGO_ICON_DRAWABLE(PropModelCategory.ICON) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_LOGO_ICON_DRAWABLE_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof String)
                Utils.setTag(view, R.id.PROP_LOGO_ICON_DRAWABLE_TAG, value);
            else
                Utils.setTag(view, R.id.PROP_LOGO_ICON_DRAWABLE_TAG, null);
        }
    },

    ACTION_ICON_DRAWABLE(PropModelCategory.ICON) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_ACTION_ICON_DRAWABLE_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof String)
                Utils.setTag(view, R.id.PROP_ACTION_ICON_DRAWABLE_TAG, value);
            else
                Utils.setTag(view, R.id.PROP_ACTION_ICON_DRAWABLE_TAG, null);
        }
    },

    TAB_ICON_DRAWABLE(PropModelCategory.ICON) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_TAB_ICON_DRAWABLE_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof String)
                Utils.setTag(view, R.id.PROP_TAB_ICON_DRAWABLE_TAG, value);
            else
                Utils.setTag(view, R.id.PROP_TAB_ICON_DRAWABLE_TAG, null);
        }
    },

    ICON_DRAWABLE(PropModelCategory.ICON) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_ICON_DRAWABLE_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof String)
                Utils.setTag(view, R.id.PROP_ICON_DRAWABLE_TAG, value);
            else
                Utils.setTag(view, R.id.PROP_ICON_DRAWABLE_TAG, null);
        }
    },

    ICON_SIZE(PropModelCategory.ICON) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_ICON_SIZE_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setTag(view, R.id.PROP_ICON_SIZE_TAG, value);
        }
    },

    ICON_COLOR(PropModelCategory.ICON) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_ICON_COLOR_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && (value instanceof ThemeModelColor || value instanceof Integer))
                Utils.setTag(view, R.id.PROP_ICON_COLOR_TAG, value);
        }
    },

    ICON_CONTOUR_COLOR(PropModelCategory.ICON) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_ICON_CONTOUR_COLOR_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && (value instanceof ThemeModelColor || value instanceof Integer))
                Utils.setTag(view, R.id.PROP_ICON_CONTOUR_COLOR_TAG, value);
        }
    },

    ICON_CONTOUR_SIZE(PropModelCategory.ICON) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_ICON_CONTOUR_SIZE_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setTag(view, R.id.PROP_ICON_CONTOUR_SIZE_TAG, value);
        }
    },

    LAYOUT_WIDTH(PropModelCategory.SIZE) {
        @Override
        public Object doGetProp(Object view) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null)
                return layoutParams.width;
            return ViewGroup.LayoutParams.MATCH_PARENT;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer) {
                ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
                if (layoutParams != null) {
                    layoutParams.width = (Integer) value;
                    Utils.requestLayout(view);
                }
            }
        }
    },

    LAYOUT_HEIGHT(PropModelCategory.SIZE) {
        @Override
        public Object doGetProp(Object view) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null)
                return layoutParams.height;
            return ViewGroup.LayoutParams.MATCH_PARENT;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer) {
                ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
                if (layoutParams != null) {
                    layoutParams.height = (Integer) value;
                    Utils.requestLayout(view);
                }
            }
        }
    },

    LAYOUT_MIN_WIDTH(PropModelCategory.SIZE) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getMinimumWidth(view);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setMinimumWidth(view, (Integer) value);
        }
    },
    LAYOUT_MIN_HEIGHT(PropModelCategory.SIZE) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getMinimumHeight(view);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setMinimumHeight(view, (Integer) value);
        }
    },

    LAYOUT_WEIGHT(PropModelCategory.SIZE) {
        @Override
        public Object doGetProp(Object view) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null && layoutParams instanceof LinearLayout.LayoutParams)
                return (int) ((LinearLayout.LayoutParams) layoutParams).weight;
            return 0;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null && layoutParams instanceof LinearLayout.LayoutParams && value != null && value instanceof Integer) {
                ((LinearLayout.LayoutParams) layoutParams).weight = (Integer) value;
                Utils.requestLayout(view);
            }
        }
    },

    LAYOUT_GRAVITY(PropModelCategory.GRAVITY) {
        @Override
        public Object doGetProp(Object view) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null && layoutParams instanceof LinearLayout.LayoutParams)
                return ((LinearLayout.LayoutParams) layoutParams).gravity;
            else if (layoutParams != null && layoutParams instanceof FrameLayout.LayoutParams)
                return ((FrameLayout.LayoutParams) layoutParams).gravity;
            return 0;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null && layoutParams instanceof LinearLayout.LayoutParams && value != null && value instanceof Integer) {
                ((LinearLayout.LayoutParams) layoutParams).gravity = (Integer) value;
                Utils.requestLayout(view);
            }
            if (layoutParams != null && layoutParams instanceof FrameLayout.LayoutParams && value != null && value instanceof Integer) {
                ((FrameLayout.LayoutParams) layoutParams).gravity = (Integer) value;
                Utils.requestLayout(view);
            }
        }
    },

    LAYOUT_MARGIN_START(null) {
        @Override
        public Object doGetProp(Object view) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams)
                return ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
            return 0;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams && value != null && value instanceof Integer) {
                ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = (Integer) value;
                Utils.requestLayout(view);
            }
        }
    },

    LAYOUT_MARGIN_TOP(null) {
        @Override
        public Object doGetProp(Object view) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams)
                return ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
            return 0;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams && value != null && value instanceof Integer) {
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = (Integer) value;
                Utils.requestLayout(view);
            }
        }
    },

    LAYOUT_MARGIN_END(null) {
        @Override
        public Object doGetProp(Object view) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams)
                return ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            return 0;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams && value != null && value instanceof Integer) {
                ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = (Integer) value;
                Utils.requestLayout(view);
            }
        }
    },

    LAYOUT_MARGIN_BOTTOM(null) {
        @Override
        public Object doGetProp(Object view) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams)
                return ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            return 0;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            ViewGroup.LayoutParams layoutParams = Utils.getLayoutParams(view);
            if (layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams && value != null && value instanceof Integer) {
                ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = (Integer) value;
                Utils.requestLayout(view);
            }
        }
    },

    LAYOUT_MARGINS(PropModelCategory.MARGINS) {
        @Override
        public Object doGetProp(Object view) {
            return null;
        }

        @Override
        public void doSetProp(Object view, Object value) {
        }
    },

    PADDING_START(null) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getPaddingLeft(view);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setPaddingLeft(view, (Integer) value);
        }
    },

    PADDING_TOP(null) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getPaddingTop(view);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setPaddingTop(view, (Integer) value);
        }
    },

    PADDING_END(null) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getPaddingRight(view);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setPaddingRight(view, (Integer) value);
        }
    },

    PADDING_BOTTOM(null) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getPaddingBottom(view);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setPaddingBottom(view, (Integer) value);
        }
    },

    PADDINGS(PropModelCategory.PADDINGS) {
        @Override
        public Object doGetProp(Object view) {
            return null;
        }

        @Override
        public void doSetProp(Object view, Object value) {
        }
    },

    TOOLBAR_TITLE_TEXT(PropModelCategory.TEXT) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof Toolbar) {
                Toolbar toolbar = (Toolbar) view;
                return toolbar.getTitle() == null ? "" : toolbar.getTitle().toString();
            }
            return null;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && value != null && view instanceof Toolbar && value instanceof String) {
                Toolbar toolbar = (Toolbar) view;
                toolbar.setTitle((String) value);
            }
        }
    },

    TOOLBAR_SUBTITLE_TEXT(PropModelCategory.TEXT) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof Toolbar) {
                Toolbar toolbar = (Toolbar) view;
                return toolbar.getSubtitle() == null ? "" : toolbar.getSubtitle().toString();
            }
            return null;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && value != null && view instanceof Toolbar && value instanceof String) {
                Toolbar toolbar = (Toolbar) view;
                toolbar.setSubtitle((String) value);
            }
        }
    },

    TEXT(PropModelCategory.TEXT) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof TextView) {
                TextView textView = (TextView) view;
                return textView.getText() == null ? "" : textView.getText().toString();
            } else if (view != null && view instanceof Utils.Tab) {
                Utils.Tab tab = (Utils.Tab) view;
                return tab.text == null ? "" : tab.text;
            } else if (view != null && view instanceof Utils.MenuItem) {
                Utils.MenuItem menuItem = (Utils.MenuItem) view;
                return menuItem.text == null ? "" : menuItem.text;
            }
            return null;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && value != null && view instanceof TextView && value instanceof String) {
                TextView textView = (TextView) view;
                textView.setText((String) value);
            } else if (view != null && value != null && view instanceof Utils.Tab && value instanceof String) {
                Utils.Tab tab = (Utils.Tab) view;
                tab.text = (String) value;
                TabLayout.Tab realTab = Utils.findRealTabForTab(tab);
                if (realTab != null)
                    realTab.setText((String) value);
            } else if (view != null && value != null && view instanceof Utils.MenuItem && value instanceof String) {
                Utils.MenuItem menuItem = (Utils.MenuItem) view;
                menuItem.text = (String) value;
                MenuItem realMenuItem = Utils.findRealMenuItem(menuItem);
                if (realMenuItem != null)
                    realMenuItem.setTitle((String) value);
            }
        }
    },

    TEXT_COLOR(PropModelCategory.TEXT_STYLE) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_TEXT_COLOR_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && (value instanceof ThemeModelColor || value instanceof Integer))
                Utils.setTag(view, R.id.PROP_TEXT_COLOR_TAG, value);
        }
    },

    TEXT_SIZE(PropModelCategory.TEXT_STYLE) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof TextView)
                return (int) ((TextView) view).getTextSize();
            return 0;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof TextView && value != null && value instanceof Integer) {
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, (Integer) value);
                Utils.requestLayout(view);
            }
        }
    },

    TEXT_STYLE(PropModelCategory.TEXT_STYLE) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof TextView) {
                TextView textView = (TextView) view;
                if (textView.getTypeface() != null) return textView.getTypeface().getStyle();
            }
            return 0;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof TextView && value != null && value instanceof Integer) {
                TextView textView = (TextView) view;
                Typeface typeface = Typeface.create((String) null, (Integer) value);
                textView.setTypeface(typeface);
                textView.requestLayout();
            }
        }
    },

    TEXT_GRAVITY(PropModelCategory.TEXT_STYLE) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof TextView) return ((TextView) view).getGravity();
            return 0;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof TextView && value != null && value instanceof Integer) {
                ((TextView) view).setGravity((Integer) value);
                Utils.requestLayout(view);
            }
        }
    },

    TEXT_MIN_LINES(PropModelCategory.TEXT_STYLE) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof TextView)
                return Math.max(1, Math.min(50, ((TextView) view).getMinLines()));
            return 1;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof TextView && value != null && value instanceof Integer)
                ((TextView) view).setMinLines((Integer) value);
        }
    },

    TEXT_MAX_LINES(PropModelCategory.TEXT_STYLE) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof TextView)
                return Math.max(1, Math.min(50, ((TextView) view).getMaxLines()));
            return 1;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof TextView && value != null && value instanceof Integer)
                ((TextView) view).setMaxLines((Integer) value);
        }
    },

    TEXT_ELLIPSES(PropModelCategory.TEXT_STYLE) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof TextView)
                return ((TextView) view).getEllipsize();
            return null;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof TextView && value != null && value instanceof TextUtils.TruncateAt)
                ((TextView) view).setEllipsize((TextUtils.TruncateAt) value);
        }
    },

    ALPHA(PropModelCategory.BACKGROUND) {
        @Override
        public Object doGetProp(Object view) {
            int alpha = (int) (100.0f * Utils.getAlpha(view));
            alpha = Math.max(0, Math.min(alpha, 100));
            return alpha;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer) {
                float alpha = (Integer) value / 100.0f;
                alpha = Math.max(0.0f, Math.min(alpha, 1.0f));
                Utils.setAlpha(view, alpha);
            }
        }
    },

    BACKGROUND_ALPHA(PropModelCategory.BACKGROUND) {
        @Override
        public Object doGetProp(Object view) {
            Object value = Utils.getTag(view, R.id.PROP_BACKGROUND_ALPHA_TAG);
            if (value == null)
                return 100;
            return value;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setTag(view, R.id.PROP_BACKGROUND_ALPHA_TAG, value);
        }
    },

    BACKGROUND_COLOR(PropModelCategory.BACKGROUND) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_BACKGROUND_COLOR_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && (value instanceof ThemeModelColor || value instanceof Integer))
                Utils.setTag(view, R.id.PROP_BACKGROUND_COLOR_TAG, value);
        }
    },

    BACKGROUND_IMAGE(PropModelCategory.BACKGROUND) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_BACKGROUND_IMAGE_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof String)
                Utils.setTag(view, R.id.PROP_BACKGROUND_IMAGE_TAG, value);
            else
                Utils.setTag(view, R.id.PROP_BACKGROUND_IMAGE_TAG, null);
        }
    },

    BACKGROUND_IMAGE_TILE_MODE(PropModelCategory.BACKGROUND) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_BACKGROUND_IMAGE_TILE_MODE_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Shader.TileMode)
                Utils.setTag(view, R.id.PROP_BACKGROUND_IMAGE_TILE_MODE_TAG, value);
        }
    },

    RECYCLER_VIEW_ITEM_LAYOUT(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_RECYCLER_VIEW_ITEM_LAYOUT_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof PropLayoutValue)
                Utils.setTag(view, R.id.PROP_RECYCLER_VIEW_ITEM_LAYOUT_TAG, value);
        }
    },

    RECYCLER_VIEW_ITEM_COUNT(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_RECYCLER_VIEW_ITEM_COUNT_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setTag(view, R.id.PROP_RECYCLER_VIEW_ITEM_COUNT_TAG, value);
        }
    },

    RECYCLER_VIEW_LAYOUT_ORIENTATION(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            Object value = Utils.getTag(view, R.id.PROP_RECYCLER_VIEW_LAYOUT_ORIENTATION_TAG);
            if (value == null)
                return OrientationHelper.VERTICAL;
            return value;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setTag(view, R.id.PROP_RECYCLER_VIEW_LAYOUT_ORIENTATION_TAG, value);
        }
    },

    RECYCLER_VIEW_LAYOUT_SPANS(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_RECYCLER_VIEW_LAYOUT_SPANS_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setTag(view, R.id.PROP_RECYCLER_VIEW_LAYOUT_SPANS_TAG, value);
        }
    },

    RECYCLER_VIEW_ITEM_PADDING(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_RECYCLER_VIEW_LAYOUT_PADDING_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setTag(view, R.id.PROP_RECYCLER_VIEW_LAYOUT_PADDING_TAG, value);
        }
    },

    CUSTOM_LAYOUT(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_CUSTOM_LAYOUT_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof PropLayoutValue)
                Utils.setTag(view, R.id.PROP_CUSTOM_LAYOUT_TAG, value);
            else
                Utils.setTag(view, R.id.PROP_CUSTOM_LAYOUT_TAG, null);
        }
    },

    ON_CLICK(PropModelCategory.ON_CLICK) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_ONCLICK_TAG);
        }

        @Override
        public void doSetProp(Object view, final Object value) {
            if (value != null && value instanceof PropEventValue)
                Utils.setTag(view, R.id.PROP_ONCLICK_TAG, value);
            else
                Utils.setTag(view, R.id.PROP_ONCLICK_TAG, null);
        }
    },

    ON_LONG_CLICK(PropModelCategory.ON_LONG_CLICK) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_ONLONGCLICK_TAG);
        }

        @Override
        public void doSetProp(Object view, final Object value) {
            if (value != null && value instanceof PropEventValue)
                Utils.setTag(view, R.id.PROP_ONLONGCLICK_TAG, value);
            else
                Utils.setTag(view, R.id.PROP_ONLONGCLICK_TAG, null);
        }
    },

    ELEVATION(PropModelCategory.OUTLINE) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof View)
                return (int) ViewCompat.getElevation((View) view);
            return null;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof View && value != null && value instanceof Integer)
                ViewCompat.setElevation((View) view, (Integer) value);
        }
    },

    OUTLINE_ENABLED(PropModelCategory.OUTLINE) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_OUTLINE_ENABLED_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Boolean)
                Utils.setTag(view, R.id.PROP_OUTLINE_ENABLED_TAG, value);
        }
    },

    OUTLINE_CLIP_TO_PADDING(PropModelCategory.OUTLINE) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_OUTLINE_CLIP_TO_PADDING_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Boolean)
                Utils.setTag(view, R.id.PROP_OUTLINE_CLIP_TO_PADDING_TAG, value);
        }
    },

    OUTLINE_CLIP_TO_OUTLINE(PropModelCategory.OUTLINE) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_OUTLINE_CLIP_TO_OUTLINE_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Boolean)
                Utils.setTag(view, R.id.PROP_OUTLINE_CLIP_TO_OUTLINE_TAG, value);
        }
    },

    OUTLINE_RADIUS(PropModelCategory.OUTLINE) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_OUTLINE_RADIUS_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setTag(view, R.id.PROP_OUTLINE_RADIUS_TAG, value);
        }
    },

    OUTLINE_OFFSET(PropModelCategory.OUTLINE) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_OUTLINE_OFFSET_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setTag(view, R.id.PROP_OUTLINE_OFFSET_TAG, value);
        }
    },

    TAB_LAYOUT_TAB_MODE(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof TabLayout)
                return ((TabLayout) view).getTabMode();
            return null;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof TabLayout && value != null && value instanceof Integer)
                ((TabLayout) view).setTabMode((Integer) value);
        }
    },

    TAB_LAYOUT_INDICATOR_COLOR(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_TAB_LAYOUT_INDICATOR_COLOR_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && (value instanceof ThemeModelColor || value instanceof Integer))
                Utils.setTag(view, R.id.PROP_TAB_LAYOUT_INDICATOR_COLOR_TAG, value);
        }
    },

    TAB_LAYOUT_INDICATOR_HEIGHT(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_TAB_LAYOUT_INDICATOR_HEIGHT_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof TabLayout && value != null && value instanceof Integer) {
                ((TabLayout) view).setSelectedTabIndicatorHeight((Integer) value);
                Utils.setTag(view, R.id.PROP_TAB_LAYOUT_INDICATOR_HEIGHT_TAG, (Integer) value);
            }
        }
    },

    TAB_LAYOUT_TEXT_COLORS(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_TAB_LAYOUT_TEXT_COLORS_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof TabLayout && value != null && value instanceof ThemeModelColor[]) {
                ThemeModelColor[] colors = (ThemeModelColor[]) value;
                if (colors.length == 2)
                    Utils.setTag(view, R.id.PROP_TAB_LAYOUT_TEXT_COLORS_TAG, value);
            }
        }
    },

    TAB_LAYOUT_VIEW_PAGER_REF(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_TAB_LAYOUT_VIEW_PAGER_REF_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof String)
                Utils.setTag(view, R.id.PROP_TAB_LAYOUT_VIEW_PAGER_REF_TAG, value);
            else
                Utils.setTag(view, R.id.PROP_TAB_LAYOUT_VIEW_PAGER_REF_TAG, null);
        }
    },

    TAB_LAYOUT_DEFAULT_TAB_INDEX(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_TAB_LAYOUT_TAB_LAYOUT_DEFAULT_TAB_INDEX_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && value instanceof Integer)
                Utils.setTag(view, R.id.PROP_TAB_LAYOUT_TAB_LAYOUT_DEFAULT_TAB_INDEX_TAG, value);
        }
    },

    TOOLBAR_ACTION_TEXT_COLOR(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_TOOLBAR_ACTION_TEXT_COLOR_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && (value instanceof ThemeModelColor || value instanceof Integer))
                Utils.setTag(view, R.id.PROP_TOOLBAR_ACTION_TEXT_COLOR_TAG, value);
        }
    },

    TOOLBAR_ACTION_ICON_COLOR(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_TOOLBAR_ACTION_ICON_COLOR_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && (value instanceof ThemeModelColor || value instanceof Integer))
                Utils.setTag(view, R.id.PROP_TOOLBAR_ACTION_ICON_COLOR_TAG, value);
        }
    },

    TOOLBAR_TITLE_TEXT_COLOR(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_TOOLBAR_TITLE_TEXT_COLOR_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && (value instanceof ThemeModelColor || value instanceof Integer))
                Utils.setTag(view, R.id.PROP_TOOLBAR_TITLE_TEXT_COLOR_TAG, value);
        }
    },

    TOOLBAR_SUBTITLE_TEXT_COLOR(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            return Utils.getTag(view, R.id.PROP_TOOLBAR_SUBTITLE_TEXT_COLOR_TAG);
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (value != null && (value instanceof ThemeModelColor || value instanceof Integer))
                Utils.setTag(view, R.id.PROP_TOOLBAR_SUBTITLE_TEXT_COLOR_TAG, value);
        }
    },

    TOOLBAR_MENU_ITEM_SHOW_AS_ACTION(PropModelCategory.PROP) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof Utils.MenuItem) {
                Utils.MenuItem menuItem = (Utils.MenuItem) view;
                return menuItem.showAsActionFlag;
            }
            return MenuItem.SHOW_AS_ACTION_NEVER;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof Utils.MenuItem && value != null && value instanceof Integer) {
                Utils.MenuItem menuItem = (Utils.MenuItem) view;
                menuItem.showAsActionFlag = (Integer) value;
                MenuItem realMenuItem = Utils.findRealMenuItem(menuItem);
                if (realMenuItem != null)
                    realMenuItem.setShowAsAction((Integer) value);
            }
        }
    },

    NAVIGATION_DRAWER_LAYOUT(PropModelCategory.GENERAL) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof LayoutDescriptor)
                return ((LayoutDescriptor) view).getNavDrawerLayout();
            return null;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof LayoutDescriptor)
                if (value != null && value instanceof PropLayoutValue)
                    ((LayoutDescriptor) view).setNavDrawerLayout((PropLayoutValue) value);
                else
                    ((LayoutDescriptor) view).setNavDrawerLayout(null);
        }
    },

    STATUS_BAR_COLOR(PropModelCategory.GENERAL) {
        @Override
        public Object doGetProp(Object view) {
            if (view != null && view instanceof LayoutDescriptor)
                return ((LayoutDescriptor) view).getStatusBarColor();
            return null;
        }

        @Override
        public void doSetProp(Object view, Object value) {
            if (view != null && view instanceof LayoutDescriptor)
                if (value != null && value instanceof ThemeModelColor)
                    ((LayoutDescriptor) view).setStatusBarColor((ThemeModelColor) value);
                else
                    ((LayoutDescriptor) view).setStatusBarColor(null);
        }
    };

    private PropModelCategory category;

    PropModel(PropModelCategory category) {
        this.category = category;
    }

    public PropModelCategory getCategory() {
        return category;
    }

    public boolean isApplicableForView(Object view) {
        if (view == null)
            return false;

        switch (this) {

            case BACKGROUND_COLOR:
            case BACKGROUND_ALPHA:
            case BACKGROUND_IMAGE:
            case BACKGROUND_IMAGE_TILE_MODE:
                return view instanceof View;

            case OUTLINE_ENABLED:
            case OUTLINE_RADIUS:
            case OUTLINE_OFFSET:
            case OUTLINE_CLIP_TO_OUTLINE:
            case ELEVATION:
                return view instanceof View;

            case OUTLINE_CLIP_TO_PADDING:
                return view instanceof ViewGroup;

            case VISIBILITY:
                return view instanceof View;

            case LAYOUT_ANIMATION:
                return (view instanceof LinearLayout || view instanceof FrameLayout);

            case LAYOUT_ORIENTATION:
                return view instanceof LinearLayout;

            case LAYOUT_MIN_WIDTH:
            case LAYOUT_MIN_HEIGHT:
            case LAYOUT_WIDTH:
            case LAYOUT_HEIGHT:
                return view instanceof View;

            case LAYOUT_MARGIN_START:
            case LAYOUT_MARGIN_TOP:
            case LAYOUT_MARGIN_END:
            case LAYOUT_MARGIN_BOTTOM:
            case LAYOUT_MARGINS:
                return Utils.getLayoutParams(view) != null && Utils.getLayoutParams(view) instanceof ViewGroup.MarginLayoutParams;

            case LAYOUT_GRAVITY:
                return Utils.getLayoutParams(view) != null && (Utils.getLayoutParams(view) instanceof LinearLayout.LayoutParams || Utils.getLayoutParams(view) instanceof FrameLayout.LayoutParams);

            case LAYOUT_WEIGHT:
                return Utils.getLayoutParams(view) != null && Utils.getLayoutParams(view) instanceof LinearLayout.LayoutParams;

            case PADDING_START:
            case PADDING_TOP:
            case PADDING_END:
            case PADDING_BOTTOM:
            case PADDINGS:
                return view instanceof View;

            case TEXT:
                return view instanceof TextView || view instanceof Utils.Tab || view instanceof Utils.MenuItem;

            case TOOLBAR_TITLE_TEXT:
            case TOOLBAR_SUBTITLE_TEXT:
                return view instanceof Toolbar;

            case TEXT_COLOR:
            case TEXT_GRAVITY:
            case TEXT_STYLE:
            case TEXT_SIZE:
            case TEXT_MIN_LINES:
            case TEXT_MAX_LINES:
            case TEXT_ELLIPSES:
                return view instanceof TextView;

            case LOGO_ICON_DRAWABLE:
            case NAV_ICON_DRAWABLE:
                return view instanceof Toolbar;

            case ACTION_ICON_DRAWABLE:
                return view instanceof Utils.MenuItem;

            case TAB_ICON_DRAWABLE:
                return view instanceof Utils.Tab;

            case ICON_DRAWABLE:
            case ICON_SIZE:
            case ICON_COLOR:
            case ICON_CONTOUR_COLOR:
            case ICON_CONTOUR_SIZE:
                return view instanceof ImageView;

            case IMAGE_DRAWABLE:
            case IMAGE_SCALE_TYPE:
                return view instanceof ImageView;

            case RECYCLER_VIEW_ITEM_LAYOUT:
            case RECYCLER_VIEW_ITEM_COUNT:
            case RECYCLER_VIEW_ITEM_PADDING:
            case RECYCLER_VIEW_LAYOUT_ORIENTATION:
            case RECYCLER_VIEW_LAYOUT_SPANS:
                return view instanceof RecyclerView;

            case ON_CLICK:
                return view instanceof View || view instanceof Utils.MenuItem;

            case ON_LONG_CLICK:
                return view instanceof View && !(view instanceof Toolbar);

            case TAB_LAYOUT_TAB_MODE:
            case TAB_LAYOUT_TEXT_COLORS:
            case TAB_LAYOUT_INDICATOR_COLOR:
            case TAB_LAYOUT_INDICATOR_HEIGHT:
            case TAB_LAYOUT_VIEW_PAGER_REF:
            case TAB_LAYOUT_DEFAULT_TAB_INDEX:
                return view instanceof TabLayout;

            case TOOLBAR_ACTION_TEXT_COLOR:
            case TOOLBAR_ACTION_ICON_COLOR:
            case TOOLBAR_TITLE_TEXT_COLOR:
            case TOOLBAR_SUBTITLE_TEXT_COLOR:
                return view instanceof Toolbar;

            case TOOLBAR_MENU_ITEM_SHOW_AS_ACTION:
                return view instanceof Utils.MenuItem;

            case STATUS_BAR_COLOR:
            case NAVIGATION_DRAWER_LAYOUT:
                return view instanceof LayoutDescriptor;
        }
        return false;
    }

    public static final PropModel[] VALUES = PropModel.values();

    public enum PropModifyAction {
        DEFAULT,
        FORCE_MODIFY,
        CLEAR_MODIFY
    }

    public static PropModel fromOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal >= VALUES.length)
            return VALUES[0];
        return VALUES[ordinal];
    }

    public final Object getProp(Object view) {
        return doGetProp(view);
    }

    public final void setProp(Object view, Object value) {
        setProp(view, value, PropModifyAction.DEFAULT);
    }

    public final void setProp(Object view, Object value, PropModifyAction propModifyAction) {
        doSetProp(view, value);

        Map<PropModel, Object> originalValues = (Map<PropModel, Object>) Utils.getTag(view, R.id.PROP_ORIGINAL_VALUE_TAG);

        if (originalValues == null) {
            originalValues = new HashMap<>();
            Utils.setTag(view, R.id.PROP_ORIGINAL_VALUE_TAG, originalValues);
        }

        final boolean hasOriginalValue = originalValues.containsKey(this);

        if (!hasOriginalValue || propModifyAction == PropModifyAction.CLEAR_MODIFY)
            originalValues.put(this, value);

        switch (propModifyAction) {

            case DEFAULT:
            case FORCE_MODIFY:
                if (hasOriginalValue || propModifyAction == PropModifyAction.FORCE_MODIFY) {
                    Map<PropModel, Object> modifiedValues = (Map<PropModel, Object>) Utils.getTag(view, R.id.PROP_MODIFIED_VALUES_TAG);

                    if (modifiedValues == null) {
                        modifiedValues = new HashMap<>();
                        Utils.setTag(view, R.id.PROP_MODIFIED_VALUES_TAG, modifiedValues);
                    }
                    modifiedValues.put(this, value);
                }
                break;

            case CLEAR_MODIFY: {
                Map<PropModel, Object> modifiedValues = (Map<PropModel, Object>) Utils.getTag(view, R.id.PROP_MODIFIED_VALUES_TAG);
                if (modifiedValues != null)
                    modifiedValues.remove(this);
            }
            break;
        }
    }

    public final void revertToOriginal(Object view) {
        Map<PropModel, Object> modifiedValues = (Map<PropModel, Object>) Utils.getTag(view, R.id.PROP_MODIFIED_VALUES_TAG);
        if (modifiedValues == null || !modifiedValues.containsKey(this)) return;

        modifiedValues.remove(this);

        Map<PropModel, Object> originalValues = (Map<PropModel, Object>) Utils.getTag(view, R.id.PROP_ORIGINAL_VALUE_TAG);
        if (originalValues == null || !originalValues.containsKey(this)) return;

        Object value = originalValues.get(this);
        doSetProp(view, value);
    }

    public interface PropIterator {
        Object NOT_SET = new Object();

        void processProp(PropModel propModel, Object originalValue, Object modifiedValue);
    }

    public interface PropRevertIterator {
        void afterRevert(Object view, PropModel model, Object value);
    }

    public static void iterateProps(Object view, PropIterator iterator) {
        Map<PropModel, Object> originalValues = (Map<PropModel, Object>) Utils.getTag(view, R.id.PROP_ORIGINAL_VALUE_TAG);
        if (originalValues == null) return;

        Map<PropModel, Object> modifiedValues = (Map<PropModel, Object>) Utils.getTag(view, R.id.PROP_MODIFIED_VALUES_TAG);

        for (Map.Entry<PropModel, Object> entry : originalValues.entrySet()) {
            PropModel propModel = entry.getKey();
            Object originalValue = entry.getValue();
            Object modifiedValue = modifiedValues == null || !modifiedValues.containsKey(propModel) ? PropIterator.NOT_SET : modifiedValues.get(propModel);
            iterator.processProp(propModel, originalValue, modifiedValue);
        }
    }

    public static void revertViewToOriginal(Object view, PropRevertIterator iterator) {
        Map<PropModel, Object> modifiedValues = (Map<PropModel, Object>) Utils.getTag(view, R.id.PROP_MODIFIED_VALUES_TAG);
        if (modifiedValues == null) return;

        Map<PropModel, Object> originalValues = (Map<PropModel, Object>) Utils.getTag(view, R.id.PROP_ORIGINAL_VALUE_TAG);
        if (originalValues == null) return;

        for (PropModel propModel : modifiedValues.keySet()) {
            if (!originalValues.containsKey(propModel))
                continue;

            Object currentValue = propModel.getProp(view);

            Object value = originalValues.get(propModel);
            propModel.doSetProp(view, value);

            iterator.afterRevert(view, propModel, currentValue);
        }

        modifiedValues.clear();
    }

    public static void revertViewHierarchyToOriginal(Object view, final PropRevertIterator iterator) {
        revertViewToOriginal(view, iterator);
        Utils.iterateChildren(view, new Utils.ViewChildrenIterator() {
            @Override
            public Object processChild(Object parent, Object child, int position) {
                revertViewHierarchyToOriginal(child, iterator);
                return null;
            }
        });
    }

    public static void clearModified(Object view) {
        Map<PropModel, Object> modifiedValues = (Map<PropModel, Object>) Utils.getTag(view, R.id.PROP_MODIFIED_VALUES_TAG);
        if (modifiedValues == null) return;

        Map<PropModel, Object> originalValues = (Map<PropModel, Object>) Utils.getTag(view, R.id.PROP_ORIGINAL_VALUE_TAG);
        if (originalValues == null) return;

        for (Map.Entry<PropModel, Object> entry : modifiedValues.entrySet())
            originalValues.put(entry.getKey(), entry.getValue());

        modifiedValues.clear();
    }

    public static void clearModifiedHierarchy(Object view) {
        clearModified(view);
        Utils.iterateChildren(view, new Utils.ViewChildrenIterator() {
            @Override
            public Object processChild(Object parent, Object child, int position) {
                clearModifiedHierarchy(child);
                return null;
            }
        });
    }

    public final boolean isPropModified(Object view) {
        Map<PropModel, Object> modifiedValues = (Map<PropModel, Object>) Utils.getTag(view, R.id.PROP_MODIFIED_VALUES_TAG);
        if (modifiedValues != null && modifiedValues.containsKey(this))
            return true;
        return false;
    }

    public abstract Object doGetProp(Object view);

    public abstract void doSetProp(Object view, Object value);

    public final int getPropAsInt(Object view) {
        Object value = getProp(view);
        return value != null && value instanceof Integer ? (Integer) value : 0;
    }

    public final Integer getPropAsInteger(Object view) {
        Object value = getProp(view);
        return value != null && value instanceof Integer ? (Integer) value : null;
    }

    public final Boolean getPropAsBoolean(Object view) {
        Object value = getProp(view);
        return value != null && value instanceof Boolean ? (Boolean) value : null;
    }

    public final String getPropAsString(Object view) {
        Object value = getProp(view);
        return value != null && value instanceof String ? (String) value : null;
    }

    public final <T> T getPropAs(Object view, Class<T> tClass) {
        Object value = getProp(view);
        return value == null || !tClass.isAssignableFrom(value.getClass()) ? null : (T) value;
    }
}