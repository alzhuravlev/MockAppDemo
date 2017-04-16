package com.crane.mockapp.core.model.icons;

import android.content.Context;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crane2002 on 1/14/2017.
 */

class IconServiceImpl implements IconService {
    private Context context;

    private List<IconModel> iconModels = new ArrayList<>();


    private void init() {
        for (GoogleMaterial.Icon icon : GoogleMaterial.Icon.values()) {
            iconModels.add(new IconModel(icon.getName()));
        }
    }

    public IconServiceImpl(Context context) {
        this.context = context;
        init();
    }

    @Override
    public List<IconModel> findIcons() {
        return findIcons(null);
    }

    @Override
    public List<IconModel> findIcons(String s) {
        List<IconModel> result = new ArrayList<>();
        for (IconModel iconModel : iconModels)
            if (iconModel.containsString(s))
                result.add(iconModel);
        return result;
    }
}
