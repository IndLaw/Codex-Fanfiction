package com.qan.fiction.ui.ff_activity.norm;

import androidx.fragment.app.ListFragment;

import com.qan.fiction.R;
import com.qan.fiction.ui.abs_web_activity.Browser;

public class FF_Browser extends Browser {

    @Override
    public int appendResource() {
        return R.array.ff_net_append_norm;
    }

    @Override
    public int categoryResource() {
        return R.array.ff_net_categories;
    }


    @Override
    public ListFragment getNextFragment(int position) {
        return new FF_Categories();
    }

    @Override
    public String getAddress(int position) {
        return "https://www.fanfiction.net/" + append[position];
    }


    public String getTitle() {
        return getString(R.string.ff_net);
    }

    public String getMobileUrl() {
        return "https://m.fanfiction.net/";
    }

    public String getUrl() {
        return "https://www.fanfiction.net/";
    }
}
