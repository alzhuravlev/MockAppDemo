package com.crane.mockapp.core;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.crane.mockapp.core.model.layouts.Layout;
import com.crane.mockapp.core.model.layouts.LayoutInflater;
import com.crane.mockapp.core.model.theme.ThemeModel;

/**
 * Created by crane2002 on 1/20/2017.
 */
public class RecyclerViewLayoutAdapter extends RecyclerView.Adapter<RecyclerViewLayoutAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        void bind() {
            ThemeUtils.applyThemeToViewHierarchy(itemView.getContext(), designTime, imageProvider, itemView, themeModel, false);
        }
    }

    private boolean designTime;
    private int count;
    private Layout layout;
    private ThemeModel themeModel;
    private ImageProvider imageProvider;

    public RecyclerViewLayoutAdapter(Layout layout) {
        this.layout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((View) LayoutInflater.inflate(parent.getContext(), layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return layout == null ? 0 : count;
    }

    public void update(boolean designTime, ImageProvider imageProvider, int count, ThemeModel themeModel) {
        boolean needToUpdate = (this.count != count) || this.themeModel != themeModel;
        this.designTime = designTime;
        this.imageProvider = imageProvider;
        this.count = designTime && count > 1 ? 1 : count;
        this.themeModel = themeModel;
        if (needToUpdate)
            notifyDataSetChanged();
    }
}
