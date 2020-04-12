package com.qan.fiction.ui.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import com.qan.fiction.R;
import com.qan.fiction.util.constants.Settings;


public class SettingsActivityModern extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    private void setView() {
        if (Settings.isLightTheme(this))
            setTheme(R.style.Theme_Styled);
        else
            setTheme(R.style.Theme_Styled_Dark);
        setContentView(R.layout.single_menu);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
         transaction.replace(R.id.fragment_container, new SettingsFragment());
        transaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.settings));
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.prefs);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.light_theme))) {
            recreate();
            Intent intent = new Intent();
            intent.setAction(getString(R.string.broadcast_theme));
            sendBroadcast(intent);
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}