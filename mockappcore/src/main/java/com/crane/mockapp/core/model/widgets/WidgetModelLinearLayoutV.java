package com.crane.mockapp.core.model.widgets;

import android.content.Context;
import android.widget.LinearLayout;

import com.crane.mockapp.core.model.props.PropModel;

/**
 * Created by crane2002 on 3/11/2017.
 */

public class WidgetModelLinearLayoutV extends WidgetModelViewClass {

    public WidgetModelLinearLayoutV(Context context) {
        super(context, LinearLayout.class);
    }

    @Override
    public void assignDefaults(Context context, Object view) {
        super.assignDefaults(context, view);
        PropModel.LAYOUT_ORIENTATION.setProp(view, LinearLayout.VERTICAL, PropModel.PropModifyAction.FORCE_MODIFY);
    }

    @Override
    public String getTitle() {
        return super.getTitle() + " V";
    }
}
