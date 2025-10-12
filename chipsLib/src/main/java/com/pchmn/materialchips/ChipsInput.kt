package com.pchmn.materialchips

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.InputType
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.merseyside.utils.binding.viewBinding
import com.merseyside.utils.convertDpToPixel
import com.pchmn.materialchips.adapter.ChipsAdapter
import com.pchmn.materialchips.databinding.ChipsInputBinding
import com.pchmn.materialchips.model.Chip
import com.pchmn.materialchips.model.ChipInterface
import com.pchmn.materialchips.util.ActivityUtil
import com.pchmn.materialchips.util.MyWindowCallback
import com.pchmn.materialchips.util.ViewUtil
import com.pchmn.materialchips.views.ChipsInputEditText
import com.pchmn.materialchips.views.DetailedChipView
import com.pchmn.materialchips.views.FilterableListView
import com.pchmn.materialchips.views.ScrollViewMaxHeight


class ChipsInput(private val mContext: Context, attrsSet: AttributeSet?) : ScrollViewMaxHeight(mContext, attrsSet) {

    private val binding: ChipsInputBinding by viewBinding(R.layout.chips_input)

    // xml element
    val mRecyclerView: RecyclerView by lazy { binding.chipsRecycler }

    // adapter
    private var mChipsAdapter: ChipsAdapter? = null
    var hint: String? = null
    private var mHintColor: ColorStateList? = null
    private var mTextColor: ColorStateList? = null
    private var mMaxRows = 2
    private var mChipLabelColor: ColorStateList? = null
    private var mChipHasAvatarIcon = true
    private var mChipDeletable = false
    private var mIsEditable = true
    private var mIsSelectable = false
    private var mSelectedColor: ColorStateList? = null
    private var mSelectedTextColor: ColorStateList? = null
    private var mChipDeleteIcon: Drawable? = null
    private var mChipDeleteIconColor: ColorStateList? = null
    private var mChipBackgroundColor: ColorStateList? = null
    private var mShowChipDetailed = true
    private var mChipDetailedTextColor: ColorStateList? = null
    private var mChipDetailedDeleteIconColor: ColorStateList? = null
    private var mChipDetailedBackgroundColor: ColorStateList? = null
    private var mFilterableListBackgroundColor: ColorStateList? = null
    private var mFilterableListTextColor: ColorStateList? = null

    // chips listener
    private val mChipsListenerList: MutableList<ChipsListener> = ArrayList()
    private var mChipsListener: ChipsListener? = null

    // chip list
    private var mChipList: List<ChipInterface?>? = null
    private var mFilterableListView: FilterableListView? = null

    // chip validator
    var chipValidator: ChipValidator? = null

    lateinit var editText: ChipsInputEditText
        private set

    private var selectedlistener: OnChipSelectedListener? = null

    @IdRes
    private var filterableContainerId = 0

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setMaxHeight(convertDpToPixel(context, 150f).toInt())
        init(attrsSet)
    }

    /**
     * Inflate the view according to attributes
     *
     * @param attrs the attributes
     */
    private fun init(attrs: AttributeSet?) {
        // attributes
        if (attrs != null) {
            val a = mContext.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ChipsInput,
                0, 0
            )

            try {
                // hint
                hint = a.getString(R.styleable.ChipsInput_hint)
                mHintColor = a.getColorStateList(R.styleable.ChipsInput_hintColor)
                mTextColor = a.getColorStateList(R.styleable.ChipsInput_textColor)
                mMaxRows = a.getInteger(R.styleable.ChipsInput_maxRows, 2)
                setMaxHeight(ViewUtil.dpToPx((40 * mMaxRows) + 8))
                //setVerticalScrollBarEnabled(true);
                // chip label color
                mChipLabelColor = a.getColorStateList(R.styleable.ChipsInput_chip_labelColor)
                // chip avatar icon
                mChipHasAvatarIcon = a.getBoolean(R.styleable.ChipsInput_chip_hasAvatarIcon, true)
                // chip delete icon
                mChipDeletable = a.getBoolean(R.styleable.ChipsInput_chip_deletable, false)
                mChipDeleteIconColor =
                    a.getColorStateList(R.styleable.ChipsInput_chip_deleteIconColor)
                val deleteIconId = a.getResourceId(R.styleable.ChipsInput_chip_deleteIcon, NONE)
                if (deleteIconId != NONE) mChipDeleteIcon =
                    ContextCompat.getDrawable(mContext, deleteIconId)
                // chip background color
                mChipBackgroundColor =
                    a.getColorStateList(R.styleable.ChipsInput_chip_backgroundColor)
                // show chip detailed
                mShowChipDetailed = a.getBoolean(R.styleable.ChipsInput_showChipDetailed, true)
                // chip detailed text color
                mChipDetailedTextColor =
                    a.getColorStateList(R.styleable.ChipsInput_chip_detailed_textColor)
                mChipDetailedBackgroundColor =
                    a.getColorStateList(R.styleable.ChipsInput_chip_detailed_backgroundColor)
                mChipDetailedDeleteIconColor =
                    a.getColorStateList(R.styleable.ChipsInput_chip_detailed_deleteIconColor)
                // filterable list
                mFilterableListBackgroundColor =
                    a.getColorStateList(R.styleable.ChipsInput_filterable_list_backgroundColor)
                mFilterableListTextColor =
                    a.getColorStateList(R.styleable.ChipsInput_filterable_list_textColor)

                mIsEditable = a.getBoolean(R.styleable.ChipsInput_chip_editable, true)
                mIsSelectable = a.getBoolean(R.styleable.ChipsInput_chip_selectable, false)

                mSelectedColor = a.getColorStateList(R.styleable.ChipsInput_chip_selectedColor)
                mSelectedTextColor =
                    a.getColorStateList(R.styleable.ChipsInput_chip_selectedLabelColor)

                filterableContainerId =
                    a.getResourceId(R.styleable.ChipsInput_chip_filterable_container, 0)
            } finally {
                initEditText()
                a.recycle()
            }
        }

        // adapter
        mChipsAdapter = ChipsAdapter(mContext, this, mRecyclerView, mIsSelectable)
        val chipsLayoutManager = ChipsLayoutManager.newBuilder(mContext)
            .setOrientation(ChipsLayoutManager.HORIZONTAL)
            .build()
        mRecyclerView!!.layoutManager = chipsLayoutManager
        mRecyclerView!!.isNestedScrollingEnabled = false
        mRecyclerView!!.adapter = mChipsAdapter

        // set window callback
        // will hide DetailedOpenView and hide keyboard on touch outside
        val activity = ActivityUtil.scanForActivity(mContext)
            ?: throw ClassCastException("android.view.Context cannot be cast to android.app.Activity")

        val mCallBack = (activity).window.callback
        activity.window.callback = MyWindowCallback(mCallBack, activity)
    }


    private fun initEditText() {
        editText = ChipsInputEditText(mContext)
        if (mHintColor != null) editText!!.setHintTextColor(mHintColor)
        if (mTextColor != null) editText!!.setTextColor(mTextColor)

        if (!mIsEditable) {
            editText!!.setBackgroundResource(android.R.color.transparent)
        } else if (mChipBackgroundColor != null) {
            editText!!.supportBackgroundTintList = mChipBackgroundColor
        }

        if (!mIsEditable) {
            editText!!.isFocusable = false
            editText!!.isFocusableInTouchMode = false
            editText!!.inputType = InputType.TYPE_NULL
            editText!!.isEnabled = false
        }
    }

    fun addChip(chip: ChipInterface?) {
        mChipsAdapter!!.addChip(chip)
    }

    fun addChip(id: Any, icon: Drawable?, label: String, info: String?) {
        val chip = Chip(id, icon, label, info)
        mChipsAdapter!!.addChip(chip)
    }

    fun addChip(icon: Drawable?, label: String, info: String?) {
        val chip = Chip(icon, label, info)
        mChipsAdapter!!.addChip(chip)
    }

    fun addChip(id: Any, label: String, info: String?) {
        val chip = Chip(id, label, info)
        mChipsAdapter!!.addChip(chip)
    }

    fun addChip(id: Any, iconUri: Uri?, label: String, info: String?) {
        val chip = Chip(id, iconUri, label, info)
        mChipsAdapter!!.addChip(chip)
    }

    fun addChip(iconUri: Uri?, label: String, info: String?) {
        val chip = Chip(iconUri, label, info)
        mChipsAdapter!!.addChip(chip)
    }

    fun addChip(id: Any, label: String) {
        val chip: ChipInterface = Chip(id, label)
        mChipsAdapter!!.addChip(chip)
    }

    fun removeChip(chip: ChipInterface?) {
        mChipsAdapter!!.removeChip(chip)
    }

    fun removeChipById(id: Any?) {
        mChipsAdapter!!.removeChipById(id)
    }

    fun removeChipByLabel(label: String?) {
        mChipsAdapter!!.removeChipByLabel(label)
    }

    fun removeChipByInfo(info: String?) {
        mChipsAdapter!!.removeChipByInfo(info)
    }

    val chipView: ChipView
        get() {
            val padding = ViewUtil.dpToPx(4)
            val chipView = ChipView.Builder(mContext)
                .labelColor(mChipLabelColor)
                .hasAvatarIcon(mChipHasAvatarIcon)
                .deletable(mChipDeletable)
                .deleteIcon(mChipDeleteIcon)
                .deleteIconColor(mChipDeleteIconColor)
                .backgroundColor(mChipBackgroundColor)
                .selectedColor(mSelectedColor)
                .selectedTextColor(mSelectedTextColor)
                .build()

            if (mIsSelectable) {
                chipView.setOnChipClicked(onClickListener)
            }

            chipView.setPadding(padding, padding, padding, padding)

            return chipView
        }

    private val onClickListener = OnChipClickListener { chip: ChipView ->
        if (mIsSelectable) {
            chip.setChipSelected()

            if (selectedlistener != null) {
                selectedlistener!!.onSelected(chip.isChipSelected, chip.chip)
            }
        }
    }

    fun getDetailedChipView(chip: ChipInterface): DetailedChipView {
        return DetailedChipView.Builder(mContext)
            .chip(chip)
            .textColor(mChipDetailedTextColor)
            .backgroundColor(mChipDetailedBackgroundColor)
            .deleteIconColor(mChipDetailedDeleteIconColor)
            .build()
    }

    fun addChipsListener(chipsListener: ChipsListener) {
        mChipsListenerList.add(chipsListener)
        mChipsListener = chipsListener
    }

    fun onChipAdded(chip: ChipInterface?, size: Int) {
        for (chipsListener in mChipsListenerList) {
            chipsListener.onChipAdded(chip, size)
        }
    }

    fun onChipRemoved(chip: ChipInterface?, size: Int) {
        for (chipsListener in mChipsListenerList) {
            chipsListener.onChipRemoved(chip, size)
        }
    }

    fun onTextChanged(text: CharSequence) {
        if (mChipsListener != null) {
            for (chipsListener in mChipsListenerList) {
                chipsListener.onTextChanged(text)
            }
            // show filterable list
            if (mFilterableListView != null) {
                if (text.length > 0) {
                    mFilterableListView!!.filterList(text)
                    mFilterableListView!!.fadeIn()
                }
                else mFilterableListView!!.fadeOut()
            }
        }
    }

    val allChips: List<ChipInterface>
        get() = mChipsAdapter!!.chipList

    fun setHintColor(mHintColor: ColorStateList?) {
        this.mHintColor = mHintColor
    }

    fun setTextColor(mTextColor: ColorStateList?) {
        this.mTextColor = mTextColor
    }

    fun setMaxRows(mMaxRows: Int): ChipsInput {
        this.mMaxRows = mMaxRows
        return this
    }

    fun setChipLabelColor(mLabelColor: ColorStateList?) {
        this.mChipLabelColor = mLabelColor
    }

    fun setChipHasAvatarIcon(mHasAvatarIcon: Boolean) {
        this.mChipHasAvatarIcon = mHasAvatarIcon
    }

    fun chipHasAvatarIcon(): Boolean {
        return mChipHasAvatarIcon
    }

    fun setChipDeletable(mDeletable: Boolean) {
        this.mChipDeletable = mDeletable
    }

    fun setChipDeleteIcon(mDeleteIcon: Drawable?) {
        this.mChipDeleteIcon = mDeleteIcon
    }

    fun setChipDeleteIconColor(mDeleteIconColor: ColorStateList?) {
        this.mChipDeleteIconColor = mDeleteIconColor
    }

    fun setChipBackgroundColor(mBackgroundColor: ColorStateList?) {
        this.mChipBackgroundColor = mBackgroundColor
    }

    fun setShowChipDetailed(mShowChipDetailed: Boolean): ChipsInput {
        this.mShowChipDetailed = mShowChipDetailed
        return this
    }

    val isShowChipDetailed: Boolean
        get() = mShowChipDetailed && !mIsSelectable

    fun setChipDetailedTextColor(mChipDetailedTextColor: ColorStateList?) {
        this.mChipDetailedTextColor = mChipDetailedTextColor
    }

    fun setChipDetailedDeleteIconColor(mChipDetailedDeleteIconColor: ColorStateList?) {
        this.mChipDetailedDeleteIconColor = mChipDetailedDeleteIconColor
    }

    fun setChipDetailedBackgroundColor(mChipDetailedBackgroundColor: ColorStateList?) {
        this.mChipDetailedBackgroundColor = mChipDetailedBackgroundColor
    }

    val selectedChips: List<ChipInterface>
        get() {
            val selectedChips: MutableList<ChipInterface> =
                ArrayList()

            for (i in 0 until mRecyclerView!!.childCount) {
                if (mRecyclerView!!.getChildAt(i) is ChipView) {
                    val chip = mRecyclerView!!.getChildAt(i) as ChipView

                    if (chip.isChipSelected) {
                        selectedChips.add(requireNotNull(chip.chip))
                    }
                }
            }

            return selectedChips
        }

    fun getChipViewById(id: Any): ChipView? {
        for (i in 0 until mRecyclerView!!.childCount) {
            if (mRecyclerView!!.getChildAt(i) is ChipView) {
                val chip = mRecyclerView!!.getChildAt(i) as ChipView
                if (chip.chip!!.id === id) return chip
            }
        }

        return null
    }

    var filterableList: List<ChipInterface?>?
        get() = mChipList
        set(list) {
            mChipList = list
            mFilterableListView = FilterableListView(mContext)
            mFilterableListView!!.setContainer(filterableContainerId)

            mFilterableListView!!.build(
                mChipList!!,
                this,
                mFilterableListBackgroundColor,
                mFilterableListTextColor
            )
            mChipsAdapter!!.setFilterableListView(mFilterableListView)
        }

    fun setOnChipSelectedListener(listener: OnChipSelectedListener?) {
        this.selectedlistener = listener
    }

    interface ChipsListener {
        fun onChipAdded(chip: ChipInterface?, newSize: Int)
        fun onChipRemoved(chip: ChipInterface?, newSize: Int)
        fun onTextChanged(text: CharSequence?)
    }

    interface ChipValidator {
        fun areEquals(chip1: ChipInterface?, chip2: ChipInterface?): Boolean
    }

    fun onBackPressed(): Boolean {
        val container = mFilterableListView!!.parent as? ViewGroup
        if (container != null) {
            return if (container.visibility == GONE) {
                false
            } else {
                mFilterableListView!!.fadeOut()
                true
            }
        }

        return false
    }

    fun setSelectedChipById(id: Any?, isSelected: Boolean) {
        if (mChipsAdapter != null) {
            mChipsAdapter!!.setChipSelected(id, isSelected)
        }
    }

    companion object {
        private val TAG = ChipsInput::class.java.toString()

        // attributes
        private const val NONE = -1
    }
}
