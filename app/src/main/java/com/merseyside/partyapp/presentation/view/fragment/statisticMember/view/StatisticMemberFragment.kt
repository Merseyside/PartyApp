package com.merseyside.partyapp.presentation.view.fragment.statisticMember.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.merseyside.mvvmcleanarch.utils.randomTrueOrFalse
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.databinding.FragmentMemberStatisticBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerStatisticMemberComponent
import com.merseyside.partyapp.presentation.di.module.StatisticMemberModule
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.model.StatisticMemberViewModel
import com.merseyside.partyapp.utils.getMemberStatistic

class StatisticMemberFragment : BaseCalcFragment<FragmentMemberStatisticBinding, StatisticMemberViewModel>() {

    private var statistic: MemberStatistic? = null

    override fun hasTitleBackButton(): Boolean {
        return true
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?) {
        DaggerStatisticMemberComponent.builder()
            .appComponent(appComponent)
            .statisticMemberModule(getStatisticMemberModule(bundle))
            .build().inject(this)
    }

    private fun getStatisticMemberModule(bundle: Bundle?): StatisticMemberModule {
        return StatisticMemberModule(this, bundle)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_member_statistic
    }

    override fun getTitle(context: Context): String? {
        return null
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

    }

    private fun doLayout() {
        if (statistic != null) {
            viewModel.initWithMemberStatistic(statistic!!)
        }

        binding.shareMember.setOnClickListener {
            shareStatistic(getMemberStatistic(
                CalcApplication.getInstance().getContext(),
                viewModel.statistic
            ))

            if (!prefsHelper.isRated() && randomTrueOrFalse(0.20f)) showRateUsDialog()

            logEvent("share_member", Bundle())
        }
    }

    companion object {
        private const val TAG = "StatisticMemberFragment"

        fun newInstance(statistic: MemberStatistic): StatisticMemberFragment {
            return StatisticMemberFragment().apply {
                this.statistic = statistic
            }
        }
    }
}