package com.qan.fiction.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.qan.fiction.R;
import com.qan.fiction.custom.AlertBuilder;
import com.qan.fiction.util.constants.Settings;
import com.qan.fiction.util.download.Connector;
import com.qan.fiction.util.download.StoryDownload;
import com.qan.fiction.util.misc.listeners.ViewListener;
import com.qan.fiction.util.storage.ReaderWrapper;
import com.qan.fiction.util.web.Web;
import com.qan.fiction.util.web.reader_data_manager.DataManager;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

import static com.qan.fiction.util.constants.Constants.EX_SIGNAL;

public abstract class OnlineReaderFragment extends ReaderFragment {


    private ViewListener callback;


    private boolean dualPane;
    private DataManager dataManager;

    public boolean isDualPane() {
        return dualPane;
    }

    private void setDualPane(boolean dualPane) {
        this.dualPane = dualPane;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = getManagerInstance();
    }

    protected class OnlinePage extends Page {
        @Override
        protected CharSequence doInBackground(Void... params) {
            try {
                setDocument(Connector.getUrl(getUrl()));
                return Html.fromHtml(getContent());
            } catch (Exception e) {
                e.printStackTrace();
                return EX_SIGNAL;
            }
        }

        @Override
        protected void onPostExecute(CharSequence s) {
            super.onPostExecute(s);
            if (getDocument() != null && !s.equals(EX_SIGNAL) && getChapters() == 0) {
                Settings.setLastSite(getActivity(), getSite());
                String title = getTitle();
                Settings.setOnlineInfo(getActivity(), new ReaderWrapper(getLocation(), title, getStoryId(), getChapters()));
                getSupportActivity().getSupportActionBar().setTitle(title);
                setChapters(getChapterCount());
                getArguments().putInt("chapters", getChapters());
                getArguments().putString("title", title);
                getActivity().invalidateOptionsMenu();
            }
        }

        @Override
        protected void makeError() {
            AlertBuilder builder = new AlertBuilder(getActivity());
            builder.setTitle(R.string.alert);
            if (Connector.isNetworkAvailable(getActivity()))
                builder.setMessage(R.string.loading_failed);
            else
                builder.setMessage(getString(R.string.no_internet));
            builder.setPositiveButton(getString(R.string.ok), null);
            builder.setNegativeButton(getString(R.string.cancel), null);
            builder.create().show();
        }
    }


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

    @Override
    public void initialize(Bundle saved, Bundle b) {
        setHasOptionsMenu(true);
        getSupportActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (b != null) {
            setDualPane(b.getBoolean("dual"));
            if (run(b, saved))
                return;
        } else if (saved != null) {
            //Does this ever run?
            if (run(saved, saved))
                return;

        }
        if (getLocation() != null) {
            Settings.setLastSite(getActivity(), getSite());
            String title = getArguments().getString("title");
            Settings.setOnlineInfo(getActivity(), new ReaderWrapper(getLocation(), title, getStoryId(), getChapters()));
        }
        getActivity().invalidateOptionsMenu();
    }

    private String getContent() {
        return dataManager.getContent();
    }

    private String getUrl() {
        System.out.println(dataManager.getUrl());
        return dataManager.getUrl();
    }

    private int getSite() {
        return dataManager.getSite();
    }

    private String getTitle() {
        return dataManager.getTitle();
    }

    private int getChapterCount() {
        return dataManager.getChapterCount();
    }

    @Override
    public Page getDownloadInstance() {
        return new OnlinePage();
    }


    boolean run(Bundle b, Bundle saved) {
        getInfo(b);

        if (saved == null || reset(saved)) {
            int x = getResources().getConfiguration().orientation;
            int y = getActivity().getRequestedOrientation();
            if (y == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE && x != Configuration.ORIENTATION_LANDSCAPE ||
                    y == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT && x != Configuration.ORIENTATION_PORTRAIT)
                return true;
            scrollToPosition = true;
            setContent();
        }
        return false;
    }

    private void getInfo(Bundle b) {
        setLocation(b.getString("url"));
        setStoryId(b.getInt("id"));
        setPage(Settings.getPage(getActivity(), getLocation()));
        setChapters(b.getInt("chapters"));
        getSupportActivity().getSupportActionBar().setTitle(b.getString("title"));
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putInt("chapters", getChapters());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater m) {
        inflateMenu(menu, m, R.menu.reader_online); //It's ok since the reader should be fullscreen
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_story:
                Intent intent = new Intent(getActivity(), StoryDownload.class);
                intent.putExtra("id", getArguments().getInt("id"));
                intent.putExtra("site", getArguments().getString("site"));
                intent.putExtra("download", true);
                callback.startDownloadService(intent);
                return true;
            case R.id.view_web_icon:
                web_action();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ArrayList<String> getStrings() {
        ArrayList<String> s = super.getStrings();
        for (int i = getChapters() + 1; i <= getPage(); i++) {
            s.add("Chapter " + i);
        }
        return s;
    }

    @Override
    void setPage(int page) {
        super.setPage(page);
        dataManager.setPage(page);
    }



    @Override
    void setLocation(String location) {
        super.setLocation(location);
        dataManager.setFirstPage(location);
    }

    protected abstract DataManager getManagerInstance();

    private void web_action() {
        String url;
        url = dataManager.getWebUrl();
        Intent i = Web.web_intent(url);
        startActivity(i);
    }


    protected Document getDocument() {
        return dataManager.getDocument();
    }

    protected void setDocument(Document document) {
        dataManager.setDocument(document);
    }

    private int getStoryId() {
        return dataManager.getStoryId();
    }

    private void setStoryId(int storyId) {
        dataManager.setStoryId(storyId);
    }


}
