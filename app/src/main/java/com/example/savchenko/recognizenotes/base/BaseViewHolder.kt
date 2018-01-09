package ru.savchenko.andrey.realenglish.base

import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.View
import ru.savchenko.andrey.realenglish.interfaces.OnItemClickListener

/**
 * Created by savchenko on 27.11.17.
 */
open class BaseViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    lateinit var clickListener:OnItemClickListener;

    override fun onClick(p0: View?) {
        clickListener.onItemClick(adapterPosition)
    }

    open fun bind(t:T, clickListener: OnItemClickListener){
        this.clickListener = clickListener;
    }

    init {
        itemView.setOnClickListener(this)
    }
}