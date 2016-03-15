package com.example.nikhiljoshi.naakh.network.POJO.Translate;

import java.util.List;

/**
 * Contains JSON keys returned when hitting a translation Object Naakh API endpoint
 *
 * <br> Note: Don't change the name of the variables unless there are changes made in the
 * NaakhAPI.
 */
public class TranslateObject {

    private List<TranslationInfo> objects;

    public List<TranslationInfo> getObjects() {
        return objects;
    }

}
