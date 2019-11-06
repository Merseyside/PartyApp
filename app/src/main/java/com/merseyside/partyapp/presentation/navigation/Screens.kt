package com.merseyside.partyapp.presentation.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.merseyside.partyapp.presentation.view.fragment.addEvent.view.AddEventFragment
import com.merseyside.partyapp.presentation.view.fragment.addItem.view.AddItemFragment
import com.merseyside.partyapp.presentation.view.fragment.eventList.view.EventListFragment
import com.merseyside.partyapp.presentation.view.fragment.itemList.view.ItemListFragment
import com.merseyside.partyapp.presentation.view.fragment.statisticMain.view.StatisticMainFragment
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

            return AddEventFragment.newInstance().apply {
                arguments = bundle
            }
        }
    }

    class ItemListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {

            return ItemListFragment.newInstance()
        }
    }

    class AddItemScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return AddItemFragment.newInstance()
        }
    }

    class StatisticScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return StatisticMainFragment.newInstance()
        }
    }

    class EditItemScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            val bundle = Bundle().apply {
                putInt(AddItemFragment.MODE_KEY, AddItemFragment.EDIT_VALUE)
            }

            return AddItemFragment.newInstance().apply {
                arguments = bundle
            }
        }
    }
}