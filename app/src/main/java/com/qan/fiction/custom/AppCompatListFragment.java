package com.qan.fiction.custom;

import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;

public class AppCompatListFragment extends ListFragment  {
    protected AppCompatActivity getSupportActivity() {
        return ((AppCompatActivity) getActivity());
    }
}

