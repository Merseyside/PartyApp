package com.merseyside.partyapp.presentation.view.fragment.addItem.adapter

import com.merseyside.adapters.SimpleAdapter
import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.config.init.initAdapter
import com.merseyside.adapters.core.feature.selecting.SelectableMode
import com.merseyside.adapters.core.feature.selecting.Selecting
import com.merseyside.adapters.core.feature.selecting.config.getAdapterSelect
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.presentation.view.fragment.addItem.model.MemberItemViewModel

class MemberAdapter private constructor(
    adapterConfig: AdapterConfig<Member, MemberItemViewModel>
) : SimpleAdapter<Member, MemberItemViewModel>(adapterConfig) {

    val selectFeature by lazy {
        requireNotNull(adapterConfig.getAdapterSelect())
    }

    override fun getLayoutIdForViewType(viewType: Int): Int {
        return viewType
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItemByPosition(position).avatarUrl.isNullOrEmpty()) R.layout.view_text_member
        else R.layout.view_image_member
    }

    override fun getBindingVariable(): Int = BR.obj

    override fun createItemViewModel(item: Member): MemberItemViewModel {
        return MemberItemViewModel(item)
    }

    companion object {

        operator fun invoke(onSelect: (Member, Int) -> Unit = { _, _ -> }): MemberAdapter {
            return initAdapter(::MemberAdapter) {
                Selecting {
                    selectableMode = SelectableMode.Single(forceSelect = true)
                    this.onSelect = { item, isSelected, _ ->
                        if (isSelected) {
                            onSelect(item, adapter.getPositionOfItem(item))
                        }
                    }
                }
            }
        }
    }
}