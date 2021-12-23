package com.base.lib.view.recycler_view

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<VB>(containerView: View, listener: (View) -> Unit) : RecyclerView.ViewHolder(containerView) {

    var viewType = -1;
    var binding:VB? = null;

    init {
        itemView.setOnClickListener(listener)
    }

}