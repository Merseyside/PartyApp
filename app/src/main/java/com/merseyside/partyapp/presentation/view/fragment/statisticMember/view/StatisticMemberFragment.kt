package com.merseyside.partyapp.presentation.view.fragment.statisticMember.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.merseyside.animators.Anchor
import com.merseyside.animators.AnimStrategy
import com.merseyside.animators.AnimatorList
import com.merseyside.animators.Axis
import com.merseyside.animators.animator.AlphaAnimator
import com.merseyside.animators.animator.TransitionAnimator
import com.merseyside.merseyLib.kotlin.utils.randomBool
import com.merseyside.merseyLib.time.units.Millis
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.databinding.FragmentMemberStatisticBinding
import com.merseyside.partyapp.presentation.base.BaseCalcFragment
import com.merseyside.partyapp.presentation.di.component.DaggerStatisticMemberComponent
import com.merseyside.partyapp.presentation.di.module.StatisticMemberModule
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.model.StatisticMemberViewModel
import com.merseyside.partyapp.utils.getMemberStatistic
import com.merseyside.utils.delayedMainThread


class StatisticMemberFragment : BaseCalcFragment<FragmentMemberStatisticBinding, StatisticMemberViewModel>() {

    private var statistic: MemberStatistic? = null

    private var animatorList: AnimatorList? = null

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun performInjection(bundle: Bundle?, vararg args: Any) {
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
        onBackPressedCallback.isEnabled = false
        doLayout()
    }

    private fun doLayout() {
        if (statistic != null) {
            viewModel.initWithMemberStatistic(statistic!!)
        }

        requireBinding().shareMember.setOnClickListener {
            shareStatistic(getMemberStatistic(
                member = viewModel.statistic
            ))

            if (!prefsHelper.isRated() && randomBool(0.2f)) showRateUsDialog()

            logEvent("share_member", Bundle())
        }

        startAnimation()
    }

    override fun setupAppBar() {}

    private fun startAnimation() {
        if (animatorList == null) {
            animatorList = AnimatorList(AnimStrategy.TOGETHER).apply {
                addAnimator(
                    TransitionAnimator(
                        TransitionAnimator.Builder(
                        view = requireBinding().orders,
                        duration = duration
                    ).apply {
                        setInPercents(
                            0f to Anchor.TOP_RIGHT,
                            0f to Anchor.TOP_LEFT,
                            axis = Axis.X
                        )
                    })
                )

                addAnimator(
                    AlphaAnimator(AlphaAnimator.Builder(
                        view = requireBinding().orders,
                        duration = duration
                    ).apply {
                        values(0f, 1f)
                    })
                )

                addAnimator(
                    AlphaAnimator(
                        AlphaAnimator.Builder(
                        view = requireBinding().stats,
                        duration = duration
                    ).apply {
                        values(0f, 1f)
                    })
                )

                addAnimator(
                    TransitionAnimator(TransitionAnimator.Builder(
                        view = requireBinding().results,
                        duration = duration
                    ).apply {
                        setInPercents(
                            1f to Anchor.TOP_LEFT,
                            0f to Anchor.TOP_LEFT,
                            axis = Axis.Y
                        )
                    })
                )

                addAnimator(
                    AlphaAnimator(AlphaAnimator.Builder(
                        view = requireBinding().results,
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