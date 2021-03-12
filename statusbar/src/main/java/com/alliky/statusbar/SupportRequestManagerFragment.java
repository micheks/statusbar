package com.alliky.statusbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Description TODO
 * @Author wxianing
 * @Date 2021/3/12 0012 14:40
 * @Version 1.0
 */
public final class SupportRequestManagerFragment extends Fragment {

    private StatusBarDelegate mDelegate;

    public StatusBarUtils get(Object o) {
        if (mDelegate == null) {
            mDelegate = new StatusBarDelegate(o);
        }
        return mDelegate.get();
    }

    public StatusBarUtils get(Activity activity, Dialog dialog) {
        if (mDelegate == null) {
            mDelegate = new StatusBarDelegate(activity, dialog);
        }
        return mDelegate.get();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mDelegate != null) {
            mDelegate.onActivityCreated(getResources().getConfiguration());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mDelegate != null) {
            mDelegate.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDelegate != null) {
            mDelegate.onDestroy();
            mDelegate = null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDelegate != null) {
            mDelegate.onConfigurationChanged(newConfig);
        }
    }
}
