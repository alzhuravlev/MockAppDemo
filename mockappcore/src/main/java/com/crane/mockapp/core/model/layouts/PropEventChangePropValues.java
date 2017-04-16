package com.crane.mockapp.core.model.layouts;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ViewAnimator;

import com.crane.mockapp.core.ThemeUtils;
import com.crane.mockapp.core.Utils;
import com.crane.mockapp.core.model.theme.ThemeModelColor;
import com.crane.mockapp.core.model.theme.ThemeModel;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crane2002 on 1/28/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropEventChangePropValues extends PropEventValue {

    @JsonProperty("props")
    private List<PropEventChangePropValue> props;

    @JsonCreator
    public PropEventChangePropValues(@JsonProperty("props") List<PropEventChangePropValue> props) {
        this.props = props;
    }

    private Object findViewByPath(Object view, String path) {
        Object root = Utils.getRootView(view);
        return Utils.findViewByViewIdPath(root, path);
    }

    @Override
    public boolean isEmpty() {
        return props == null || props.size() == 0;
    }

    private Interpolator interpolator = new DecelerateInterpolator(3.0f);

    private void animateProp(final Context context, final PropEventChangePropValue prop, final Object view, final ThemeModel themeModel, final boolean designTime) {

        switch (prop.prop) {
            case PADDING_START:
            case PADDING_TOP:
            case PADDING_END:
            case PADDING_BOTTOM:
            case LAYOUT_MARGIN_START:
            case LAYOUT_MARGIN_TOP:
            case LAYOUT_MARGIN_END:
            case LAYOUT_MARGIN_BOTTOM: {

                int startPx = prop.prop.getPropAsInt(view);
                int endPx = Utils.dpToPx(context, (Integer) prop.value);

                if (prop.animate == null || prop.animate == PropEventChangePropValue.Anim.NONE) {
                    prop.prop.setProp(view, endPx);
                } else {
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(startPx, endPx);
                    valueAnimator.setDuration(200);
                    valueAnimator.setInterpolator(interpolator);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            prop.prop.setProp(view, animation.getAnimatedValue());
                        }
                    });
                    valueAnimator.start();

                }
            }
            break;

            case TEXT_SIZE:
                prop.prop.setProp(view, Utils.dpToPx(context, (Integer) prop.value));
                break;

            case ICON_SIZE: {

                int startPx = prop.prop.getPropAsInt(view);
                int endPx = Utils.dpToPx(context, (Integer) prop.value);

                if (prop.animate == null || prop.animate == PropEventChangePropValue.Anim.NONE) {
                    prop.prop.setProp(view, endPx);
                    ThemeUtils.applyThemeToView(context, designTime, null, view, themeModel, false);
                } else {
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(startPx, endPx);
                    valueAnimator.setDuration(250);
                    valueAnimator.setInterpolator(interpolator);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            prop.prop.setProp(view, animation.getAnimatedValue());
                            ThemeUtils.applyThemeToView(context, designTime, null, view, themeModel, false);
                        }
                    });
                    valueAnimator.start();
                }
            }
            break;

            case BACKGROUND_COLOR:
            case ICON_COLOR:
            case TEXT_COLOR: {

                if (prop.animate == null || prop.animate == PropEventChangePropValue.Anim.NONE) {
                    prop.prop.setProp(view, prop.value);
                    ThemeUtils.applyThemeToViewHierarchy(context, designTime, null, Utils.getRootView(view), themeModel, false);
                } else {

                    ThemeModelColor startThemeModelColor = prop.prop.getPropAs(view, ThemeModelColor.class);
                    ThemeModelColor endThemeModelColor = (ThemeModelColor) prop.value;

                    int startColor = ThemeModelColor.isRealColor(startThemeModelColor) ?
                            ThemeUtils.resolveColor(startThemeModelColor, themeModel) :
                            ThemeUtils.resolveColor(view, startThemeModelColor, themeModel);

                    int endColor = ThemeModelColor.isRealColor(endThemeModelColor) ?
                            ThemeUtils.resolveColor(endThemeModelColor, themeModel) :
                            ThemeUtils.resolveColor(view, endThemeModelColor, themeModel);

                    ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
                    valueAnimator.setDuration(250);
                    valueAnimator.setInterpolator(interpolator);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            prop.prop.setProp(view, animation.getAnimatedValue());
                            ThemeUtils.applyThemeToView(context, designTime, null, view, themeModel, false);
                        }
                    });
                    valueAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            prop.prop.setProp(view, prop.value);
                            ThemeUtils.applyThemeToViewHierarchy(context, designTime, null, Utils.getRootView(view), themeModel, false);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                    valueAnimator.start();
                }
            }
            break;

            case VISIBILITY: {

                boolean done = false;

                ViewAnimator viewAnimator = Utils.findViewAnimatorInParents(view);
                if (viewAnimator != null) {
                    int v = (Integer) prop.value;
                    if (v == View.VISIBLE) {
                        int idx = Utils.indexOfChild(view);
                        if (idx != -1 && viewAnimator.getDisplayedChild() != idx)
                            viewAnimator.setDisplayedChild(idx);
                        done = true;
                    }
                }

                ViewPager viewPager = Utils.findViewPagerInParents(view);
                if (viewPager != null) {
                    int v = (Integer) prop.value;
                    if (v == View.VISIBLE) {
                        int idx = Utils.indexOfChild(view);
                        if (idx != -1 && idx != viewPager.getCurrentItem())
                            viewPager.setCurrentItem(idx);
                        done = true;
                    }
                }

                if (!done)
                    if (prop.animate == null || prop.animate == PropEventChangePropValue.Anim.NONE) {
                        prop.prop.setProp(view, prop.value);
                        ThemeUtils.applyThemeToView(context, designTime, null, view, themeModel, false);
                    } else {

                        int start = prop.prop.getPropAsInt(view);
                        int end = (Integer) prop.value;

                        boolean needAnimate = false;

                        float startAlpha = 0.0f, endAlpha = 0.0f;

                        if (start == View.VISIBLE && (end == View.GONE || end == View.INVISIBLE)) {
                            needAnimate = true;
                            startAlpha = 1.0f;
                            endAlpha = 0.0f;
                        } else if (end == View.VISIBLE && (start == View.GONE || start == View.INVISIBLE)) {
                            needAnimate = true;
                            startAlpha = 0.0f;
                            endAlpha = 1.0f;
                        }

                        if (needAnimate) {
                            ValueAnimator valueAnimator = ValueAnimator.ofFloat(startAlpha, endAlpha);
                            valueAnimator.setDuration(350);
                            valueAnimator.setInterpolator(interpolator);
                            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    Utils.setAlpha(view, (float) animation.getAnimatedValue());
                                }
                            });
                            valueAnimator.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    prop.prop.setProp(view, View.VISIBLE);
                                    ThemeUtils.applyThemeToView(context, designTime, null, view, themeModel, false);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    prop.prop.setProp(view, prop.value);
                                    ThemeUtils.applyThemeToView(context, designTime, null, view, themeModel, false);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }
                            });
                            valueAnimator.start();
                        } else {
                            prop.prop.setProp(view, prop.value);
                            ThemeUtils.applyThemeToView(context, designTime, null, view, themeModel, false);
                        }
                    }
            }
            break;

        }
    }

    @Override
    protected void doExecute(Context context, Object view, ThemeModel themeModel) {
        if (props == null)
            return;

        for (PropEventChangePropValue prop : props) {
            if (prop.prop == null)
                continue;
            Object targetView = findViewByPath(view, prop.viewIdPath);
            if (targetView != null)
                animateProp(context, prop, targetView, themeModel, false);
        }
    }

    @Override
    public PropEventChangePropValues clone() {
        List<PropEventChangePropValue> props = null;
        if (this.props != null) {
            props = new ArrayList<>();
            for (PropEventChangePropValue propValue : this.props)
                props.add(propValue.clone());
        }
        return new PropEventChangePropValues(props);
    }

    public void addProp(PropEventChangePropValue propValue) {
        if (props == null) props = new ArrayList<>();
        props.add(propValue);
    }

    public List<PropEventChangePropValue> getProps() {
        return props;
    }
}
