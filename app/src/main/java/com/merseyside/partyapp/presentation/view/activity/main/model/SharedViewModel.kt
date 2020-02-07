package com.merseyside.partyapp.presentation.view.activity.main.model

import android.content.Context
import android.os.Bundle
import com.merseyside.mvvmcleanarch.data.serialization.deserialize
import com.merseyside.mvvmcleanarch.data.serialization.serialize
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.mvvmcleanarch.presentation.model.ParcelableViewModel
import com.merseyside.partyapp.CalcApplication

class SharedViewModel : ParcelableViewModel() {

    override val application = CalcApplication.getInstance()

    override fun readFrom(bundle: Bundle) {

        bundle.apply {
            if (bundle.containsKey(EVENT_KEY)) {
                eventContainer = getString(EVENT_KEY)!!.deserialize()
            }

            if (bundle.containsKey(ITEM_KEY)) {
                itemContainer = getString(ITEM_KEY)!!.deserialize()
            }
        }
    }

    override fun writeTo(bundle: Bundle) {
        bundle.apply {

            if (eventContainer != null) {
                bundle.putString(
                    EVENT_KEY,
                    eventContainer!!.serialize()
                )
            }

            if (itemContainer != null) {
                itemContainer!!.serialize()
            }
        }
    }

    override fun dispose() {}

    override fun updateLanguage(context: Context) {}

    var eventContainer: Event? = null

    var itemContainer: Item? = null

    companion object {
        private const val EVENT_KEY = "event"
        private const val ITEM_KEY = "item"
    }
}