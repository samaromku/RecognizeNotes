package ru.savchenko.andrey.realenglish.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ru.savchenko.andrey.realenglish.interfaces.OnItemClickListener

/**
 * Created by savchenko on 27.11.17.
 */
open class BaseAdapter<T>(val data:List<T>)
    : RecyclerView.Adapter<BaseViewHolder<T>>() {
    lateinit var clickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<T>? {
        return null
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>?, position: Int) {
        holder?.bind(data.get(position), clickListener)
    }

    override fun getItemCount(): Int = data.size

}