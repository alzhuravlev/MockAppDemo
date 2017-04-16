package com.crane.mockapp.core.model.layouts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by azhuravlev on 2/2/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectModel {

    public enum ProjectType {
        INTERNAL,
        LOCAL_FILE_SYSTEM,
        GOOGLE_DRIVE,
        ONE_DRIVE,
    }

    @JsonProperty
    ProjectType projectType;

    @JsonProperty
    String id;

    @JsonProperty
    String name;

    @JsonProperty
    int layoutCount;

    @JsonProperty
    Date lastOpened;

    @JsonCreator
    ProjectModel(@JsonProperty("projectType") ProjectType projectType,
                 @JsonProperty("id") String id,
                 @JsonProperty("name") String name,
                 @JsonProperty("layoutCount") int layoutCount,
                 @JsonProperty("lastOpened") Date lastOpened) {
        this.projectType = projectType;
        this.id = id;
        this.name = name;
        this.layoutCount = layoutCount;
        this.lastOpened = lastOpened;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public String getId() {
        return id;
    }

    public int getLayoutCount() {
        return layoutCount;
    }

    public String getName() {
        return name;
    }

    public Date getLastOpened() {
        return lastOpened;
    }
}
