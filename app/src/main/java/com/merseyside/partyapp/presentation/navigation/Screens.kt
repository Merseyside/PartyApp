package com.merseyside.partyapp.presentation.navigation

import androidx.fragment.app.Fragment
import com.merseyside.partyapp.presentation.view.fragment.eventList.view.EventListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class EventListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return EventListFragment.newInstance()
        }
    }
}