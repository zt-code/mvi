package com.base.lib.base.bind;


import android.graphics.Color;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;

import com.base.lib.base.command.BindingCommand;
import com.base.lib.view.edittext.EditTextListener;
import com.base.lib.view.edittext.MyEditText;


/**
 * Created by goldze on 2017/6/16.
 */

public class TextAdapter {

    @BindingAdapter(value = {"onText","onTextColor", "onTextPaint"}, requireAll = false)
    public static void onText(TextView view, String str, String colorName, int textPaint) {
        //设置文字
        if(str != null && !str.equals("")) {
            if(str.equals("null")) str = "";
            view.setText(str);
        }

        //设置颜色
        if(colorName != null && colorName.length() > 0) {
            view.setTextColor(Color.parseColor(colorName));
        }

        //设置下划线等样式
        if(textPaint > 0) {
            view.getPaint().setFlags(textPaint); //中划线
            //view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            view.getPaint().setAntiAlias(true);//抗锯齿
        }
    }

    @BindingAdapter(value = {"onText","onTextColor", "onTextPaint"}, requireAll = false)
    public static void onText(TextView view, String str, int colorName, int textPaint) {
        //设置文字
        if(str != null && !str.equals("")) {
            if(str.equals("null")) str = "";
            view.setText(str);
        }

        //设置颜色
        if(colorName > 0) {
            view.setTextColor(view.getResources().getColor(colorName));
        }

        //设置下划线等样式
        if(textPaint > 0) {
            view.getPaint().setFlags(textPaint); //中划线
            //view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            view.getPaint().setAntiAlias(true);//抗锯齿
        }
    }

    @BindingAdapter(value = {"onTextInput"}, requireAll = false)
    public static void onTextInput(MyEditText textView, final BindingCommand clickCommand) {
        textView.setListener(new EditTextListener() {
            @Override
            public void onTextSizeChange(TextView v, int num, String str) {
                super.onTextSizeChange(v, num, str);
                if(clickCommand != null) clickCommand.execute(str);
            }
        });
    }

}
