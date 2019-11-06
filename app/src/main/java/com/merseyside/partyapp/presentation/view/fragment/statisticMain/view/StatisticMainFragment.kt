package com.merseyside.partyapp.presentation.view.fragment.statisticMain.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProviders
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.databinding.FragmentStatisticMainBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerStatisticMainComponent
import com.merseyside.partyapp.presentation.di.module.StatisticMainModule
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import com.merseyside.partyapp.presentation.view.fragment.statisticMain.adapter.MemberAdapter
import com.merseyside.partyapp.presentation.view.fragment.statisticMain.model.StatisticMainViewModel
import com.upstream.basemvvmimpl.presentation.adapter.BaseAdapter

class StatisticMainFragment : BaseCalcFragment<FragmentStatisticMainBinding, StatisticMainViewModel>() {

    private val adapter = MemberAdapter()
    private lateinit var sharedViewModel: SharedViewModel

    override fun isActionBarVisible(): Boolean {
        return false
    }

    override fun hasTitleBackButton(): Boolean {
        return true
    }

    override fun setBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?) {
        DaggerStatisticMainComponent.builder()
            .appComponent(appComponent)
            .statisticMainModule(getStatisticMainModule(bundle))
            .build().inject(this)
    }

    private fun getStatisticMainModule(bundle: Bundle?): StatisticMainModule {
        return StatisticMainModule(this)
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_statistic_main
    }

    override fun getTitle(context: Context): String? {
        return context.getString(R.string.statistic)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProviders.of(baseActivityView).get(SharedViewModel::class.java)

        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doLayout()
    }

    private fun init() {

    }

    private fun doLayout() {
        binding.memberList.adapter = adapter

        adapter.setOnItemClickListener(object: BaseAdapter.AdapterClickListener<Member> {
            override fun onItemClicked(obj: Member) {
            }

        })

        viewModel.getStatistic(sharedViewModel.eventContainer)
    }

    companion object {
        fun newInstance(): StatisticMainFragment {
            return StatisticMainFragment()
        }
    }
}