package com.example.savchenko.recognizenotes.dialogs.choose_locale

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.savchenko.recognizenotes.R
import ru.savchenko.andrey.realenglish.base.BaseAdapter
import ru.savchenko.andrey.realenglish.base.BaseViewHolder
import ru.savchenko.andrey.realenglish.interfaces.OnItemClickListener
import java.util.*


/**
 * Created by savchenko on 09.01.18.
 */
class ChooseLocaleAdapter(locales:List<Locale>) : BaseAdapter<Locale>(locales) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<Locale>? {
        return LocaleHolder(LayoutInflater
                .from(parent?.context).inflate(R.layout.item_locale, parent, false))
    }

    class LocaleHolder(itemView: View): BaseViewHolder<Locale>(itemView){
        val tvLocale: TextView = itemView.findViewById(R.id.tvLocale)

        override fun bind(t: Locale, clickListener: OnItemClickListener) {
            super.bind(t, clickListener)
            tvLocale.text = t.displayName
        }
    }
}