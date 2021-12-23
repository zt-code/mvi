package com.zt.mvi.demo.refresh

import android.view.View
import com.base.lib.view.recycler_view.BaseAdapter
import com.base.lib.view.recycler_view.BaseViewHolder
import com.zt.mvi.R
import com.zt.mvi.databinding.ItemViewBinding
import com.zt.mvi.demo.bean.Data

class DemoAdapter(listener: (View) -> Unit) : BaseAdapter<Data, ItemViewBinding>(listener) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_view
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ItemViewBinding>, item: Data, position: Int) {
        super.onBindViewHolder(holder, item, position)
        holder.itemView.setTag(item)
        holder.binding?.tv?.text = item.name
    }

}