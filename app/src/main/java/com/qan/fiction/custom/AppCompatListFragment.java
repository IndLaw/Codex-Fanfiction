package com.qan.fiction.custom;

import androidx.fragment.app.ListFragment;
import androidx.appcompat.app.AppCompatActivity;

public class AppCompatListFragment extends ListFragment  {
    protected AppCompatActivity getSupportActivity() {
        return ((AppCompatActivity) getActivity());
    }
}

