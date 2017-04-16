package com.crane.mockapp.core;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * Created by crane2002 on 2/20/2017.
 */

public class OutlineProvider extends ViewOutlineProvider {

    private int offset;
    private int radius;

    public OutlineProvider(int offset, int radius) {
        this.offset = offset;
        this.radius = radius;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(offset, offset, view.getWidth() - offset, view.getHeight() - offset, radius);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
