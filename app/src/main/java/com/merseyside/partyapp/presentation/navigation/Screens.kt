package com.merseyside.partyapp.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.merseyside.partyapp.presentation.view.fragment.addEvent.view.AddEventFragment
import com.merseyside.partyapp.presentation.view.fragment.addItem.view.AddItemFragment
import com.merseyside.partyapp.presentation.view.fragment.eventList.view.EventListFragment
import com.merseyside.partyapp.presentation.view.fragment.itemList.view.ItemListFragment
import com.merseyside.partyapp.presentation.view.fragment.settings.view.SettingsFragment
import com.merseyside.partyapp.presentation.view.fragment.statisticMain.view.StatisticMainFragment

class Screens {
    companion object {
        fun EventListScreen() = FragmentScreen {
            EventListFragment.newInstance()
        }

        fun AddEventScreen() = FragmentScreen {
            AddEventFragment.newInstance()
        }

        fun EditEventScreen(id: Long) = FragmentScreen {
            AddEventFragment.newInstance(id)
        }

        fun ItemListScreen() = FragmentScreen {
            ItemListFragment.newInstance()
        }

        fun AddItemScreen() = FragmentScreen {
            AddItemFragment.newInstance(AddItemFragment.ADD_VALUE)
        }

        fun StatisticScreen() = FragmentScreen {
            StatisticMainFragment.newInstance()
        }

        fun EditItemScreen() = FragmentScreen {
            AddItemFragment.newInstance(AddItemFragment.EDIT_VALUE)
        }

        fun SettingsScreen() = FragmentScreen {
            SettingsFragment.newInstance()
        }
    }
}