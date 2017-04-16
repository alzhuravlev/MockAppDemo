package com.crane.mockapp.core.model.props;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by crane2002 on 1/4/2017.
 */

class PropModelServiceImpl implements PropModelService {

    private final List<PropModel> MODELS = new ArrayList<>();

    private Context context;

    public PropModelServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<PropModel> findPropModels(PropModelCategory category, Object view) {
        List<PropModel> result = null;
        for (PropModel model : PropModel.VALUES) {
            if (model.getCategory() != null && model.getCategory() == category && model.isApplicableForView(view)) {
                if (result == null)
                    result = new ArrayList<>();
                result.add(model);
            }
        }
        return result == null ? Collections.<PropModel>emptyList() : result;
    }

    @Override
    public Set<PropModelCategory> findPropModelCategories(Object view) {
        Set<PropModelCategory> result = null;
        for (PropModel model : PropModel.VALUES) {
            if (model.getCategory() != null)
                if (model.isApplicableForView(view)) {
                    if (result == null)
                        result = new TreeSet<>();
                    result.add(model.getCategory());
                }
        }
        return result == null ? Collections.<PropModelCategory>emptySet() : result;
    }
}
