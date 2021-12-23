package com.base.lib.view.edittext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Kevin on 2016/6/12.
 * EditText如果限制了最大输入，超出的话，给出相应的提示
 */

@SuppressLint("AppCompatCustomView")
public class MyEditText extends EditText {

    private boolean isFirst = true;
    private boolean openListener;
    private EditTextListener listener;

    public String namespace = "http://schemas.android.com/apk/res/android"; //命名空间（别告诉我不熟悉）
    public MyFilter myFilter = null;
    public MyWatcher myWatcher = null;

    private int maxLength = 999999; //允许输入长度
    private boolean openToast = true; //开启输入提示
    private boolean openEmoji = false; //允许输入表情符
    private String regex; //格式验证正则表达式

    private final int[] atrr = new int[]{
            android.R.attr.scrollbars,
    };

    public MyEditText(Context context) {
        this(context, null);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        maxLength = attrs.getAttributeIntValue(namespace, "maxLength", 999999);
        String scrollbars = attrs.getAttributeValue(namespace, "scrollbars");
        initScrollbars(scrollbars);
        initFilter();
        initWatcher();
        initListener(context);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);;
        maxLength = attrs.getAttributeIntValue(namespace, "maxLength", 999999);
        String scrollbars = attrs.getAttributeValue(namespace, "scrollbars");
        initScrollbars(scrollbars);
        initFilter();
        initWatcher();
        initListener(context);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        isFirst = false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //initJsonSet();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //initJsonSet();
    }

    /*private void initJsonSet() {
        jsonSet = getRLayout(getParent());
        if(jsonSet != null) {
            if(jsonSet.containsKey("maxLength")) maxLength = jsonSet.getIntValue("maxLength");
            if(jsonSet.containsKey("input")) {
                String input = jsonSet.getString("input");
                if(input.equals("decimal")) {
                    setInputType(EditorInfo.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                }else if(input.equals("number")) {
                    setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }
            if(jsonSet.containsKey("text")) setText(jsonSet.getString("text"));
            if(jsonSet.containsKey("hint")) setHint(jsonSet.getString("hint"));
            L.i("====onAttachedToWindow  "+jsonSet.toString());
        }
    }

    private JSONObject getRLayout(ViewParent parent) {
        if(parent == null) return null;
        if(parent instanceof BaseView) {
            BaseView ilayout = (BaseView) parent;
            return ilayout.getJsonSet(getId());
        }else {
            return getRLayout(parent.getParent());
        }
    }*/

    private void initScrollbars(String str) {
        if(str != null && !str.equals("null")) {
            setOnTouchListener((v, event) -> {
                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
                    //按下或滑动时请求父节点不拦截子节点
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //抬起时请求父节点拦截子节点
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            });
        }
    }

    public void setMaxLength(int length) {
        this.maxLength = length;
    }

    public void setRegex(String regex) {
        this.regex = regex;
        if(myWatcher != null){
            myWatcher.setRegMatch(regex);
        }
    }

    //允许弹框提示
    public void openToast(boolean tag) {
        openToast = tag;
    }

    //允许输入表情
    public void openEmoji(boolean tag) {
        openEmoji = tag;
    }

    //允许监听回调
    public void openListener(boolean tag) {
        openListener = tag;
    }

    //设置监听
    public void setListener(EditTextListener listener) {
        openListener = true;
        this.listener = listener;
    }

    public void initListener(final Context context) {
        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEND){
                    //关闭软键盘
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getWindowToken(), 0);
                    if(openListener && listener != null) listener.onEditorAction(v, actionId, event);
                }
                return false;
            }
        });
    }

    public void initWatcher() {
        myWatcher = new MyWatcher(this);
        addTextChangedListener(myWatcher);
    }

    public void initFilter() {
        myFilter = new MyFilter(this);
        setFilters(new InputFilter[]{myFilter});
    }

    /**
     * 用户输入拦截器
     */
    public class MyFilter implements InputFilter {

        /*private String format = "[a-zA-Z0-9]{0,20}"; //验证账号输入
        private String regex = "(^[1-9][0-9]{0,"+2+"}(\\.[0-9]{0,"+2+"})?)|(^0(\\.[0-9]{0,"+2+"})?)"; //验证Price输入*/

        private MyEditText editText;


        public MyFilter(MyEditText editText) {
            this.editText = editText;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            /*source 新输入的字符串
            start 新输入的字符串起始下标，一般为0
            end 新输入的字符串终点下标，一般为source长度-1
            dest 输入之前文本框内容
            dstart 原内容起始坐标，一般为0
            dend 原内容终点坐标，一般为dest长度-1*/

            //禁止输入表情
            if(!openEmoji) {
                if(containsEmoji(source.toString())) {
                    Log.i("why", "禁止输入表情");
                    return "";
                }
            }

            //长度限制验证
            int keep = maxLength - (dest.length() - (dend - dstart));
            if (keep <= 0) {
                //输入长度越界
                if(openToast) {
                    Toast.makeText(editText.getContext(), "字数不能超过" + maxLength + "位", Toast.LENGTH_SHORT).show();
                }
                return "";
            } else if (keep >= end - start) {
                //正常输入中
                return null;
            } else {
                keep += start;
                if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                    --keep;
                    if (keep == start) {
                        return "";
                    }
                }
                return source.subSequence(start, keep);
            }
        }

    }

    /**
     * 用户输入验证器
     */
    public class MyWatcher implements TextWatcher {

        /*private String format = "[a-zA-Z0-9]{0,20}"; //验证账号输入
        private String regex = "(^[1-9][0-9]{0,"+2+"}(\\.[0-9]{0,"+2+"})?)|(^0(\\.[0-9]{0,"+2+"})?)"; //验证Price输入*/

        private EditText mEditText;
        private String beforeText; //输入前的文本内容

        Pattern pattern = null;

        public MyWatcher(EditText editText) {
            this.mEditText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // 输入前的字符
            beforeText = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        public void setRegMatch(String format){
            pattern = Pattern.compile(format);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // 输入后的字符
            String afterText = s.toString();
            //Log.i("why", afterText + "          regex  " + regex);
            // 光标移动到文本末尾
            mEditText.setSelection(afterText.length());

            boolean isValid = true;
            if (!TextUtils.isEmpty(afterText)) {
                if(regex != null && pattern != null) {
                    isValid = isMatches(afterText);
                }
                if (!isValid) {
                    // 用户现在输入的字符数减去之前输入的字符数，等于新增的字符数
                    int differ = afterText.length() - beforeText.length();
                    // 如果用户的输入不符合规范，则显示之前输入的文本
                    mEditText.setText(beforeText);
                    // 光标移动到文本末尾
                    mEditText.setSelection(afterText.length() - differ);
                }
            }
            if(!openListener) return;
            if(listener != null) listener.onTextSizeChange(mEditText, mEditText.getText().length(), getText().toString());
        }

        /**
         * 字符串是否符合正则表达式的规则
         * @param text 匹配文本
         * @return true 匹配成功 flase 匹配失败
         */
        private boolean isMatches(String text) {
            Matcher m = pattern.matcher(text);
            return m.matches();
        }

    }

    /**
     * 判断是否含有表情
     * @param source
     * @return
     */
    public boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

}