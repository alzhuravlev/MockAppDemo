package com.crane.mockapp.core.model.props;

import java.util.List;
import java.util.Set;

/**
 * Created by crane2002 on 1/4/2017.
 */

public interface PropModelService {

    List<PropModel> findPropModels(PropModelCategory category, Object view);

    Set<PropModelCategory> findPropModelCategories(Object view);
}
