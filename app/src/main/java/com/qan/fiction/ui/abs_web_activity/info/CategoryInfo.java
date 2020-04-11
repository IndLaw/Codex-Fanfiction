package com.qan.fiction.ui.abs_web_activity.info;

import com.qan.fiction.util.web.FormattedNumber;

import java.io.Serializable;

public class CategoryInfo implements Serializable {

    public final String name;

    /**
     * A  formatted {@link String} representing number of stories
     */
    public final String value;
    public final Integer val;

    /**
     * The absolute url of the link location
     */
    public final String ref;

    public CategoryInfo(String name, String value, String ref) {
        this.name = name;
        this.value = value;
        String number = value.replaceAll(",", "");
        val = FormattedNumber.parseInt(number);
        this.ref = ref;
    }


    public String toString() {
        return name.replace("/", " ");
    }

}
