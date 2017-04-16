package com.crane.mockapp.core.model.layouts;

import android.content.Context;
import android.content.Intent;

import com.crane.mockapp.core.Arguments;
import com.crane.mockapp.core.PreviewActivity;
import com.crane.mockapp.core.model.theme.ThemeModel;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by crane2002 on 1/28/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropEventOpenCustomLayoutValue extends PropEventValue {

    @JsonProperty("layoutValue")
    private PropLayoutValue layoutValue;

    @JsonProperty("openAsDialog")
    private Boolean openAsDialog;


    @JsonCreator
    public PropEventOpenCustomLayoutValue(
            @JsonProperty("layoutValue") PropLayoutValue layoutValue,
            @JsonProperty("openAsDialog") Boolean openAsDialog) {
        this.layoutValue = layoutValue;
        this.openAsDialog = openAsDialog;
    }

    @Override
    public boolean isEmpty() {
        return layoutValue == null;
    }

    @Override
    protected void doExecute(final Context context, final Object view, ThemeModel themeModel) {
        if (layoutValue == null)
            return;

        if (isOpenAsDialog() && context instanceof PreviewActivity) {
            PreviewActivity previewActivity = (PreviewActivity) context;
            previewActivity.showDialog(layoutValue.getProjectId(), layoutValue.getLayoutId());
        } else {
            Intent intent = new Intent(context, PreviewActivity.class);
            intent.putExtra(Arguments.ARGUMENT_PROJECT_ID, layoutValue.getProjectId());
            intent.putExtra(Arguments.ARGUMENT_LAYOUT_ID, layoutValue.getLayoutId());
            context.startActivity(intent);
        }
    }

    @Override
    public PropEventOpenCustomLayoutValue clone() {
        return new PropEventOpenCustomLayoutValue(layoutValue == null ? null : layoutValue.clone(), openAsDialog);
    }

    public PropLayoutValue getLayoutValue() {
        return layoutValue;
    }

    public boolean isOpenAsDialog() {
        return openAsDialog != null && openAsDialog;
    }

    public void setOpenAsDialog(Boolean openAsDialog) {
        this.openAsDialog = openAsDialog;
    }
}
