package com.merseyside.partyapp.presentation.view.fragment.statisticMember.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.merseyside.animators.AnimatorList
import com.merseyside.animators.Approach
import com.merseyside.animators.Axis
import com.merseyside.animators.MainPoint
import com.merseyside.animators.animator.AlphaAnimator
import com.merseyside.animators.animator.TransitionAnimator
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.databinding.FragmentMemberStatisticBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerStatisticMemberComponent
import com.merseyside.partyapp.presentation.di.module.StatisticMemberModule
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.model.StatisticMemberViewModel
import com.merseyside.partyapp.utils.getMemberStatistic
import com.merseyside.utils.Logger
import com.merseyside.utils.delayedMainThread
import com.merseyside.utils.randomBool
import com.merseyside.utils.time.Millis

class StatisticMemberFragment : BaseCalcFragment<FragmentMemberStatisticBinding, StatisticMemberViewModel>() {

    private var statistic: MemberStatistic? = null

    private var animatorList: AnimatorList? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doLayout()
    }

    private fun doLayout() {
        if (statistic != null) {
            viewModel.initWithMemberStatistic(statistic!!)
        }

        binding.shareMember.setOnClickListener {
            shareStatistic(getMemberStatistic(
                member = viewModel.statistic
            ))

            if (!prefsHelper.isRated() && randomBool(0.2f)) showRateUsDialog()

            logEvent("share_member", Bundle())
        }

        startAnimation()
    }

    private fun startAnimation() {
        if (animatorList == null) {
            animatorList = AnimatorList(Approach.TOGETHER).apply {
                addAnimator(
                    TransitionAnimator(
                        TransitionAnimator.Builder(
                        view = binding.orders,
                        duration = duration
                    ).apply {
                        setInPercents(
                            0f to MainPoint.TOP_RIGHT,
                            0f to MainPoint.TOP_LEFT,
                            axis = Axis.X
                        )
                    })
                )

                addAnimator(
                    AlphaAnimator(AlphaAnimator.Builder(
                        view = binding.orders,
                        duration = duration
                    ).apply {
                        values(0f, 1f)
                    })
                )

                addAnimator(
                    AlphaAnimator(
                        AlphaAnimator.Builder(
                        view = binding.stats,
                        duration = duration
                    ).apply {
                        values(0f, 1f)
                    })
                )

                addAnimator(
                    TransitionAnimator(TransitionAnimator.Builder(
                        view = binding.results,
                        duration = duration
                    ).apply {
                        setInPercents(
                            1f to MainPoint.TOP_LEFT,
                            0f to MainPoint.TOP_LEFT,
                            axis = Axis.Y
                        )
                    })
                )

                addAnimator(
                    AlphaAnimator(AlphaAnimator.Builder(
                        view = binding.results,
                        duration = duration
                    ).apply {
                        values(0f, 1f)
                    })
                )
            }
        }

        delayedMainThread(Millis(300)) {
            animatorList!!.start()
            animatorList = null
        }
    }

    companion object {

        fun newInstance(statistic: MemberStatistic): StatisticMemberFragment {
            return StatisticMemberFragment().apply {
                this.statistic = statistic
            }
        }

        val duration = Millis(700)
    }
}