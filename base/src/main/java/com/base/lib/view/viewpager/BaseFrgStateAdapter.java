package com.base.lib.view.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * 当切换页面的时候，FragmentStatePagerAdapter会remove之前加载的fragment从而将内存释放掉。
 * 而FragmentPagerAdapter不会remove掉fragment而只是detach，仅仅是在页面上让fragment的UI脱离Activity的UI，但是fragment仍然保存在内存里，并不会回收内存。
 * 这里我自己重写了destroyItem方法做到remove与detach灵活切换
 * @param <T>
 */
public class BaseFrgStateAdapter<T extends Fragment> extends FragmentStateAdapter {

    private List<T> fragments;

    public BaseFrgStateAdapter(@NonNull @NotNull FragmentActivity fragmentActivity, List<T> fragments) {
        this(fragmentActivity.getSupportFragmentManager(), fragmentActivity.getLifecycle(), fragments);
    }

    public BaseFrgStateAdapter(@NonNull @NotNull Fragment fragment, List<T> fragments) {
        this(fragment.getChildFragmentManager(), fragment.getLifecycle(), fragments);
    }

    public BaseFrgStateAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle, List<T> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FragmentViewHolder holder, int position, @NonNull @NotNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        try {
            T fragment = (T) fragments.get(position);
            T frg = (T) fragments.get(position).getClass().newInstance();
            frg.setArguments(fragment.getArguments());
            return frg;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }


    /*@Override
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
    }*/

}