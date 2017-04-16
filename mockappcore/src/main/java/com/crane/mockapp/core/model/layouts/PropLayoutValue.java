package com.crane.mockapp.core.model.layouts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by crane2002 on 1/28/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropLayoutValue {

    @JsonProperty("projectId")
    private String projectId;

    @JsonProperty("layoutId")
    private String layoutId;

    @JsonCreator
    public PropLayoutValue(@JsonProperty("projectId") String projectId, @JsonProperty("layoutId") String layoutId) {
        this.projectId = projectId;
        this.layoutId = layoutId;
    }

    @Override
    public PropLayoutValue clone() {
        PropLayoutValue clone = new PropLayoutValue(projectId, layoutId);
        return clone;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getLayoutId() {
        return layoutId;
    }
}
