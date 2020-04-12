package com.qan.fiction.util.misc.listeners;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public interface ViewListener {
    void startDownloadService(Intent i);

    void openFragment(Fragment fragment, Bundle b);

    void openTab(Fragment fragment, FragmentTransaction ft);

    /**
     * Sets the content for the tabs which are opened.
     */
    void setContent();
}
