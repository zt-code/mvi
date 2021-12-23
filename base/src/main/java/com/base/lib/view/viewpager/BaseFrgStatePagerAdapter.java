package com.base.lib.view.viewpager;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.base.lib.net.L;

import java.util.List;

/**
 * 当切换页面的时候，FragmentStatePagerAdapter会remove之前加载的fragment从而将内存释放掉。
 * 而FragmentPagerAdapter不会remove掉fragment而只是detach，仅仅是在页面上让fragment的UI脱离Activity的UI，但是fragment仍然保存在内存里，并不会回收内存。
 * 这里我自己重写了destroyItem方法做到remove与detach灵活切换
 * @param <T>
 */
public class BaseFrgStatePagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private FragmentManager fragmentManager;
    private List<T> fragments;
    private JSONArray tabs;
    private boolean isDestroy = true;

    public BaseFrgStatePagerAdapter(@NonNull FragmentManager fm, int behavior, List<T> fragments, JSONArray array) {
        super(fm, behavior);
        this.fragmentManager = fm;
        this.fragments = fragments;
        this.tabs = array;
    }

    public BaseFrgStatePagerAdapter(FragmentManager fm, List<T> fragments, JSONArray array) {
        super(fm);
        this.fragmentManager = fm;
        this.fragments = fragments;
        this.tabs = array;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        /*BaseFragment fragment = (BaseFragment) fragments.get(position);
        T frg = null;
        try {
            frg = (T) fragments.get(position).getClass().newInstance();
            BaseFragment baseFragment = (BaseFragment) frg;
            baseFragment.isFirstLoad = true;
            baseFragment.isVpChild = true;
            L.i("why","=============instantiateItem  "+fragment.getArguments().toString());
            baseFragment.setArguments(fragment.getArguments());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        //删除原有Frg
        fragments.remove(position);
        fragments.add(position, frg);*/

        if(!isDestroy) {
            //L.i("!isDestroy");
            fragmentManager.beginTransaction().show(fragments.get(position)).commitAllowingStateLoss();
        }else {
            //L.i("isDestroy");
        }

        return super.instantiateItem(container, position);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return super.isViewFromObject(view, object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(!isDestroy) {
            //隐藏UI布局
            fragmentManager.beginTransaction().hide(getItem(position)).commitAllowingStateLoss();

        }else {
            //销毁UI布局
            super.destroyItem(container, position, object);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /*private Class getIns() {
        Class modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            modelClass = Fragment.class;
        }
        return modelClass;
    }*/

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = tabs.getString(position);
        return title;
    }

    public JSONArray getTabs() {
        return tabs;
    }

    public void isDestroyItem(boolean tag) {
        isDestroy = tag;
    }


}