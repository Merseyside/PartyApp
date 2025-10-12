package com.merseyside.partyapp.presentation.view.fragment.itemList.view

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.item.Item
import com.merseyside.partyapp.databinding.FragmentItemListBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerItemListComponent
import com.merseyside.partyapp.presentation.di.module.ItemListModule
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import com.merseyside.partyapp.presentation.view.fragment.itemList.adapter.ItemAdapter
import com.merseyside.partyapp.presentation.view.fragment.itemList.model.ItemListViewModel
import java.lang.IllegalStateException

class ItemListFragment : BaseCalcFragment<FragmentItemListBinding, ItemListViewModel>() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val adapter by lazy {
        ItemAdapter { item ->
            sharedViewModel.itemContainer = item
            viewModel.navigateToEditItemScreen()
        }
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?, vararg args: Any) {
        DaggerItemListComponent.builder()
            .appComponent(appComponent)
            .itemListModule(getItemListModule(bundle))
            .build().inject(this)
    }

    private fun getItemListModule(bundle: Bundle?): ItemListModule {
        return ItemListModule(this, bundle)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_item_list
    }

    override fun getTitle(context: Context): String? {
        return sharedViewModel.eventContainer?.name ?: throw IllegalStateException()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        doLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        baseActivity.menuInflater.inflate(R.menu.menu_items,  menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_edit -> {
                viewModel.navigateToEditScreen(sharedViewModel.eventContainer?.id ?: throw IllegalStateException())
                return true
            }
            R.id.action_statistic -> {
                viewModel.navigateToStatisticScreen()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun init() {
        adapter.setOnItemOptionsClickListener(object: ItemAdapter.OnItemOptionsClickListener {
            override fun onDeleteClick(item: Item) {
                viewModel.deleteItem(item)
            }
        })
    }

    private fun doLayout() {
        requireBinding().itemList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        viewModel.init(sharedViewModel.eventContainer!!)
    }

    companion object {
        fun newInstance(): ItemListFragment {
            return ItemListFragment()
        }
    }
}