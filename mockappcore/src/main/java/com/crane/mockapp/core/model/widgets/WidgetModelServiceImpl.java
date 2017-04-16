package com.crane.mockapp.core.model.widgets;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.crane.mockapp.core.Utils;

/**
 * Created by crane2002 on 1/4/2017.
 */

class WidgetModelServiceImpl implements WidgetModelService {
    private Context context;

    private final List<WidgetModel> WIDGET_MODELS = new ArrayList<>();
    private final List<WidgetModel> TOOLBAR_WIDGET_MODELS = new ArrayList<>();
    private final List<WidgetModel> TABLAYOUT_WIDGET_MODELS = new ArrayList<>();

    private void addWidgetModel(WidgetModel model) {
        WIDGET_MODELS.add(model);
    }

    private void addToolbarWidgetModel(WidgetModel model) {
        TOOLBAR_WIDGET_MODELS.add(model);
    }

    private void addTabLayoutWidgetModel(WidgetModel model) {
        TABLAYOUT_WIDGET_MODELS.add(model);
    }

    private void init() {

        addWidgetModel(new WidgetModelViewClass(context, View.class));
        addWidgetModel(new WidgetModelViewClass(context, TextView.class));
        addWidgetModel(new WidgetModelViewClass(context, EditText.class));
        addWidgetModel(new WidgetModelViewClass(context, Button.class));
        addWidgetModel(new WidgetModelViewClass(context, ImageView.class));
        addWidgetModel(new WidgetModelViewClass(context, CheckBox.class));
        addWidgetModel(new WidgetModelViewClass(context, RadioButton.class));
        addWidgetModel(new WidgetModelViewClass(context, SeekBar.class));
        addWidgetModel(new WidgetModelViewClass(context, ProgressBar.class));

        addWidgetModel(new WidgetModelViewClass(context, FrameLayout.class));
        addWidgetModel(new WidgetModelLinearLayoutH(context));
        addWidgetModel(new WidgetModelLinearLayoutV(context));
        addWidgetModel(new WidgetModelViewClass(context, RadioGroup.class));
        addWidgetModel(new WidgetModelViewClass(context, Toolbar.class));
        addWidgetModel(new WidgetModelViewClass(context, RecyclerView.class));
        addWidgetModel(new WidgetModelViewClass(context, ViewAnimator.class));
        addWidgetModel(new WidgetModelViewClass(context, ViewPager.class));
        addWidgetModel(new WidgetModelViewClass(context, TabLayout.class));
        addWidgetModel(new WidgetModelViewClass(context, ScrollView.class));
//        addWidgetModel(new WidgetModelViewClass(context, HorizontalScrollView.class));
        addWidgetModel(new WidgetModelCustom(context));

        addTabLayoutWidgetModel(new WidgetModelViewClass(context, Utils.Tab.class));

        addToolbarWidgetModel(new WidgetModelViewClass(context, Utils.MenuItem.class));
        addToolbarWidgetModel(new WidgetModelLinearLayoutH(context));
        addToolbarWidgetModel(new WidgetModelLinearLayoutV(context));
    }


    WidgetModelServiceImpl(Context context) {
        this.context = context;
        init();
    }

    @Override
    public List<WidgetModel> findWidgetModels(Object parentToAdd) {
        if (parentToAdd == null)
            return Collections.emptyList();

        if (parentToAdd instanceof TabLayout)
            return TABLAYOUT_WIDGET_MODELS;
        else if (parentToAdd instanceof Toolbar) {
            return TOOLBAR_WIDGET_MODELS;
        } else if (parentToAdd instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) parentToAdd;
            if (scrollView.getChildCount() > 0)
                return Collections.emptyList();
        }

        return WIDGET_MODELS;
    }
}
