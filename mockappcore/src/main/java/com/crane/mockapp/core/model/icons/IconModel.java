package com.crane.mockapp.core.model.icons;

/**
 * Created by crane2002 on 1/14/2017.
 */

public class IconModel {

    public static final IconModel NO_ICON = new IconModel(null);

    private String id;

    IconModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return getTitle(id);
    }

    public boolean containsString(String s) {
        return id != null && id.contains(s);
    }

    public static String getTitle(String id) {
        return id == null ? "No Icon" : id.length() < 5 ? id : id.substring(4);
    }
}
