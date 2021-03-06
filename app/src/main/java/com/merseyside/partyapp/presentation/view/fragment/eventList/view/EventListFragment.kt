package com.merseyside.partyapp.presentation.view.fragment.eventList.view

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.merseyside.adapters.base.OnItemClickListener
import com.merseyside.adapters.base.BaseAdapter
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

class EventListFragment : BaseCalcFragment<FragmentEventListBinding, EventListViewModel>() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun hasTitleBackButton(): Boolean {
        return false
    }

    private val adapter = EventAdapter()

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?) {
        DaggerEventListComponent.builder()
            .appComponent(appComponent)
            .eventListModule(getEventListModule(bundle))
            .build().inject(this)
    }

    private fun getEventListModule(bundle: Bundle?): EventListModule {
        return EventListModule(this, bundle)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_event_list
    }

    override fun getTitle(context: Context): String? {

        return context.getString(R.string.event_list_title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doLayout()
    }

    private fun init() {
        sharedViewModel = ViewModelProviders.of(baseActivity).get(SharedViewModel::class.java)
    }

    private fun doLayout() {
        binding.eventList.adapter = adapter
        adapter.setOnItemClickListener(onItemClickListener)

        adapter.setOnEventOptionsClickListener(object: EventAdapter.OnEventOptionsClickListener {
            override fun onEditClick(event: Event) {
                viewModel.onEditClick(event.id)
            }

            override fun onDeleteClick(event: Event) {
                viewModel.onDeleteClick(event)
            }

            override fun onStatisticClick(event: Event) {
                sharedViewModel.eventContainer = event
                viewModel.navigateToStatistic()
            }

        })

        viewModel.showEvents()
    }

    private val onItemClickListener = object: OnItemClickListener<Event> {
        override fun onItemClicked(obj: Event) {
            sharedViewModel.eventContainer = obj
            viewModel.onEventClick()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        adapter.removeOnItemClickListener(onItemClickListener)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        baseActivity.menuInflater.inflate(R.menu.menu_main,  menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_settings -> {
                viewModel.navigateToSettings()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {

        private const val TAG = "EventListFragment"

        fun newInstance(): EventListFragment {
            return EventListFragment()
        }
    }
}