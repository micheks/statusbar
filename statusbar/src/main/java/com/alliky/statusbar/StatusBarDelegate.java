package com.alliky.statusbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Build;
import android.view.Surface;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * @Description TODO
 * @Author wxianing
 * @Date 2021/3/12 0012 14:39
 * @Version 1.0
 */
public class StatusBarDelegate implements Runnable {

    private StatusBarUtils mStatusBarUtils;
    private BarProperties mBarProperties;
    private OnBarListener mOnBarListener;
    private int mNotchHeight;

    StatusBarDelegate(Object o) {
        if (o instanceof Activity) {
            if (mStatusBarUtils == null) {
                mStatusBarUtils = new StatusBarUtils((Activity) o);
            }
        } else if (o instanceof Fragment) {
            if (mStatusBarUtils == null) {
                if (o instanceof DialogFragment) {
                    mStatusBarUtils = new StatusBarUtils((DialogFragment) o);
                } else {
                    mStatusBarUtils = new StatusBarUtils((Fragment) o);
                }
            }
        } else if (o instanceof android.app.Fragment) {
            if (mStatusBarUtils == null) {
                if (o instanceof android.app.DialogFragment) {
                    mStatusBarUtils = new StatusBarUtils((android.app.DialogFragment) o);
                } else {
                    mStatusBarUtils = new StatusBarUtils((android.app.Fragment) o);
                }
            }
        }
    }

    StatusBarDelegate(Activity activity, Dialog dialog) {
        if (mStatusBarUtils == null) {
            mStatusBarUtils = new StatusBarUtils(activity, dialog);
        }
    }

    public StatusBarUtils get() {
        return mStatusBarUtils;
    }

    void onActivityCreated(Configuration configuration) {
        barChanged(configuration);
    }

    void onResume() {
        if (mStatusBarUtils != null) {
            mStatusBarUtils.onResume();
        }
    }

    void onDestroy() {
        mBarProperties = null;
        if (mStatusBarUtils != null) {
            mStatusBarUtils.onDestroy();
            mStatusBarUtils = null;
        }
    }

    void onConfigurationChanged(Configuration newConfig) {
        if (mStatusBarUtils != null) {
            mStatusBarUtils.onConfigurationChanged(newConfig);
            barChanged(newConfig);
        }
    }

    /**
     * 横竖屏切换监听
     * Orientation change.
     *
     * @param configuration the configuration
     */
    private void barChanged(Configuration configuration) {
        if (mStatusBarUtils != null && mStatusBarUtils.initialized() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mOnBarListener = mStatusBarUtils.getBarParams().onBarListener;
            if (mOnBarListener != null) {
                final Activity activity = mStatusBarUtils.getActivity();
                if (mBarProperties == null) {
                    mBarProperties = new BarProperties();
                }
                mBarProperties.setPortrait(configuration.orientation == Configuration.ORIENTATION_PORTRAIT);
                int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                if (rotation == Surface.ROTATION_90) {
                    mBarProperties.setLandscapeLeft(true);
                    mBarProperties.setLandscapeRight(false);
                } else if (rotation == Surface.ROTATION_270) {
                    mBarProperties.setLandscapeLeft(false);
                    mBarProperties.setLandscapeRight(true);
                } else {
                    mBarProperties.setLandscapeLeft(false);
                    mBarProperties.setLandscapeRight(false);
                }
                activity.getWindow().getDecorView().post(this);
            }
        }
    }

    @Override
    public void run() {
        if (mStatusBarUtils != null && mStatusBarUtils.getActivity() != null) {
            Activity activity = mStatusBarUtils.getActivity();
            BarConfig barConfig = new BarConfig(activity);
            mBarProperties.setStatusBarHeight(barConfig.getStatusBarHeight());
            mBarProperties.setNavigationBar(barConfig.hasNavigationBar());
            mBarProperties.setNavigationBarHeight(barConfig.getNavigationBarHeight());
            mBarProperties.setNavigationBarWidth(barConfig.getNavigationBarWidth());
            mBarProperties.setActionBarHeight(barConfig.getActionBarHeight());
            boolean notchScreen = NotchUtils.hasNotchScreen(activity);
            mBarProperties.setNotchScreen(notchScreen);
            if (notchScreen && mNotchHeight == 0) {
                mNotchHeight = NotchUtils.getNotchHeight(activity);
                mBarProperties.setNotchHeight(mNotchHeight);
            }
            mOnBarListener.onBarChange(mBarProperties);
        }
    }
}
