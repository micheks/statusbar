package com.alliky.statusbar;

/**
 * @Description TODO 软键盘监听
 * @Author wxianing
 * @Date 2021/3/12 0012 14:30
 * @Version 1.0
 */
public interface OnKeyboardListener {
    /**
     * On keyboard change.
     *
     * @param isPopup        the is popup  是否弹出
     * @param keyboardHeight the keyboard height  软键盘高度
     */
    void onKeyboardChange(boolean isPopup, int keyboardHeight);
}
