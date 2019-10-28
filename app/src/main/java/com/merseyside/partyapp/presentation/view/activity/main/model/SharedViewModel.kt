package com.merseyside.partyapp.presentation.view.activity.main.model

import androidx.lifecycle.ViewModel
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.data.db.item.Item

class SharedViewModel : ViewModel() {

    var eventContainer: Event? = null

    var itemContainer: Item? = null

}