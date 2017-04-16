package com.crane.mockapp.core.model.layouts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.crane.mockapp.core.ImageProvider;
import com.crane.mockapp.core.R;
import com.crane.mockapp.core.ThemeUtils;
import com.crane.mockapp.core.Utils;
import com.crane.mockapp.core.ViewPagerAdapter;
import com.crane.mockapp.core.OperationCallback;
import com.crane.mockapp.core.model.theme.ThemeModel;
import com.crane.mockapp.core.model.theme.ThemeModelServiceFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by crane2002 on 1/15/2017.
 */

public class LayoutScreenshoter {

    public static View inflateForScreenshot(Context context, LayoutDescriptor layoutDescriptor, ImageProvider imageProvider) {
        if (layoutDescriptor == null || layoutDescriptor.getLayout() == null)
            return null;

        FrameLayout group = new FrameLayout(context);

        Object viewObject = LayoutInflater.inflate(context, layoutDescriptor.getLayout(), group, true);
        if (viewObject == null || !(viewObject instanceof View))
            return null;

        final int w = context.getResources().getDisplayMetrics().widthPixels;
        final int h = context.getResources().getDisplayMetrics().heightPixels;

        ThemeModel themeModel = ThemeModelServiceFactory.getInstace(context).buildTheme(layoutDescriptor.getPrimaryThemeId(), layoutDescriptor.getAccentThemeId());

        // this may trigger some layouts, so call it before global "layout"
        ThemeUtils.applyThemeToViewHierarchy(context, false, imageProvider, viewObject, themeModel, false);

        if (layoutDescriptor.isFullScreen())
            group.measure(View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(h, View.MeasureSpec.EXACTLY));
        else {
            View view = (View) viewObject;
            if (view.getLayoutParams() != null && view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            }
            group.measure(View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(h, View.MeasureSpec.AT_MOST));
        }

        group.layout(0, 0, group.getMeasuredWidth(), group.getMeasuredHeight());

        return group;
    }

    public static Bitmap screenshot(Context context, LayoutDescriptor layoutDescriptor, ImageProvider imageProvider) {
        final ResultHolder<Bitmap> holder = new ResultHolder<>();
        screenshot(context, layoutDescriptor, imageProvider, new OperationCallback<Bitmap>() {
            @Override
            public void onSuccess(Bitmap result) {
                holder.result = result;
            }

            @Override
            public void onFail(String message) {
            }
        });
        return holder.result;
    }

    public static Bitmap screenshot(Context context, Object view) {
        final ResultHolder<Bitmap> holder = new ResultHolder<>();
        screenshot(context, view, new OperationCallback<Bitmap>() {
            @Override
            public void onSuccess(Bitmap result) {
                holder.result = result;
            }

            @Override
            public void onFail(String message) {
            }
        });
        return holder.result;
    }

    public static void screenshot(Context context, LayoutDescriptor layoutDescriptor, ImageProvider imageProvider, final OperationCallback<Bitmap> callback) {
        View root = inflateForScreenshot(context, layoutDescriptor, imageProvider);
        screenshot(context, root, callback);
    }

    public static void screenshot(Context context, Object viewObject, final OperationCallback<Bitmap> callback) {

        if (viewObject == null || !(viewObject instanceof View)) {
            callback.onFail(context.getString(R.string.error_unable_to_make_screenshot));
            return;
        }

        View view = (View) viewObject;

        if (view.getWidth() == 0 || view.getHeight() == 0) {
            callback.onFail(context.getString(R.string.error_unable_to_make_screenshot));
            return;
        }

//        prepareOutline(root);

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        screenshotViewPagerPage(canvas, view, view);

        callback.onSuccess(bitmap);
    }

    private static void screenshotViewPagerPage(final Canvas canvas, final Object root, final Object view) {
        if (view instanceof ViewPager) {
            ViewPager viewPager = (ViewPager) view;
            ViewPagerAdapter viewPagerAdapter = (ViewPagerAdapter) viewPager.getAdapter();
            if (viewPagerAdapter != null) {
                View pageView = viewPagerAdapter.getViewAt(viewPager.getCurrentItem());
                pageView.measure(View.MeasureSpec.makeMeasureSpec(viewPager.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(viewPager.getHeight(), View.MeasureSpec.EXACTLY));
                pageView.layout(0, 0, pageView.getMeasuredWidth(), pageView.getMeasuredHeight());

                Rect rect = new Rect();
                Utils.getRelativeRect(root, viewPager, rect);

                canvas.save();
                {
                    canvas.translate(rect.left, rect.top);
                    pageView.draw(canvas);
                }
                canvas.restore();
            }
        }

        Utils.iterateChildren(view, new Utils.ViewChildrenIterator() {
            @Override
            public Object processChild(Object parent, Object child, int position) {
                screenshotViewPagerPage(canvas, root, child);
                return null;
            }
        });
    }

    private static void prepareOutline(final Object view) {

        if (view instanceof View) {
            View v = (View) view;
            if (v.getOutlineProvider() != null) {
                Field field = null;
                try {
                    field = View.class.getDeclaredField("mRenderNode");
                    field.setAccessible(true);
                    Object value = field.get(v);
                    if (value != null) {
                        Method method = value.getClass().getDeclaredMethod("setOutline", Outline.class);
                        method.setAccessible(true);
                        Outline outline = new Outline();
                        v.getOutlineProvider().getOutline(v, outline);
                        method.invoke(value, outline);

                        method = value.getClass().getDeclaredMethod("setClipToOutline", boolean.class);
                        method.setAccessible(true);
                        method.invoke(value, true);

                        v.invalidateOutline();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Utils.iterateChildren(view, new Utils.ViewChildrenIterator() {
            @Override
            public Object processChild(Object parent, Object child, int position) {
                prepareOutline(child);
                return null;
            }
        });
    }
}
