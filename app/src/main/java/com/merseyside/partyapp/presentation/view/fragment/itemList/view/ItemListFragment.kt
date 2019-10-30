package com.merseyside.partyapp.presentation.view.fragment.itemList.view

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
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
import com.upstream.basemvvmimpl.presentation.adapter.BaseAdapter
import java.lang.IllegalStateException

class ItemListFragment : BaseCalcFragment<FragmentItemListBinding, ItemListViewModel>() {

    private lateinit var sharedViewModel: SharedViewModel

    private val adapter = ItemAdapter()

    override fun hasTitleBackButton(): Boolean {
        return true
    }

    override fun setBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?) {
        DaggerItemListComponent.builder()
            .appComponent(appComponent)
            .itemListModule(getItemListModule(bundle))
            .build().inject(this)
    }

    private fun getItemListModule(bundle: Bundle?): ItemListModule {
        return ItemListModule(this)
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_item_list
    }

    override fun getTitle(context: Context): String? {
        return sharedViewModel.eventContainer?.name ?: throw IllegalStateException()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProviders.of(baseActivityView).get(SharedViewModel::class.java)

        setHasOptionsMenu(true)

        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        baseActivityView.menuInflater.inflate(R.menu.menu_items,  menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_edit) {
            viewModel.navigateToEditScreen(sharedViewModel.eventContainer?.id ?: throw IllegalStateException())
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private fun init() {
        adapter.setOnItemClickListener(object: BaseAdapter.AdapterClickListener {
            override fun onItemClicked(obj: Any) {
                if (obj is Item) {
                    sharedViewModel.itemContainer = obj
                    viewModel.navigateToEditItemScreen()
                }
            }
        })
    }

    private fun doLayout() {
        binding.itemList.adapter = adapter
        
        adapter.setOnItemOptionsClickListener(object: ItemAdapter.OnItemOptionsClickListener {
            override fun onDeleteClick(item: Item) {
                viewModel.deleteItem(item.id)
            }

        })

        viewModel.init(sharedViewModel.eventContainer!!.id)
    }

    companion object {
        fun newInstance(): ItemListFragment {
            return ItemListFragment()
        }
    }

}