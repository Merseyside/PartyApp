package com.merseyside.partyapp.presentation.view.activity.main.model

import android.content.Context
import android.os.Bundle
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.merseyLib.presentation.model.ParcelableViewModel
import com.merseyside.merseyLib.utils.serialization.deserialize
import com.merseyside.merseyLib.utils.serialization.serialize
import com.merseyside.partyapp.CalcApplication

class SharedViewModel : ParcelableViewModel() {

    val application = CalcApplication.getInstance()

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

    override fun getLocaleContext(): Context {
        return application
    }

    var eventContainer: Event? = null

    var itemContainer: Item? = null

    companion object {
        private const val EVENT_KEY = "event"
        private const val ITEM_KEY = "item"
    }
}