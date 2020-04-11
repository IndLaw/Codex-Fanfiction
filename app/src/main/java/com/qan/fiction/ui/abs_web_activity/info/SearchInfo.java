package com.qan.fiction.ui.abs_web_activity.info;

import java.io.Serializable;

public class SearchInfo implements Serializable {

    public final String name;

    /**
     * A  formatted {@link String} representing the story name
     */
    public final String title;

    /**
     * The href of the link location
     */
    public final String ref;

    public SearchInfo(String name, String title, String ref) {
        this.name = name;
        this.title = title;
        this.ref = ref;
    }


    public String toString() {
        return name.replace("/", " ");
    }

}
