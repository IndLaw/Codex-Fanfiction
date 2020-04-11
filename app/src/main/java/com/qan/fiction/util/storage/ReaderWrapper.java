package com.qan.fiction.util.storage;

import java.io.Serializable;

public class ReaderWrapper implements Serializable {
    public final String loc;
    public final int chapters;
    public final int id;
    public final String title;

    public ReaderWrapper(String location, String title, int id, int chapters) {
        this.loc = location;
        this.title = title;
        this.id = id;
        this.chapters = chapters;
    }
}