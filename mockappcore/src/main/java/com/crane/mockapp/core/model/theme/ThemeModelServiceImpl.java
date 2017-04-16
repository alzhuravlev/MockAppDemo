package com.crane.mockapp.core.model.theme;

import android.content.Context;

import com.crane.mockapp.core.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crane2002 on 1/4/2017.
 */
class ThemeModelServiceImpl implements ThemeModelService {

    private Context context;

    private List<ThemeModel> modelsPrimary = new ArrayList<>();
    private List<ThemeModel> modelsAccent = new ArrayList<>();

    private ThemeModel modelCommon;

    private void init() {
        ThemeModel model;

        int id = 100;

        model = new ThemeModel(id++, R.string.theme_red);
        model.addColor(ThemeModelColor.P50, 0xffFFEBEE, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffFFCDD2, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffEF9A9A, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xffE57373, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xffEF5350, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P500, 0xffF44336, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P600, 0xffE53935, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xffD32F2F, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xffC62828, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xffB71C1C, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_red);
        model.addColor(ThemeModelColor.A100, 0xffFFEBEE, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xffFF5252, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A400, 0xffFF1744, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A700, 0xffD50000, ThemeModelBrightness.LIGHT);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_pink);
        model.addColor(ThemeModelColor.P50, 0xffFCE4EC, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffF8BBD0, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffF48FB1, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xffF06292, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P400, 0xffEC407A, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P500, 0xffE91E63, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P600, 0xffD81B60, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xffC2185B, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xffAD1457, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff880E4F, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_pink);
        model.addColor(ThemeModelColor.A100, 0xffFF80AB, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xffFF4081, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A400, 0xffF50057, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A700, 0xffC51162, ThemeModelBrightness.LIGHT);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_purple);
        model.addColor(ThemeModelColor.P50, 0xffF3E5F5, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffE1BEE7, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffCE93D8, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xffBA68C8, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P400, 0xffAB47BC, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P500, 0xff9C27B0, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P600, 0xff8E24AA, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xff7B1FA2, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xff6A1B9A, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff4A148C, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_purple);
        model.addColor(ThemeModelColor.A100, 0xffEA80FC, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xffE040FB, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A400, 0xffD500F9, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A700, 0xffAA00FF, ThemeModelBrightness.LIGHT);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_deep_purple);
        model.addColor(ThemeModelColor.P50, 0xffEDE7F6, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffD1C4E9, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffB39DDB, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xff9575CD, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P400, 0xff7E57C2, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P500, 0xff673AB7, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P600, 0xff5E35B1, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xff512DA8, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xff4527A0, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff311B92, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_deep_purple);
        model.addColor(ThemeModelColor.A100, 0xffB388FF, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xff7C4DFF, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A400, 0xff651FFF, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A700, 0xff6200EA, ThemeModelBrightness.LIGHT);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_indigo);
        model.addColor(ThemeModelColor.P50, 0xffE8EAF6, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffC5CAE9, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xff9FA8DA, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xff7986CB, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P400, 0xff5C6BC0, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P500, 0xff3F51B5, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P600, 0xff3949AB, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xff303F9F, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xff283593, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff1A237E, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_indigo);
        model.addColor(ThemeModelColor.A100, 0xff8C9EFF, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xff536DFE, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A400, 0xff3D5AFE, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A700, 0xff304FFE, ThemeModelBrightness.LIGHT);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_blue);
        model.addColor(ThemeModelColor.P50, 0xffE3F2FD, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffBBDEFB, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xff90CAF9, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xff64B5F6, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xff42A5F5, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P500, 0xff2196F3, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P600, 0xff1E88E5, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xff1976D2, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xff1565C0, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff0D47A1, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_blue);
        model.addColor(ThemeModelColor.A100, 0xff82B1FF, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xff448AFF, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A400, 0xff2979FF, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A700, 0xff2962FF, ThemeModelBrightness.LIGHT);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_light_blue);
        model.addColor(ThemeModelColor.P50, 0xffE1F5FE, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffB3E5FC, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xff81D4FA, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xff4FC3F7, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xff29B6F6, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P500, 0xff03A9F4, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P600, 0xff039BE5, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xff0288D1, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xff0277BD, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff01579B, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_light_blue);
        model.addColor(ThemeModelColor.A100, 0xff80D8FF, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xff40C4FF, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A400, 0xff00B0FF, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A700, 0xff0091EA, ThemeModelBrightness.LIGHT);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_cyan);
        model.addColor(ThemeModelColor.P50, 0xffE0F7FA, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffB2EBF2, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xff80DEEA, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xff4DD0E1, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xff26C6DA, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P500, 0xff00BCD4, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P600, 0xff00ACC1, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P700, 0xff0097A7, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xff00838F, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff006064, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_cyan);
        model.addColor(ThemeModelColor.A100, 0xff84FFFF, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xff18FFFF, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A400, 0xff00E5FF, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A700, 0xff00B8D4, ThemeModelBrightness.DARK);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_teal);
        model.addColor(ThemeModelColor.P50, 0xffE0F2F1, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffB2DFDB, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xff80CBC4, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xff4DB6AC, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xff26A69A, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P500, 0xff009688, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P600, 0xff00897B, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xff00796B, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xff00695C, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff004D40, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_teal);
        model.addColor(ThemeModelColor.A100, 0xffA7FFEB, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xff64FFDA, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A400, 0xff1DE9B6, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A700, 0xff00BFA5, ThemeModelBrightness.DARK);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_green);
        model.addColor(ThemeModelColor.P50, 0xffE8F5E9, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffC8E6C9, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffA5D6A7, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xff81C784, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xff66BB6A, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P500, 0xff4CAF50, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P600, 0xff43A047, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xff388E3C, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xff2E7D32, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff1B5E20, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_green);
        model.addColor(ThemeModelColor.A100, 0xffB9F6CA, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xff69F0AE, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A400, 0xff00E676, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A700, 0xff00C853, ThemeModelBrightness.DARK);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_light_green);
        model.addColor(ThemeModelColor.P50, 0xffF1F8E9, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffDCEDC8, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffC5E1A5, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xffAED581, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xff9CCC65, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P500, 0xff8BC34A, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P600, 0xff7CB342, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P700, 0xff689F38, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xff558B2F, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff33691E, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_light_green);
        model.addColor(ThemeModelColor.A100, 0xffCCFF90, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xffB2FF59, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A400, 0xff76FF03, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A700, 0xff64DD17, ThemeModelBrightness.DARK);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_lime);
        model.addColor(ThemeModelColor.P50, 0xffF9FBE7, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffF0F4C3, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffE6EE9C, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xffDCE775, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xffD4E157, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P500, 0xffCDDC39, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P600, 0xffC0CA33, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P700, 0xffAFB42B, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P800, 0xff9E9D24, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P900, 0xff827717, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_lime);
        model.addColor(ThemeModelColor.A100, 0xffF4FF81, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xffEEFF41, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A400, 0xffC6FF00, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A700, 0xffAEEA00, ThemeModelBrightness.DARK);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_yellow);
        model.addColor(ThemeModelColor.P50, 0xffFFFDE7, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffFFF9C4, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffFFF9C4, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xffFFF176, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xffFFEE58, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P500, 0xffFFEB3B, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P600, 0xffFDD835, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P700, 0xffFBC02D, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P800, 0xffF9A825, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P900, 0xffF57F17, ThemeModelBrightness.DARK);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_yellow);
        model.addColor(ThemeModelColor.A100, 0xffFFFF8D, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xffFFFF00, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A400, 0xffFFEA00, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A700, 0xffFFD600, ThemeModelBrightness.DARK);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_amber);
        model.addColor(ThemeModelColor.P50, 0xffFFF8E1, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffFFECB3, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffFFE082, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xffFFD54F, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xffFFCA28, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P500, 0xffFFC107, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P600, 0xffFFB300, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P700, 0xffFFA000, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P800, 0xffFF8F00, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P900, 0xffFF6F00, ThemeModelBrightness.DARK);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_amber);
        model.addColor(ThemeModelColor.A100, 0xffFFE57F, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xffFFD740, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A400, 0xffFFC400, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A700, 0xffFFC400, ThemeModelBrightness.DARK);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_orange);
        model.addColor(ThemeModelColor.P50, 0xffFFF3E0, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffFFE0B2, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffFFCC80, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xffFFB74D, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xffFFA726, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P500, 0xffFF9800, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P600, 0xffFB8C00, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P700, 0xffF57C00, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P800, 0xffEF6C00, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xffE65100, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_orange);
        model.addColor(ThemeModelColor.A100, 0xffFFD180, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xffFFAB40, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A400, 0xffFF9100, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A700, 0xffFF6D00, ThemeModelBrightness.DARK);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_deep_orange);
        model.addColor(ThemeModelColor.P50, 0xffFBE9E7, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffFFCCBC, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffFFAB91, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xffFF8A65, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xffFF7043, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P500, 0xffFF5722, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P600, 0xffF4511E, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xffE64A19, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xffD84315, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xffBF360C, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_deep_orange);
        model.addColor(ThemeModelColor.A100, 0xffFF9E80, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A200, 0xffFF6E40, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.A400, 0xffFF3D00, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.A700, 0xffDD2C00, ThemeModelBrightness.LIGHT);
        modelsAccent.add(model);

        model = new ThemeModel(id++, R.string.theme_brown);
        model.addColor(ThemeModelColor.P50, 0xffEFEBE9, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffD7CCC8, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffBCAAA4, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xffA1887F, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P400, 0xff8D6E63, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P500, 0xff795548, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P600, 0xff6D4C41, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xff5D4037, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xff4E342E, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff3E2723, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_grey);
        model.addColor(ThemeModelColor.P50, 0xffFAFAFA, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffF5F5F5, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffEEEEEE, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xffE0E0E0, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xffBDBDBD, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P500, 0xff9E9E9E, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P600, 0xff757575, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xff616161, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xff424242, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff212121, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        model = new ThemeModel(id++, R.string.theme_blue_grey);
        model.addColor(ThemeModelColor.P50, 0xffECEFF1, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P100, 0xffCFD8DC, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P200, 0xffB0BEC5, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P300, 0xff90A4AE, ThemeModelBrightness.DARK);
        model.addColor(ThemeModelColor.P400, 0xff78909C, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P500, 0xff607D8B, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P600, 0xff546E7A, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P700, 0xff455A64, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P800, 0xff37474F, ThemeModelBrightness.LIGHT);
        model.addColor(ThemeModelColor.P900, 0xff263238, ThemeModelBrightness.LIGHT);
        modelsPrimary.add(model);

        modelCommon = new ThemeModel(-1, -1);
        modelCommon.addColor(ThemeModelColor.TRANSPARENT, 0x00000000);

        modelCommon.addColor(ThemeModelColor.TEXT_DARK_PRIMARY, 0xde000000, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.TEXT_DARK_SECONDARY, 0x8a000000, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.TEXT_DARK_DISABLED, 0x61000000, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.DIVIDER_DARK, 0x1f000000, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.ICON_DARK_ACTIVE, 0x8a000000, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.ICON_DARK_INACTIVE, 0x61000000, ThemeModelBrightness.LIGHT);

        modelCommon.addColor(ThemeModelColor.TEXT_LIGHT_PRIMARY, 0xffffffff, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.TEXT_LIGHT_SECONDARY, 0xb3ffffff, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.TEXT_LIGHT_DISABLED, 0x80ffffff, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.DIVIDER_LIGHT, 0x1fffffff, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.ICON_LIGHT_ACTIVE, 0xffffffff, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.ICON_LIGHT_INACTIVE, 0x80ffffff, ThemeModelBrightness.DARK);

        modelCommon.addColor(ThemeModelColor.Pink, 0xffFFC0CB, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LightPink, 0xffFFB6C1, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.HotPink, 0xffFF69B4, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.DeepPink, 0xffFF1493, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.PaleVioletRed, 0xffDB7093, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.MediumVioletRed, 0xffC71585, ThemeModelBrightness.LIGHT);

        modelCommon.addColor(ThemeModelColor.LightSalmon, 0xffFFA07A, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Salmon, 0xffFA8072, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.DarkSalmon, 0xffE9967A, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LightCoral, 0xffF08080, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.IndianRed, 0xffCD5C5C, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Crimson, 0xffDC143C, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.FireBrick, 0xffB22222, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.DarkRed, 0xff8B0000, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Red, 0xffFF0000, ThemeModelBrightness.LIGHT);

        modelCommon.addColor(ThemeModelColor.OrangeRed, 0xffFF4500, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Tomato, 0xffFF6347, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Coral, 0xffFF7F50, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.DarkOrange, 0xffFF8C00, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Orange, 0xffFFA500, ThemeModelBrightness.DARK);

        modelCommon.addColor(ThemeModelColor.Yellow, 0xffFFFF00, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LightYellow, 0xffFFFFE0, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LemonChiffon, 0xffFFFACD, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LightGoldenrodYellow, 0xffFAFAD2, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.PapayaWhip, 0xffFFEFD5, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Moccasin, 0xffFFE4B5, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.PeachPuff, 0xffFFDAB9, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.PaleGoldenrod, 0xffEEE8AA, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Khaki, 0xffF0E68C, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.DarkKhaki, 0xffBDB76B, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Gold, 0xffFFD700, ThemeModelBrightness.DARK);

        modelCommon.addColor(ThemeModelColor.Cornsilk, 0xffFFF8DC, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.BlanchedAlmond, 0xffFFEBCD, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Bisque, 0xffFFE4C4, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.NavajoWhite, 0xffFFDEAD, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Wheat, 0xffF5DEB3, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.BurlyWood, 0xffDEB887, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Tan, 0xffD2B48C, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.RosyBrown, 0xffBC8F8F, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.SandyBrown, 0xffF4A460, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Goldenrod, 0xffDAA520, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.DarkGoldenrod, 0xffB8860B, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Peru, 0xffCD853F, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Chocolate, 0xffD2691E, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.SaddleBrown, 0xff8B4513, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Sienna, 0xffA0522D, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Brown, 0xffA52A2A, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Maroon, 0xff800000, ThemeModelBrightness.LIGHT);

        modelCommon.addColor(ThemeModelColor.DarkOliveGreen, 0xff556B2F, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Olive, 0xff808000, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.OliveDrab, 0xff6B8E23, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.YellowGreen, 0xff9ACD32, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LimeGreen, 0xff32CD32, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Lime, 0xff00FF00, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LawnGreen, 0xff7CFC00, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Chartreuse, 0xff7FFF00, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.GreenYellow, 0xffADFF2F, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.SpringGreen, 0xff00FF7F, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.MediumSpringGreen, 0xff00FA9A, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LightGreen, 0xff90EE90, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.PaleGreen, 0xff98FB98, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.DarkSeaGreen, 0xff8FBC8F, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.MediumAquamarine, 0xff66CDAA, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.MediumSeaGreen, 0xff3CB371, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.SeaGreen, 0xff2E8B57, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.ForestGreen, 0xff228B22, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Green, 0xff008000, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.DarkGreen, 0xff006400, ThemeModelBrightness.LIGHT);

        modelCommon.addColor(ThemeModelColor.Aqua, 0xff00FFFF, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Cyan, 0xff00FFFF, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LightCyan, 0xffE0FFFF, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.PaleTurquoise, 0xffAFEEEE, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Aquamarine, 0xff7FFFD4, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Turquoise, 0xff40E0D0, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.MediumTurquoise, 0xff48D1CC, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.DarkTurquoise, 0xff00CED1, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LightSeaGreen, 0xff20B2AA, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.CadetBlue, 0xff5F9EA0, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.DarkCyan, 0xff008B8B, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Teal, 0xff008080, ThemeModelBrightness.LIGHT);

        modelCommon.addColor(ThemeModelColor.LightSteelBlue, 0xffB0C4DE, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.PowderBlue, 0xffB0E0E6, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LightBlue, 0xffADD8E6, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.SkyBlue, 0xff87CEEB, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LightSkyBlue, 0xff87CEFA, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.DeepSkyBlue, 0xff00BFFF, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.DodgerBlue, 0xff1E90FF, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.CornflowerBlue, 0xff6495ED, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.SteelBlue, 0xff4682B4, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.RoyalBlue, 0xff4169E1, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Blue, 0xff0000FF, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.MediumBlue, 0xff0000CD, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.DarkBlue, 0xff00008B, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Navy, 0xff000080, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.MidnightBlue, 0xff191970, ThemeModelBrightness.LIGHT);

        modelCommon.addColor(ThemeModelColor.Lavender, 0xffE6E6FA, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Thistle, 0xffD8BFD8, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Plum, 0xffDDA0DD, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Violet, 0xffEE82EE, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Orchid, 0xffDA70D6, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Fuchsia, 0xffFF00FF, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Magenta, 0xffFF00FF, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.MediumOrchid, 0xffBA55D3, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.MediumPurple, 0xff9370DB, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.BlueViolet, 0xff8A2BE2, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.DarkViolet, 0xff9400D3, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.DarkOrchid, 0xff9932CC, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.DarkMagenta, 0xff8B008B, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Purple, 0xff800080, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Indigo, 0xff4B0082, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.DarkSlateBlue, 0xff483D8B, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.SlateBlue, 0xff6A5ACD, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.MediumSlateBlue, 0xff7B68EE, ThemeModelBrightness.LIGHT);

        modelCommon.addColor(ThemeModelColor.White, 0xffFFFFFF, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Snow, 0xffFFFAFA, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Honeydew, 0xffF0FFF0, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.MintCream, 0xffF5FFFA, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Azure, 0xffF0FFFF, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.AliceBlue, 0xffF0F8FF, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.GhostWhite, 0xffF8F8FF, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.WhiteSmoke, 0xffF5F5F5, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Seashell, 0xffFFF5EE, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Beige, 0xffF5F5DC, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.OldLace, 0xffFDF5E6, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.FloralWhite, 0xffFFFAF0, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Ivory, 0xffFFFFF0, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.AntiqueWhite, 0xffFAEBD7, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Linen, 0xffFAF0E6, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LavenderBlush, 0xffFFF0F5, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.MistyRose, 0xffFFE4E1, ThemeModelBrightness.DARK);

        modelCommon.addColor(ThemeModelColor.Gainsboro, 0xffDCDCDC, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.LightGray, 0xffD3D3D3, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Silver, 0xffC0C0C0, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.DarkGray, 0xffA9A9A9, ThemeModelBrightness.DARK);
        modelCommon.addColor(ThemeModelColor.Gray, 0xff808080, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.DimGray, 0xff696969, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.LightSlateGray, 0xff778899, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.SlateGray, 0xff708090, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.DarkSlateGray, 0xff2F4F4F, ThemeModelBrightness.LIGHT);
        modelCommon.addColor(ThemeModelColor.Black, 0xff000000, ThemeModelBrightness.LIGHT);

    }

    public ThemeModelServiceImpl(Context context) {
        this.context = context;
        init();
    }

    @Override
    public List<ThemeModel> findPrimaryThemes() {
        return modelsPrimary;
    }

    @Override
    public List<ThemeModel> findAccentThemes() {
        return modelsAccent;
    }

    @Override
    public ThemeModel findPrimaryTheme(int themeId) {
        for (ThemeModel themeModel : modelsPrimary)
            if (themeModel.getId() == themeId)
                return themeModel;
        return modelsPrimary.get(0);
    }

    @Override
    public ThemeModel findAccentTheme(int themeId) {
        for (ThemeModel themeModel : modelsAccent)
            if (themeModel.getId() == themeId)
                return themeModel;
        return modelsAccent.get(0);
    }

    @Override
    public ThemeModel getCommonTheme() {
        return modelCommon;
    }

    @Override
    public ThemeModel buildTheme(int primaryThemeId, int accentThemeId) {
        ThemeModel primaryTheme = findPrimaryTheme(primaryThemeId);
        ThemeModel accentTheme = findAccentTheme(accentThemeId);
        ThemeModel commonTheme = getCommonTheme();
        return new ThemeModel(primaryTheme, accentTheme, commonTheme);
    }
}
