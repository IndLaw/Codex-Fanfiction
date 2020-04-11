package com.qan.fiction.ui.abs_web_activity;

interface Browsable {

    /**
     * The action to perform when a browser is opened.
     */
    void web_action();

    /**
     * @return An absolute URL
     */
    String getUrl();

    /**
     * @return An absolute URL for mobile sites. May be the same as {@link #getUrl()}.
     */
    String getMobileUrl();

}
