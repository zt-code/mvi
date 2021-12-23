package com.base.lib.view.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


open abstract class BaseAdapter<T, BV>(private val listener: (View) -> Unit) : ListAdapter<T, BaseViewHolder<BV>>(NewsItemItemCallback<T>()) {

    var context: Context? = null;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BV> {
        context = parent.context;
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), viewType, parent, false)
        val holder = BaseViewHolder<BV>(binding.root, listener);
        holder.viewType = viewType;
        holder.binding = binding as BV;
        return holder;
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BV>, position: Int) {
        onBindViewHolder(holder, getItem(position), position)
    }

    open fun onBindViewHolder(holder: BaseViewHolder<BV>, item: T, position: Int) {

    }

    override fun getItemCount() = currentList.size

    internal class NewsItemItemCallback<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }

}

