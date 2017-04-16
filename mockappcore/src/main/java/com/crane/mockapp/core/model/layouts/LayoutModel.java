package com.crane.mockapp.core.model.layouts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by azhuravlev on 2/2/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LayoutModel {

    @JsonProperty
    String id;

    @JsonProperty
    String name;

    @JsonProperty
    Date lastModified;

    @JsonCreator
    LayoutModel(@JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("lastModified") Date lastModified) {
        this.id = id;
        this.name = name;
        this.lastModified = lastModified;
    }

    public String getId() {
        return id;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public String getName() {
        return name;
    }

    static String getLayoutFileName(String name) {
        return name + ".json";
    }

    static String getBitmapFileName(String name, boolean thumbnail) {
        return name + (thumbnail ? ".mini" : "") + ".png";
    }
}
