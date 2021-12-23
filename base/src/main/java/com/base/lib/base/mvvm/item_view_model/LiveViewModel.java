package com.base.lib.base.mvvm.item_view_model;

import androidx.annotation.NonNull;

import com.base.lib.base.bus.SingleLiveEvent;
import com.zt.mvvm.mvvm.BaseRepository;
import com.zt.mvvm.mvvm.BaseVM;
import com.zt.mvvm.mvvm.multi_view_model.MultiViewModel;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * item条目ViewModel
 * Created by ge on 2018/2/5.
 */
public class LiveViewModel<T> extends MultiViewModel {

    public SingleLiveEvent<T> event = new SingleLiveEvent<>();

    public LiveViewModel(@NonNull BaseVM viewModel) {
        super(viewModel);
        event.setValue(createT());
    }

    public LiveViewModel(@NonNull BaseVM viewModel, T json) {
        super(viewModel);
        event.setValue(json);
    }

    public T createT() {
        T t = null;
        try {
            t = (T) getTypeClass().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return t;
    }

    public Class getTypeClass() {
        Class modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            modelClass = BaseRepository.class;
        }
        //L.i( "====getIns  "+modelClass.getCanonicalName());
        return modelClass;
    }

}