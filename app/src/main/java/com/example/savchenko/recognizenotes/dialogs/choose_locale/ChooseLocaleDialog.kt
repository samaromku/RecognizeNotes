package com.example.savchenko.recognizenotes.dialogs.choose_locale

import android.app.DialogFragment
import android.content.ContentValues.TAG
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.savchenko.recognizenotes.R
import com.example.savchenko.recognizenotes.interfaces.LocaleSetter
import kotlinx.android.synthetic.main.dialog_choose_locale.*
import ru.savchenko.andrey.realenglish.interfaces.OnItemClickListener
import java.util.*

/**
 * Created by savchenko on 09.01.18.
 */
class ChooseLocaleDialog : DialogFragment(), OnItemClickListener {
    lateinit var localSetter:LocaleSetter
    lateinit var localList:MutableList<Locale>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (dialog.window != null) {
            dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return inflater?.inflate(R.layout.dialog_choose_locale, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvLocale.layoutManager = LinearLayoutManager(activity)
        localList = changePlacesRussianAndEnglish(Locale.getAvailableLocales().toMutableList())

        val adapter = ChooseLocaleAdapter(localList)
        adapter.clickListener = this
        rvLocale.adapter = adapter

    }

    fun changePlacesRussianAndEnglish(startLocals: MutableList<Locale>) :MutableList<Locale>{
        var rus : Locale? = null
        var eng : Locale? = null
        for (item in startLocals) {
            when(item.displayName.trim()){
                "русский (Россия)" -> {rus = item}
                "английский (Великобритания)" -> {eng = item}
            }
        }
        startLocals.add(0, rus!!)
        startLocals.add(1, eng!!)
        return startLocals
    }

    override fun onItemClick(position: Int) {
        localSetter.setLocal(localList.get(position))
        dialog.dismiss()
    }
}