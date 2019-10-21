package com.merseyside.partyapp.presentation.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.merseyside.partyapp.presentation.view.fragment.addEvent.view.AddEventFragment
import com.merseyside.partyapp.presentation.view.fragment.eventList.view.EventListFragment
import com.merseyside.partyapp.presentation.view.fragment.itemList.view.ItemListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class EventListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return EventListFragment.newInstance()
        }
    }

    class AddEventScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {

            return AddEventFragment.newInstance()
        }
    }

    class EditEventScreen(val id: Long) : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            val bundle = Bundle().apply {
                putLong(AddEventFragment.KEY_EDIT_ID, id)
            }

            val fragment = AddEventFragment.newInstance()
            fragment.arguments = bundle

            return fragment
        }
    }

    class ItemListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {

            return ItemListFragment.newInstance()
        }
    }
}