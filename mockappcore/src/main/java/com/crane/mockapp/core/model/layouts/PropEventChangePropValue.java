package com.crane.mockapp.core.model.layouts;

import com.crane.mockapp.core.model.props.PropModel;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by crane2002 on 1/28/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropEventChangePropValue {

    public enum Anim {
        NONE,
        FAST,
        MEDIUM,
        SLOW
    }

    @JsonProperty("viewIdPath")
    public String viewIdPath;

    @JsonProperty("prop")
    public PropModel prop;

    @JsonProperty("value")
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    public Object value;

    @JsonProperty("animate")
    public Anim animate;

    @JsonCreator
    public PropEventChangePropValue(@JsonProperty("viewIdPath") String viewIdPath,
                                    @JsonProperty("prop") PropModel prop,
                                    @JsonProperty("value") Object value,
                                    @JsonProperty("animate") Anim animate) {
        this.viewIdPath = viewIdPath;
        this.prop = prop;
        this.value = value;
        this.animate = animate;
    }

    public PropEventChangePropValue(String viewIdPath) {
        this.viewIdPath = viewIdPath;
    }

    @Override
    protected PropEventChangePropValue clone() {
        PropEventChangePropValue clone = new PropEventChangePropValue(viewIdPath);
        clone.prop = prop;
        clone.value = value;
        clone.animate = animate;
        return clone;
    }
}
