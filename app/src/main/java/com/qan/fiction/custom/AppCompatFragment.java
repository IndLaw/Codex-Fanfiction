package com.qan.fiction.custom;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

public class AppCompatFragment extends Fragment {
    protected AppCompatActivity getSupportActivity() {
        return ((AppCompatActivity) getActivity());
    }
}
