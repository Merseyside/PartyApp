package com.merseyside.partyapp.presentation.view.activity.main.model

import android.content.Context
import android.os.Bundle
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.item.Item
import com.upstream.basemvvmimpl.data.cache.serializer.Serializer
import com.upstream.basemvvmimpl.presentation.model.ParcelableViewModel

class SharedViewModel : ParcelableViewModel() {
    override fun readFrom(bundle: Bundle) {
        val serializer = Serializer()

        if (bundle.containsKey(EVENT_KEY)) {
            eventContainer = serializer.deserialize(bundle.getString(EVENT_KEY)!!, Event::class.java)
        }

        if (bundle.containsKey(ITEM_KEY)) {
            itemContainer = serializer.deserialize(bundle.getString(ITEM_KEY)!!, Item::class.java)
        }
    }

    override fun writeTo(bundle: Bundle) {
        val serializer = Serializer()

        if (eventContainer != null) {
            bundle.putString(EVENT_KEY, serializer.serialize(eventContainer!!, Event::class.java))
        }

        if (itemContainer != null) {
            bundle.putString(ITEM_KEY, serializer.serialize(itemContainer!!, Item::class.java))
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