package com.crane.mockapp.core.model.layouts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crane.mockapp.core.ImageProvider;
import com.crane.mockapp.core.R;
import com.crane.mockapp.core.ThemeUtils;
import com.crane.mockapp.core.Utils;
import com.crane.mockapp.core.model.props.PropModel;
import com.crane.mockapp.core.model.theme.ThemeModel;
import com.crane.mockapp.core.model.theme.ThemeModelColor;
import com.crane.mockapp.core.model.theme.ThemeModelServiceFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by crane2002 on 3/18/2017.
 */

public class PdfLayoutGenerator implements ImageProvider {
    private Context context;
    private String projectId;
    private String layoutId;

    private ThemeModel themeModel;

    private List<PageGenerator> pageGenerators = new ArrayList<>();

    public PdfLayoutGenerator(Context context, String projectId, String layoutId) {
        this.context = context;
        this.projectId = projectId;
        this.layoutId = layoutId;
    }

    public void generate(OutputStream outputStream) {

        LayoutDescriptor layoutDescriptor = ProjectServiceFactory.getInstace(context).loadLayout(projectId, layoutId);

        Object root = LayoutScreenshoter.inflateForScreenshot(context, layoutDescriptor, this);
        if (root == null)
            return;

        themeModel = ThemeModelServiceFactory.getInstace(context).buildTheme(layoutDescriptor.getPrimaryThemeId(), layoutDescriptor.getAccentThemeId());

        PdfDocument document = new PdfDocument();

        pageGenerators.add(new PageGenerator(document, root, formatTitle(projectId, layoutId)));
        collectPages(document, root, root);
        addNavDrawer(document, layoutDescriptor);

        int pageIndex = 1;
        while (pageGenerators.size() > 0) {
            PageGenerator pageGenerator = pageGenerators.remove(0);
            pageGenerator.generatePage(pageIndex);
            pageIndex++;
        }

        try {
            document.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private void addNavDrawer(PdfDocument document, LayoutDescriptor layoutDescriptor) {
        PropLayoutValue propLayoutValue = layoutDescriptor.getNavDrawerLayout();
        if (propLayoutValue == null)
            return;

        LayoutDescriptor navDrawerLayoutDescriptor = ProjectServiceFactory.getInstace(context).loadLayout(propLayoutValue.getProjectId(), propLayoutValue.getLayoutId());
        if (navDrawerLayoutDescriptor == null || navDrawerLayoutDescriptor.getLayout() == null)
            return;

        Object root = LayoutScreenshoter.inflateForScreenshot(context, navDrawerLayoutDescriptor, this);
        if (root == null)
            return;

        pageGenerators.add(new PageGenerator(document, root, formatTitle(propLayoutValue.getProjectId(), propLayoutValue.getLayoutId())));
        collectPages(document, root, root);
    }

    private String formatTitle(String projectId, String layoutId) {
        String projectName = ProjectServiceFactory.getInstace(context).formatProjectName(projectId);
        String layoutName = ProjectServiceFactory.getInstace(context).formatLayoutName(projectId, layoutId);
        return projectName + ". " + layoutName;
    }

    private void collectPages(final PdfDocument document, final Object root, Object view) {
        if (root != view) {
            PropLayoutValue layoutValue = PropModel.CUSTOM_LAYOUT.getPropAs(view, PropLayoutValue.class);
            if (layoutValue != null)
                pageGenerators.add(new PageGenerator(document, view, formatTitle(layoutValue.getProjectId(), layoutValue.getLayoutId())));
        }

        Utils.iterateChildren(view, new Utils.ViewChildrenIterator() {
            @Override
            public Object processChild(Object parent, Object child, int position) {
                collectPages(document, root, child);
                return null;
            }
        });
    }

    @Override
    public Bitmap loadImage(String imageFileName, int reqWidth, int reqHeight) {
        return ProjectServiceFactory.getInstace(context).loadImage(projectId, imageFileName, reqWidth, reqHeight);
    }

    private class PageGenerator {
        Rect viewRect = new Rect();
        float viewScaleFactor;

        Rect titleRect = new Rect();

        int pageWidth;
        int pageHeight;
        int levelOffset;
        int maxLevel;
        int lineOverOffset;
        int textOnLineOffset;
        int metricTextSize;
        int titleTextSize;
        int referenceGapSize;
        int referenceTextSize;
        int referencePadding;
        int referenceBgPadding;
        int referenceBadgeTextSize;
        int referenceBadgePadding;

        Rect tmpRect = new Rect();
        Rect tmpRect2 = new Rect();
        Paint tmpPaint = new Paint();

        final int sizeColor = 0xff333333;
        final int paddingColor = 0xff0000ff;
        final int marginColor = 0xffff0000;

        final int referenceBadgeBgColor = 0x99ffff00;
        final int referenceBadgeFgColor = 0xde333333;

        List<Metric> leftMetrics = new ArrayList<>();
        List<Metric> rightMetrics = new ArrayList<>();
        List<Metric> topMetrics = new ArrayList<>();
        List<Metric> bottomMetrics = new ArrayList<>();

        List<Reference> references = new ArrayList<>();

        PdfDocument document;
        Object view;
        String title;

        PageGenerator(PdfDocument document, Object view, String title) {
            this.document = document;
            this.view = view;
            this.title = title;
        }

        void generatePage(int pageIndex) {

            Bitmap bitmap = LayoutScreenshoter.screenshot(context, view);
            if (bitmap == null)
                return;

            // A4 = 8.27 x 11.69 inches
            // A4 = 595 x 842

            final float scalePage = 2.0f;

            pageWidth = (int) (595 * scalePage);
            pageHeight = (int) (842 * scalePage);

            maxLevel = 5;

            levelOffset = (int) (20.0f * scalePage);
            lineOverOffset = (int) (1.2f * scalePage);
            textOnLineOffset = (int) (2.0f * scalePage);
            metricTextSize = (int) (5.0f * scalePage);
            titleTextSize = (int) (20.0f * scalePage);
            referenceTextSize = (int) (8.0f * scalePage);
            referencePadding = (int) (1.5f * scalePage);
            referenceBgPadding = (int) (0.5f * scalePage);
            referenceBadgeTextSize = (int) (8.0f * scalePage);
            referenceBadgePadding = (int) (0.6f * scalePage);
            referenceGapSize = (int) (8.0f * scalePage);

            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageIndex).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            Canvas canvas = page.getCanvas();

            drawTitle(canvas);

            drawView(canvas, bitmap);

            collectMetrics(view, view);
            processMetrics(leftMetrics, true);
            processMetrics(rightMetrics, true);
            processMetrics(topMetrics, false);
            processMetrics(bottomMetrics, false);
            drawMetrics(canvas);

            collectReferences(view, view);
            drawReferences(canvas);

            document.finishPage(page);

        }

        private void drawTitle(Canvas canvas) {
            Paint paint = new Paint();
            paint.setTextSize(titleTextSize);
            paint.setColor(0xff333333);

            int textW = (int) paint.measureText(title);

            Paint.FontMetrics fm = paint.getFontMetrics();

            titleRect.left = (pageWidth - textW) / 2;
            titleRect.right = viewRect.left + textW;
            titleRect.top = 24;
            titleRect.bottom = viewRect.top + (int) (fm.bottom - fm.top);

            canvas.drawText(title, titleRect.left, titleRect.bottom, paint);
        }

        private void drawView(Canvas canvas, Bitmap bitmap) {
            final float rootRatio = (float) bitmap.getWidth() / bitmap.getHeight();
            final int viewSize = (int) (pageWidth * 0.6f);
            int rootW, rootH;
            if (rootRatio > 1.0f) {
                rootW = viewSize;
                rootH = (int) (viewSize / rootRatio);
            } else {
                rootW = (int) (viewSize * rootRatio);
                rootH = viewSize;
            }

            viewScaleFactor = (float) rootW / bitmap.getWidth();

            viewRect.left = (pageWidth - rootW) / 2;
            viewRect.top = titleRect.bottom + (maxLevel + 1) * levelOffset;
            viewRect.right = viewRect.left + rootW;
            viewRect.bottom = viewRect.top + rootH;

            Rect rectRootShadow = new Rect();
            rectRootShadow.set(viewRect);
            rectRootShadow.inset(-6, -6);

            Paint shadowPaint = new Paint();
            shadowPaint.setColor(0x22555555);

            Paint bgPaint = new Paint();
            bgPaint.setColor(0xfffafafa);

            canvas.drawRect(rectRootShadow, shadowPaint);
            canvas.drawRect(viewRect, bgPaint);
            canvas.drawBitmap(bitmap, null, viewRect, bgPaint);
        }

        private void drawReferences(Canvas canvas) {
            int x = 48;
            int y = viewRect.bottom + (maxLevel + 1) * levelOffset;

            int index = 1;

            Paint.FontMetrics textFontMetrics = new Paint.FontMetrics();
            tmpPaint.setTextSize(referenceTextSize);
            tmpPaint.getFontMetrics(textFontMetrics);

            Paint.FontMetrics badgeFontMetrics = new Paint.FontMetrics();
            tmpPaint.setTextSize(referenceBadgeTextSize);
            tmpPaint.getFontMetrics(badgeFontMetrics);

            for (Reference reference : references) {
                y += reference.draw(canvas, x, y, index, textFontMetrics, badgeFontMetrics, tmpPaint);
                index++;
            }
        }

        private void collectReferences(final Object rootObject, Object viewObject) {

            if (Utils.getVisibility(viewObject) != View.VISIBLE)
                return;

            if (rootObject != viewObject && Utils.hasCustomLayout(viewObject))
                return;

            Reference reference = null;

            Utils.getRelativeRect(rootObject, viewObject, tmpRect);
            tmpRect.left *= viewScaleFactor;
            tmpRect.right *= viewScaleFactor;
            tmpRect.top *= viewScaleFactor;
            tmpRect.bottom *= viewScaleFactor;
            tmpRect.offset(viewRect.left, viewRect.top);

            if (viewObject instanceof View) {

                View view = (View) viewObject;

                String backImage = PropModel.BACKGROUND_IMAGE.getPropAsString(viewObject);
                if (backImage == null) {
                    ThemeModelColor backThemeModelColor = PropModel.BACKGROUND_COLOR.getPropAs(viewObject, ThemeModelColor.class);

                    if (backThemeModelColor != null && backThemeModelColor != ThemeModelColor.TRANSPARENT) {
                        reference = new Reference(tmpRect);
                        reference.backColor = ThemeUtils.resolveColor(viewObject, backThemeModelColor, themeModel);
                        reference.backFgColor = themeModel.getColor(ThemeModelColor.getRealColor(ThemeModelColor.TEXT_PRIMARY, themeModel.isForegroundLight(backThemeModelColor)));
                        reference.backThemeModelColor = backThemeModelColor;
                        reference.backAlpha = PropModel.BACKGROUND_ALPHA.getPropAsInt(viewObject);
                    }
                }
            }

            if (viewObject instanceof TextView) {
                ThemeModelColor textThemeModelColor = PropModel.TEXT_COLOR.getPropAs(viewObject, ThemeModelColor.class);

                if (textThemeModelColor != null)
                    switch (textThemeModelColor) {
                        case RIPPLE:
                        case TRANSPARENT:
                            break;

                        default: {
                            if (reference == null)
                                reference = new Reference(tmpRect);

                            reference.textThemeModelColor = textThemeModelColor;
                            reference.textColor = ThemeUtils.resolveColor(viewObject, textThemeModelColor, themeModel);
                            reference.textSizePx = PropModel.TEXT_SIZE.getPropAsInt(viewObject);
                        }
                        break;
                    }
            }

            if (viewObject instanceof ImageView) {
                if (PropModel.ICON_DRAWABLE.getPropAsString(viewObject) != null) {

                    if (reference == null)
                        reference = new Reference(tmpRect);

                    reference.iconSizePx = PropModel.ICON_SIZE.getPropAsInt(viewObject);
                    reference.iconThemeModelColor = PropModel.ICON_COLOR.getPropAs(viewObject, ThemeModelColor.class);
                    reference.iconColor = ThemeUtils.resolveColor(viewObject, reference.iconThemeModelColor, themeModel);

                    reference.iconContourThemeModelColor = PropModel.ICON_CONTOUR_COLOR.getPropAs(viewObject, ThemeModelColor.class);
                    reference.iconContourColor = ThemeUtils.resolveColor(viewObject, reference.iconContourThemeModelColor, themeModel);
                    reference.iconContourWidthPx = PropModel.ICON_CONTOUR_SIZE.getPropAsInt(viewObject);
                }
            }

            if (reference != null)
                references.add(reference);

            Utils.iterateChildren(viewObject, new Utils.ViewChildrenIterator() {
                @Override
                public Object processChild(Object parent, Object child, int position) {
                    if (parent instanceof ViewPager) {
                        ViewPager viewPager = (ViewPager) parent;
                        if (viewPager.getCurrentItem() == position)
                            collectReferences(rootObject, child);
                    } else if (child instanceof View)
                        collectReferences(rootObject, child);
                    return null;
                }
            });
        }

        private void collectMetrics(final Object rootObject, Object viewObject) {

            if (Utils.getVisibility(viewObject) != View.VISIBLE)
                return;

            if (rootObject != viewObject && Utils.hasCustomLayout(viewObject))
                return;

            int width = PropModel.LAYOUT_WIDTH.getPropAsInt(viewObject);
            int height = PropModel.LAYOUT_HEIGHT.getPropAsInt(viewObject);

            int paddingLeft = 0;
            int paddingTop = 0;
            int paddingRight = 0;
            int paddingBottom = 0;

            if (!(viewObject instanceof EditText)) {
                paddingLeft = PropModel.PADDING_START.getPropAsInt(viewObject);
                paddingTop = PropModel.PADDING_TOP.getPropAsInt(viewObject);
                paddingRight = PropModel.PADDING_END.getPropAsInt(viewObject);
                paddingBottom = PropModel.PADDING_BOTTOM.getPropAsInt(viewObject);
            }

            int marginLeft = PropModel.LAYOUT_MARGIN_START.getPropAsInt(viewObject);
            int marginTop = PropModel.LAYOUT_MARGIN_TOP.getPropAsInt(viewObject);
            int marginRight = PropModel.LAYOUT_MARGIN_END.getPropAsInt(viewObject);
            int marginBottom = PropModel.LAYOUT_MARGIN_BOTTOM.getPropAsInt(viewObject);

            Utils.getRelativeRect(rootObject, viewObject, tmpRect);
            tmpRect.left *= viewScaleFactor;
            tmpRect.right *= viewScaleFactor;
            tmpRect.top *= viewScaleFactor;
            tmpRect.bottom *= viewScaleFactor;
            tmpRect.offset(viewRect.left, viewRect.top);

            if (width > 0)
                addHorMetric(tmpRect, sizeColor, String.valueOf(Utils.pxToDp(context, width)));

            if (height > 0)
                addVertMetric(tmpRect, sizeColor, String.valueOf(Utils.pxToDp(context, height)));

            if (paddingLeft > 0) {
                tmpRect2.set(tmpRect);
                tmpRect2.right = tmpRect2.left + (int) (paddingLeft * viewScaleFactor);
                addHorMetric(tmpRect2, paddingColor, String.valueOf(Utils.pxToDp(context, paddingLeft)));
            }

            if (paddingTop > 0) {
                tmpRect2.set(tmpRect);
                tmpRect2.bottom = tmpRect2.top + (int) (paddingTop * viewScaleFactor);
                addVertMetric(tmpRect2, paddingColor, String.valueOf(Utils.pxToDp(context, paddingTop)));
            }

            if (paddingRight > 0) {
                tmpRect2.set(tmpRect);
                tmpRect2.left = tmpRect2.right - (int) (paddingRight * viewScaleFactor);
                addHorMetric(tmpRect2, paddingColor, String.valueOf(Utils.pxToDp(context, paddingRight)));
            }

            if (paddingBottom > 0) {
                tmpRect2.set(tmpRect);
                tmpRect2.top = tmpRect2.bottom - (int) (paddingBottom * viewScaleFactor);
                addVertMetric(tmpRect2, paddingColor, String.valueOf(Utils.pxToDp(context, paddingBottom)));
            }

            if (marginLeft > 0) {
                tmpRect2.set(tmpRect);
                tmpRect2.right = tmpRect2.left;
                tmpRect2.left = tmpRect2.left - (int) (marginLeft * viewScaleFactor);
                addHorMetric(tmpRect2, marginColor, String.valueOf(Utils.pxToDp(context, marginLeft)));
            }

            if (marginTop > 0) {
                tmpRect2.set(tmpRect);
                tmpRect2.bottom = tmpRect2.top;
                tmpRect2.top = tmpRect2.top - (int) (marginTop * viewScaleFactor);
                addVertMetric(tmpRect2, marginColor, String.valueOf(Utils.pxToDp(context, marginTop)));
            }

            if (marginRight > 0) {
                tmpRect2.set(tmpRect);
                tmpRect2.left = tmpRect2.right;
                tmpRect2.right = tmpRect2.right + (int) (marginRight * viewScaleFactor);
                addHorMetric(tmpRect2, marginColor, String.valueOf(Utils.pxToDp(context, marginRight)));
            }

            if (marginBottom > 0) {
                tmpRect2.set(tmpRect);
                tmpRect2.top = tmpRect2.bottom;
                tmpRect2.bottom = tmpRect2.bottom + (int) (marginBottom * viewScaleFactor);
                addVertMetric(tmpRect2, marginColor, String.valueOf(Utils.pxToDp(context, marginBottom)));
            }

            Utils.iterateChildren(viewObject, new Utils.ViewChildrenIterator() {
                @Override
                public Object processChild(Object parent, Object child, int position) {
                    if (parent instanceof ViewPager) {
                        ViewPager viewPager = (ViewPager) parent;
                        if (viewPager.getCurrentItem() == position)
                            collectMetrics(rootObject, child);
                    } else if (child instanceof View)
                        collectMetrics(rootObject, child);
                    return null;
                }
            });
        }

        private void processMetrics(List<Metric> metrics, boolean vert) {

            int c = vert ? viewRect.centerX() : viewRect.centerY();

            // 1. delete duplicates

            List<Metric> result = new ArrayList<>();
            for (Metric metric : metrics) {
                Metric candidate = findMetric(result, metric.v1, metric.v2);
                if (candidate == null)
                    result.add(metric);
                else {
                    int d1 = Math.abs(metric.start - c);
                    int d2 = Math.abs(candidate.start - c);
                    if (d1 < d2) {
                        candidate.start = metric.start;
                        if (candidate.color == sizeColor)
                            candidate.color = metric.color;
                    }
                }
            }

            metrics.clear();
            metrics.addAll(result);

            // 2. sort

            Collections.sort(metrics, new Comparator<Metric>() {
                @Override
                public int compare(Metric o1, Metric o2) {
                    int l1 = Math.abs(o1.v1 - o1.v2);
                    int l2 = Math.abs(o2.v1 - o2.v2);
                    return Integer.compare(l1, l2);
                }
            });

            // 3. inc levels

            for (int i = 0; i < metrics.size(); i++) {
                Metric metric = metrics.get(i);
                metric.level = Math.min(maxLevel, metric.getMaxLevel(metrics, 0, i - 1) + 1);
            }
        }

        private Metric findMetric(List<Metric> metrics, int v1, int v2) {
            for (Metric metric : metrics)
                if (metric.v1 == v1 && metric.v2 == v2)
                    return metric;
            return null;
        }

        private class Reference {
            Rect sourceRect;

            ThemeModelColor backThemeModelColor;
            int backFgColor;
            int backColor;
            int backAlpha;

            ThemeModelColor textThemeModelColor;
            int textColor;
            int textSizePx;

            ThemeModelColor iconThemeModelColor;
            int iconColor;
            int iconSizePx;
            ThemeModelColor iconContourThemeModelColor;
            int iconContourColor;
            int iconContourWidthPx;

            Reference(Rect sourceRect) {
                this.sourceRect = new Rect(sourceRect);
            }

            int draw(Canvas canvas, int x, int y, int index, Paint.FontMetrics textFontMetrics, Paint.FontMetrics badgeFontMetrics, Paint paint) {

                final int height = referencePadding * 2 + (int) (textFontMetrics.bottom - textFontMetrics.top);
                final int badgeHeight = referenceBadgePadding * 2 + (int) (badgeFontMetrics.bottom - badgeFontMetrics.top);

                paint.setTextSize(referenceBadgeTextSize);

                String badgeText = String.valueOf(index);
                int badgeWidth = referenceBadgePadding * 2 + (int) paint.measureText(badgeText);

                paint.setColor(referenceBadgeBgColor);
                paint.setStyle(Paint.Style.FILL);
                float badgeRadius = badgeHeight * 0.2f;
                canvas.drawRoundRect(x, y + (height - badgeHeight) / 2, x + badgeWidth, y + height - (height - badgeHeight) / 2, badgeRadius, badgeRadius, paint);
                canvas.drawRoundRect(sourceRect.left, sourceRect.top, sourceRect.left + badgeWidth, sourceRect.top + badgeHeight, badgeRadius, badgeRadius, paint);

                paint.setColor(referenceBadgeFgColor);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText(badgeText, x + referenceBadgePadding, y + height - (height - badgeHeight) / 2 - referenceBadgePadding - badgeFontMetrics.bottom, paint);
                canvas.drawText(badgeText, sourceRect.left + referenceBadgePadding, sourceRect.top + badgeHeight - referenceBadgePadding - badgeFontMetrics.bottom, paint);

                x += badgeWidth + referenceGapSize;

                if (backThemeModelColor != null) {
                    String backText = context.getString(R.string.pdf_backgound_text, backThemeModelColor.name(), backColor, backAlpha);

                    paint.setTextSize(referenceTextSize);

                    int backW = (int) paint.measureText(backText);

                    paint.setColor(backColor == 0 || backColor == 0xffffffff ? 0xff333333 : backColor);
                    paint.setStyle(backColor == 0 || backColor == 0xffffffff ? Paint.Style.STROKE : Paint.Style.FILL);
                    paint.setStrokeWidth(1);
                    canvas.drawRect(x, y + referenceBgPadding, x + referenceGapSize * 2, y + height - referenceBgPadding, paint);

                    x += referenceGapSize * 2 + referenceGapSize;

                    paint.setColor(0xff333333);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawText(backText, x, y + height - referencePadding - textFontMetrics.bottom, paint);

                    x += backW + referenceGapSize;
                }

                if (textThemeModelColor != null) {
                    String text = context.getString(R.string.pdf_text_text, Utils.pxToDp(context, textSizePx), textThemeModelColor.name(), textColor);

                    paint.setTextSize(referenceTextSize);

                    int textW = (int) paint.measureText(text);

                    paint.setColor(0xff333333);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawText(text, x, y + height - referencePadding - textFontMetrics.bottom, paint);

                    x += textW + referenceGapSize;
                }

                if (iconThemeModelColor != null) {
                    String text = context.getString(R.string.pdf_icon_text, Utils.pxToDp(context, iconSizePx), iconThemeModelColor.name(), iconColor);

                    paint.setTextSize(referenceTextSize);

                    int textW = (int) paint.measureText(text);

                    paint.setColor(0xff333333);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawText(text, x, y + height - referencePadding - textFontMetrics.bottom, paint);

                    x += textW + referenceGapSize;
                }

                if (iconContourThemeModelColor != null) {
                    String text = context.getString(R.string.pdf_icon_contour_text, Utils.pxToDp(context, iconContourWidthPx), iconContourThemeModelColor.name(), iconContourColor);

                    paint.setTextSize(referenceTextSize);

                    int textW = (int) paint.measureText(text);

                    paint.setColor(0xff333333);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawText(text, x, y + height - referencePadding - textFontMetrics.bottom, paint);

                    x += textW + referenceGapSize;
                }

                return height;
            }
        }

        private class Metric {
            int v1, v2;
            int start;
            int color;
            String text;
            int level;

            Metric(int v1, int v2, int start, int color, String text) {
                this.v1 = v1;
                this.v2 = v2;
                this.start = start;
                this.color = color;
                this.text = text;
            }

            int getMaxLevel(List<Metric> metrics, int start, int end) {
                int level = 0;
                for (int i = start; i >= 0 && i < metrics.size() && i <= end; i++) {
                    Metric metric = metrics.get(i);
                    if (intersectsWith(metric) && level < metric.level)
                        level = metric.level;
                }
                return level;
            }

            boolean intersectsWith(Metric metric) {
                int min1 = Math.min(v1, v2);
                int max1 = Math.max(v1, v2);
                int min2 = Math.min(metric.v1, metric.v2);
                int max2 = Math.max(metric.v1, metric.v2);
                return min1 < max2 && min2 < max1;
            }
        }

        private void addVertMetric(Rect rect, int color, String text) {
            int cx = viewRect.centerX();
            if (rect.left < cx && rect.right < cx)
                leftMetrics.add(new Metric(rect.top, rect.bottom, rect.left, color, text));
            else if (rect.left > cx && rect.right > cx)
                rightMetrics.add(new Metric(rect.top, rect.bottom, rect.right, color, text));
            else if (leftMetrics.size() < rightMetrics.size())
                leftMetrics.add(new Metric(rect.top, rect.bottom, rect.left, color, text));
            else
                rightMetrics.add(new Metric(rect.top, rect.bottom, rect.right, color, text));
        }

        private void addHorMetric(Rect rect, int color, String text) {
            int cy = viewRect.centerY();
            if (rect.top < cy && rect.bottom < cy)
                topMetrics.add(new Metric(rect.left, rect.right, rect.top, color, text));
            else if (rect.top > cy && rect.bottom > cy)
                bottomMetrics.add(new Metric(rect.left, rect.right, rect.bottom, color, text));
            else if (topMetrics.size() < bottomMetrics.size())
                topMetrics.add(new Metric(rect.left, rect.right, rect.top, color, text));
            else
                bottomMetrics.add(new Metric(rect.left, rect.right, rect.bottom, color, text));
        }

        private void drawMetrics(Canvas canvas) {
            for (Metric metric : leftMetrics)
                drawLeftMetric(canvas, metric);
            for (Metric metric : rightMetrics)
                drawRightMetric(canvas, metric);
            for (Metric metric : topMetrics)
                drawTopMetric(canvas, metric);
            for (Metric metric : bottomMetrics)
                drawBottomMetric(canvas, metric);
        }

        private void drawLeftMetric(Canvas canvas, Metric metric) {
            int startX = viewRect.left - levelOffset * metric.level;
            int endX = metric.start;
            int y1 = metric.v1;
            int y2 = metric.v2;

            tmpPaint.setColor(metric.color);
            tmpPaint.setStrokeWidth(1);

            canvas.drawLine(startX - lineOverOffset, y1, endX + lineOverOffset, y1, tmpPaint);
            canvas.drawLine(startX - lineOverOffset, y2, endX + lineOverOffset, y2, tmpPaint);
            canvas.drawLine(startX, y1 - lineOverOffset, startX, y2 + lineOverOffset, tmpPaint);

            tmpPaint.setTextSize(metricTextSize);
            int textW = (int) tmpPaint.measureText(metric.text);

            int textX = startX - textOnLineOffset;
            int textY = ((y1 + y2) >> 1) + textW / 2;

            canvas.save();
            canvas.rotate(-90f, textX, textY);
            canvas.drawText(metric.text, textX, textY, tmpPaint);
            canvas.restore();
        }

        private void drawRightMetric(Canvas canvas, Metric metric) {
            int startX = metric.start;
            int endX = viewRect.right + levelOffset * metric.level;
            int y1 = metric.v1;
            int y2 = metric.v2;

            tmpPaint.setColor(metric.color);
            tmpPaint.setStrokeWidth(1);

            canvas.drawLine(startX - lineOverOffset, y1, endX + lineOverOffset, y1, tmpPaint);
            canvas.drawLine(startX - lineOverOffset, y2, endX + lineOverOffset, y2, tmpPaint);
            canvas.drawLine(endX, y1 - lineOverOffset, endX, y2 + lineOverOffset, tmpPaint);

            tmpPaint.setTextSize(metricTextSize);
            int textW = (int) tmpPaint.measureText(metric.text);

            int textX = endX + textOnLineOffset;
            int textY = ((y1 + y2) >> 1) - textW / 2;

            canvas.save();
            canvas.rotate(90f, textX, textY);
            canvas.drawText(metric.text, textX, textY, tmpPaint);
            canvas.restore();
        }

        private void drawTopMetric(Canvas canvas, Metric metric) {
            int startY = viewRect.top - levelOffset * metric.level;
            int endY = metric.start;
            int x1 = metric.v1;
            int x2 = metric.v2;

            tmpPaint.setColor(metric.color);
            tmpPaint.setStrokeWidth(1);

            canvas.drawLine(x1, startY - lineOverOffset, x1, endY, tmpPaint);
            canvas.drawLine(x2, startY - lineOverOffset, x2, endY, tmpPaint);
            canvas.drawLine(x1 - lineOverOffset, startY, x2 + lineOverOffset, startY, tmpPaint);

            tmpPaint.setTextSize(metricTextSize);
            int textW = (int) tmpPaint.measureText(metric.text);

            int textX = ((x1 + x2) >> 1) - textW / 2;
            int textY = startY - textOnLineOffset;

            canvas.drawText(metric.text, textX, textY, tmpPaint);
        }

        private void drawBottomMetric(Canvas canvas, Metric metric) {
            int startY = metric.start;
            int endY = viewRect.bottom + levelOffset * metric.level;
            int x1 = metric.v1;
            int x2 = metric.v2;

            tmpPaint.setColor(metric.color);
            tmpPaint.setStrokeWidth(1);

            canvas.drawLine(x1, startY, x1, endY + lineOverOffset, tmpPaint);
            canvas.drawLine(x2, startY, x2, endY + lineOverOffset, tmpPaint);
            canvas.drawLine(x1 - lineOverOffset, endY, x2 + lineOverOffset, endY, tmpPaint);

            tmpPaint.setTextSize(metricTextSize);
            int textW = (int) tmpPaint.measureText(metric.text);

            int textX = ((x1 + x2) >> 1) - textW / 2;
            int textY = endY + textOnLineOffset + metricTextSize;

            canvas.drawText(metric.text, textX, textY, tmpPaint);
        }
    }
}
