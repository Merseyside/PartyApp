package com.merseyside.partyapp.presentation.view.activity.main.model

import android.app.Application
import android.content.Context
import android.os.Bundle
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.archy.presentation.model.ParcelableViewModel
import com.merseyside.partyapp.CalcApplication
import com.merseyside.utils.serialization.deserialize
import com.merseyside.utils.serialization.serialize

class SharedViewModel(application: Application) : ParcelableViewModel(application) {

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