package com.merseyside.partyapp.presentation.view.fragment.eventList.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Event
import com.merseyside.partyapp.databinding.FragmentEventListBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerEventListComponent
import com.merseyside.partyapp.presentation.di.module.EventListModule
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import com.merseyside.partyapp.presentation.view.fragment.eventList.adapter.EventAdapter
import com.merseyside.partyapp.presentation.view.fragment.eventList.model.EventListViewModel
import com.upstream.basemvvmimpl.presentation.adapter.BaseAdapter

class EventListFragment : BaseCalcFragment<FragmentEventListBinding, EventListViewModel>() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun hasTitleBackButton(): Boolean {
        return false
    }

    private val adapter = EventAdapter()

    override fun setBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?) {
        DaggerEventListComponent.builder()
            .appComponent(appComponent)
            .eventListModule(getEventListModule(bundle))
            .build().inject(this)
    }

    private fun getEventListModule(bundle: Bundle?): EventListModule {
        return EventListModule(this)
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_event_list
    }

    override fun getTitle(context: Context): String? {
        return context.getString(R.string.event_list_title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doLayout()
    }

    private fun init() {
        sharedViewModel = ViewModelProviders.of(baseActivityView).get(SharedViewModel::class.java)
    }

    private fun doLayout() {
        binding.eventList.adapter = adapter
        adapter.setOnItemClickListener(object: BaseAdapter.AdapterClickListener {
            override fun onItemClicked(obj: Any) {
                if (obj is Event) {
                    sharedViewModel.eventContainer = obj
                    viewModel.onEventClick()
                }
            }

        })

        viewModel.showEvents()
    }

    companion object {

        private const val TAG = "EventListFragment"

        fun newInstance(): EventListFragment {
            return EventListFragment()
        }
    }
}