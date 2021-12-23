package com.base.lib.base.mvvm.item_view_model;

import android.view.View;
import androidx.databinding.ObservableField;

import com.base.lib.base.command.BindingCommand;

/**
 * item条目ViewModel
 * Created by ge on 2018/2/5.
 */
public class FieldViewModel<T> {

    public int layout;
    public int id;
    public int pos;
    public int state;  //0 未添加,  1 已添加， 2 已删除
    public View view;

    public ObservableField<T> data = new ObservableField<>();
    public BindingCommand<FieldViewModel<T>> onClick;

}