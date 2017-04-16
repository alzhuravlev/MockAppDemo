package com.crane.mockapp.core;

/**
 * Created by crane2002 on 1/20/2017.
 */

public class StringUtils {

    public static boolean equals(String s1, String s2) {
        if (s1 != null && s1.equals(s2))
            return true;
        return s1 == null && s2 == null;
    }
}
