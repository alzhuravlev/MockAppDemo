package com.crane.mockapp.core.model.widgets;

import android.content.Context;

import com.crane.mockapp.core.model.layouts.ProjectServiceFactory;
import com.crane.mockapp.core.model.props.PropModel;
import com.crane.mockapp.core.model.layouts.PropLayoutValue;
import com.crane.mockapp.core.model.layouts.LayoutInflater;
import com.crane.mockapp.core.model.layouts.LayoutDescriptor;

/**
 * Created by azhuravlev on 1/20/2017.
 */

public class WidgetModelLayout extends WidgetModel {

    private String projectId;
    private String layoutId;

    public WidgetModelLayout(Context context, String projectId, String layoutId) {
        super(context);
        this.projectId = projectId;
        this.layoutId = layoutId;
    }

    @Override
    public void assignDefaults(Context context, Object view) {
    }

    @Override
    protected Object doNewViewInstance(Object parent) {
        LayoutDescriptor layoutDescriptor = ProjectServiceFactory.getInstace(context).loadLayout(projectId, layoutId);
        if (layoutDescriptor == null || layoutDescriptor.getLayout() == null)
            return null;
        Object view = LayoutInflater.inflate(context, layoutDescriptor.getLayout(), parent, false);
        if (view != null) {
            PropModel.clearModifiedHierarchy(view);
            PropModel.CUSTOM_LAYOUT.setProp(view, new PropLayoutValue(projectId, layoutId));
        }
        return view;
    }

    @Override
    public String getTitle() {
        return ProjectServiceFactory.getInstace(context).formatLayoutName(projectId, layoutId);
    }

    @Override
    public WidgetModelCategory getCategory() {
        return WidgetModelCategory.CUSTOM;
    }
}
