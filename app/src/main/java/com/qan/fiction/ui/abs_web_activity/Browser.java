package com.qan.fiction.ui.abs_web_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import androidx.appcompat.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qan.fiction.R;
import com.qan.fiction.custom.AppCompatListFragment;
import com.qan.fiction.util.misc.listeners.ViewListener;
import com.qan.fiction.util.web.Web;

public abstract class Browser extends AppCompatListFragment implements Browsable {
    private ViewListener callback;
    protected String[] append;
    private String[] names;

    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        append = getResources().getStringArray(appendResource());
        names = getResources().getStringArray(categoryResource());
        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                names));
        setHasOptionsMenu(true);

    }

    protected abstract int appendResource();

    protected abstract int categoryResource();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = context instanceof Activity ? (Activity) context : null;

        try {
            callback = (ViewListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ViewListener");
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Bundle b = new Bundle();
        b.putString("url", getAddress(position));
        b.putString("name", names[position]);
        b.putString("append", append[position]);
        callback.openFragment(getNextFragment(position), b);
    }

    /**
     * Gets the next fragment to be opened
     *
     * @param position The position of the click in the list
     * @return The corresponding {@link ListFragment} to open
     */
    protected abstract ListFragment getNextFragment(int position);

    /**
     * Returns an absolute URL for the location of the link click
     *
     * @param position The position of the clicked item
     * @return The absolute URL
     */
    protected abstract String getAddress(int position);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        androidx.appcompat.app.ActionBar bar = getSupportActivity().getSupportActionBar();
        if (bar.getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
            if (bar.getTabCount() == 0)
                callback.setContent();
            bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }
        if (getArguments().getString("name") == null)
            bar.setTitle(getTitle());
        else
            bar.setTitle(getArguments().getString("name"));
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected abstract String getTitle();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.web_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.view_web_icon) {
            web_action();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void web_action() {
        String url;
        if (getArguments().getBoolean("dual"))
            url = getUrl();
        else
            url = getMobileUrl();
        Intent i = Web.web_intent(url);
        startActivity(i);
    }

    public abstract String getMobileUrl();

    public abstract String getUrl();
}
