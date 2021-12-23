package com.base.lib.view.edittext;

import android.view.KeyEvent;
import android.widget.TextView;

public interface EditTextImpl {
    void onEditorAction(TextView v, int actionId, KeyEvent event);
    void onTextSizeChange(TextView v, int num, String str);
}