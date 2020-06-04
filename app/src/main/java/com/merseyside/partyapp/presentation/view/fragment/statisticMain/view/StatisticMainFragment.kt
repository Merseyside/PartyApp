package com.merseyside.partyapp.presentation.view.fragment.statisticMain.view

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.merseyside.merseyLib.adapters.BaseAdapter
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.db.event.Member
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.databinding.FragmentStatisticMainBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerStatisticMainComponent
import com.merseyside.partyapp.presentation.di.module.StatisticMainModule
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import com.merseyside.partyapp.presentation.view.fragment.addItem.adapter.MemberAdapter
import com.merseyside.partyapp.presentation.view.fragment.statisticMain.adapter.MemberStatisticPagerAdapter
import com.merseyside.partyapp.presentation.view.fragment.statisticMain.model.StatisticMainViewModel
import com.merseyside.partyapp.utils.getShareableStatistic

class StatisticMainFragment : BaseCalcFragment<FragmentStatisticMainBinding, StatisticMainViewModel>() {

    private val adapter = MemberAdapter()
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var pagedAdapter: MemberStatisticPagerAdapter

    private val memberStatisticObserver = Observer<List<MemberStatistic>> {
        if (!it.isNullOrEmpty()) {
            pagedAdapter = MemberStatisticPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
            pagedAdapter.setData(it)

            binding.pageContainer.adapter = pagedAdapter
        }
    }

    override fun hasTitleBackButton(): Boolean {
        return true
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?) {
        DaggerStatisticMainComponent.builder()
            .appComponent(appComponent)
            .statisticMainModule(getStatisticMainModule(bundle))
            .build().inject(this)
    }

    private fun getStatisticMainModule(bundle: Bundle?): StatisticMainModule {
        return StatisticMainModule(this, bundle)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_statistic_main
    }

    override fun getTitle(context: Context): String? {
        return context.getString(R.string.statistic)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProviders.of(baseActivity).get(SharedViewModel::class.java)

        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doLayout()
    }

    private fun doLayout() {
        viewModel.memberStatisticLiveData.observe(viewLifecycleOwner, memberStatisticObserver)
        binding.memberList.adapter = adapter

        adapter.setOnItemClickListener(onMemberClickListener)

        viewModel.initWithEvent(sharedViewModel.eventContainer)
    }

    private val onMemberClickListener = object: BaseAdapter.OnItemClickListener<Member> {
        override fun onItemClicked(obj: Member) {
            val position = adapter.getPositionOfObj(obj)

            binding.pageContainer.currentItem = position
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.memberStatisticLiveData.removeObserver(memberStatisticObserver)

        adapter.removeOnItemClickListener(onMemberClickListener)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        baseActivity.menuInflater.inflate(R.menu.menu_statistic, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        viewModel.statistic?.let { statistic ->
            when (item.itemId) {
                R.id.action_share -> {
                    shareStatistic(getShareableStatistic(
                        statistic
                    ))

                    if (!prefsHelper.isRated()) showRateUsDialog()

                    logEvent("share_all", Bundle())
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "StatisticMainFragment"

        fun newInstance(): StatisticMainFragment {
            return StatisticMainFragment()
        }
    }
}