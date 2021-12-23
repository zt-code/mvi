package com.base.lib.base.bind;


import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.databinding.BindingAdapter;

import com.base.lib.base.command.BindingCommand;
import com.base.lib.net.L;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;
import io.reactivex.functions.Consumer;


/**
 * Created by goldze on 2017/6/16.
 */

public class ViewAdapter {

    //防重复点击间隔(秒)
    public static final int CLICK_INTERVAL = 1;

    @BindingAdapter("onEnabled")
    public static void onEnabled(View textView, boolean tag) {
        textView.setEnabled(tag);
    }

    /**
     * requireAll 是意思是是否需要绑定全部参数, false为否
     * View的onClick事件绑定
     * onClickCommand 绑定的命令,
     * isThrottleFirst 是否开启防止过快点击
     */
    @BindingAdapter(value = {"onClickCommand", "isThrottleFirst"}, requireAll = false)
    public static void onClickCommand(View view, final BindingCommand clickCommand, final boolean isThrottleFirst) {
        if (isThrottleFirst) {
            RxView.clicks(view)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            if (clickCommand != null) {
                                clickCommand.execute();
                            }
                        }
                    });

        } else {
            RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            if (clickCommand != null) {
                                clickCommand.execute();
                            }
                        }
                    });
        }
    }

    @BindingAdapter(value = {"onClickView", "isThrottleFirst"}, requireAll = false)
    public static <T> void onClickView(View view, final BindingCommand clickCommand, final boolean isThrottleFirst) {
        if (isThrottleFirst) {
            RxView.clicks(view)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            L.i("============点击1");
                            if(clickCommand != null) clickCommand.execute(view);
                        }
                    });

        } else {
            RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            L.i("============点击1");
                            if(clickCommand != null) clickCommand.execute(view);
                        }
                    });
        }
    }

    /*@BindingAdapter(value = {"onClickView", "isThrottleFirst"}, requireAll = false)
    public static <T> void onClickView(View view, FieldViewModel<T> model, final boolean isThrottleFirst) {
        if (isThrottleFirst) {
            RxView.clicks(view)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            L.i("============点击1");
                            if(model != null && model.onClick != null) {
                                model.id = view.getId();
                                model.onClick.execute(model);
                            }
                        }
                    });
        } else {
            RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            L.i("============点击1");
                            if(model != null && model.onClick != null) {
                                model.id = view.getId();
                                model.onClick.execute(model);
                            }
                        }
                    });
        }
    }*/

    /**
     * view的onLongClick事件绑定
     */
    @BindingAdapter(value = {"onLongClickCommand"}, requireAll = false)
    public static void onLongClickCommand(View view, final BindingCommand clickCommand) {
        RxView.longClicks(view)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        if (clickCommand != null) {
                            clickCommand.execute();
                        }
                    }
                });
    }

    /**
     * 设置背景
     * @param view
     * @param resid
     */
    @BindingAdapter("onBackground")
    public static void onBackground(View view, int resid) {
        if(resid != 0) {
            view.setBackgroundResource(resid);
        }
    }

    /**
     * 设置背景
     * @param view
     * @param drawable
     */
    @BindingAdapter("onBackground")
    public static void onBackground(View view, Drawable drawable) {
        if(drawable != null) {
            view.setBackground(drawable);
        }
    }

    /**
     * 回调控件本身
     *
     * @param currentView
     * @param bindingCommand
     */
    @BindingAdapter(value = {"currentView"}, requireAll = false)
    public static void replyCurrentView(View currentView, BindingCommand bindingCommand) {
        if (bindingCommand != null) {
            bindingCommand.execute(currentView);
        }
    }

    /**
     * view是否需要获取焦点
     */
    @BindingAdapter({"requestFocus"})
    public static void requestFocusCommand(View view, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        } else {
            view.clearFocus();
        }
    }

    /**
     * view的焦点发生变化的事件绑定
     */
    @BindingAdapter({"onFocusChangeCommand"})
    public static void onFocusChangeCommand(View view, final BindingCommand<Boolean> onFocusChangeCommand) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (onFocusChangeCommand != null) {
                    onFocusChangeCommand.execute(hasFocus);
                }
            }
        });
    }

    @BindingAdapter(value = {"onVisible"}, requireAll = false)
    public static void onVisible(View view, final int visibility) {
        view.setVisibility(visibility);
    }

    /**
     * view的显示隐藏
     */
    @BindingAdapter(value = {"onVisible"}, requireAll = false)
    public static void isVisible(View view, final Boolean visibility) {
        if(visibility == null) return;
        view.setVisibility(visibility?View.VISIBLE:View.GONE);
    }

//    @BindingAdapter({"onTouchCommand"})
//    public static void onTouchCommand(View view, final ResponseCommand<MotionEvent, Boolean> onTouchCommand) {
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (onTouchCommand != null) {
//                    return onTouchCommand.execute(event);
//                }
//                return false;
//            }
//        });
//    }

}
