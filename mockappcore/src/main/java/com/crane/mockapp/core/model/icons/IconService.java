package com.crane.mockapp.core.model.icons;

import java.util.List;

/**
 * Created by crane2002 on 1/14/2017.
 */

public interface IconService {
    List<IconModel> findIcons();

    List<IconModel> findIcons(String s);
}
