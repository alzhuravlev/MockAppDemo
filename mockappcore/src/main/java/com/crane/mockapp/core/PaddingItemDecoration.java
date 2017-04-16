package com.crane.mockapp.core;

import android.graphics.Rect;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by azhuravlev on 2/17/2017.
 */

public class PaddingItemDecoration extends RecyclerView.ItemDecoration {

    private int orientation;
    private int padding;
    private int padding_2;
    private int columnCount;
    private RecyclerView recyclerView;

    public PaddingItemDecoration(int orientation, int padding, int columnCount, RecyclerView recyclerView) {
        this.orientation = orientation;
        this.columnCount = columnCount;
        this.padding = padding;
        this.padding_2 = padding / 2;
        this.recyclerView = recyclerView;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
        this.padding_2 = padding / 2;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int pos = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        final int itemCount = recyclerView == null || recyclerView.getAdapter() == null ? 0 : recyclerView.getAdapter().getItemCount();

        switch (orientation) {
            case OrientationHelper.HORIZONTAL: {
                final boolean t = pos % columnCount == 0;
                final boolean b = pos % columnCount == columnCount - 1;
                final boolean l = pos < columnCount;
                final boolean r = pos >= itemCount - (columnCount - itemCount % columnCount);

                if (t)
                    outRect.top = padding;
                else
                    outRect.top = padding_2;

                if (b)
                    outRect.bottom = padding;
                else
                    outRect.bottom = padding_2;

                if (l)
                    outRect.left = padding;
                else
                    outRect.left = padding_2;

                if (r)
                    outRect.right = padding;
                else
                    outRect.right = padding_2;
            }
            break;

            case OrientationHelper.VERTICAL: {
                final boolean l = pos % columnCount == 0;
                final boolean r = pos % columnCount == columnCount - 1;
                final boolean t = pos < columnCount;
                final boolean b = pos >= itemCount - (columnCount - itemCount % columnCount);

                if (t)
                    outRect.top = padding;
                else
                    outRect.top = padding_2;

                if (b)
                    outRect.bottom = padding;
                else
                    outRect.bottom = padding_2;

                if (l)
                    outRect.left = padding;
                else
                    outRect.left = padding_2;

                if (r)
                    outRect.right = padding;
                else
                    outRect.right = padding_2;
            }
            break;
        }
    }
}
