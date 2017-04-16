package com.crane.mockapp.core.model.theme;

import java.util.List;

/**
 * Created by crane2002 on 1/4/2017.
 */

public interface ThemeModelService {

    ThemeModel findPrimaryTheme(int themeId);

    ThemeModel findAccentTheme(int themeId);

    ThemeModel getCommonTheme();

    List<ThemeModel> findPrimaryThemes();

    List<ThemeModel> findAccentThemes();

    ThemeModel buildTheme(int primaryThemeId, int accentThemeId);
}
