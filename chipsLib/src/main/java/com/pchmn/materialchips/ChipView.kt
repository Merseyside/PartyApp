package com.pchmn.materialchips

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.merseyside.utils.binding.viewBinding
import com.pchmn.materialchips.databinding.ChipViewBinding
import com.pchmn.materialchips.model.ChipInterface
import com.pchmn.materialchips.util.LetterTileProvider
import com.pchmn.materialchips.util.ViewUtil
import de.hdodenhof.circleimageview.CircleImageView


class ChipView : RelativeLayout {

    private val binding by viewBinding<ChipViewBinding>(R.layout.chip_view)

    // context
    private var mContext: Context

    // xml elements
    val mContentLayout: LinearLayout by lazy { binding.content }
    val mAvatarIconImageView: CircleImageView by lazy { binding.icon}
    val mLabelTextView: TextView by lazy { binding.label }
    val mDeleteButton: ImageButton by lazy { binding.deleteButton }

    private var mLabel: String? = null
    private var mLabelColor: ColorStateList? = null
    private var mHasAvatarIcon = false
    private var mAvatarIconDrawable: Drawable? = null
    private var mAvatarIconUri: Uri? = null
    private var mDeletable = false
    private var mDeleteIcon: Drawable? = null
    private var mDeleteIconColor: ColorStateList? = null
    private var mBackgroundColor: ColorStateList? = null
    private var mSelectedColor: ColorStateList? = null
    private var mSelectedTextColor: ColorStateList? = null

    // letter tile provider
    private var mLetterTileProvider: LetterTileProvider? = null

    /**
     * Set the chip object
     *
     * @param chip the chip
     */
    // chip
    var chip: ChipInterface? = null

    private var onClickListenerList: MutableList<OnChipClickListener>? = null

    private var mIsSelected = false

    constructor(context: Context) : super(context) {
        mContext = context
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
        init(attrs)
    }

    /**
     * Inflate the view according to attributes
     *
     * @param attrs the attributes
     */
    private fun init(attrs: AttributeSet?) {

        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        // letter tile provider
        mLetterTileProvider = LetterTileProvider(mContext)

        // attributes
        if (attrs != null) {
            val a = mContext.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ChipView,
                0, 0
            )

            try {
                // label
                mLabel = a.getString(R.styleable.ChipView_label)
                mLabelColor = a.getColorStateList(R.styleable.ChipView_labelColor)
                // avatar icon
                mHasAvatarIcon = a.getBoolean(R.styleable.ChipView_hasAvatarIcon, false)
                val avatarIconId = a.getResourceId(R.styleable.ChipView_avatarIcon, NONE)
                if (avatarIconId != NONE) mAvatarIconDrawable =
                    ContextCompat.getDrawable(mContext, avatarIconId)
                if (mAvatarIconDrawable != null) mHasAvatarIcon = true
                // delete icon
                mDeletable = a.getBoolean(R.styleable.ChipView_deletable, false)
                mDeleteIconColor = a.getColorStateList(R.styleable.ChipView_deleteIconColor)
                val deleteIconId = a.getResourceId(R.styleable.ChipView_deleteIcon, NONE)
                if (deleteIconId != NONE) mDeleteIcon =
                    ContextCompat.getDrawable(mContext, deleteIconId)
                // background color
                mBackgroundColor = a.getColorStateList(R.styleable.ChipView_backgroundColor)
                mIsSelected = a.getBoolean(R.styleable.ChipView_selected, false)

                inflateWithAttributes()
            } finally {
                a.recycle()
            }
        }
    }

    /**
     * Inflate the view
     */
    private fun inflateWithAttributes() {
        // label
        label = mLabel
        if (mLabelColor != null) setLabelColor(mLabelColor)

        // avatar
        setHasAvatarIcon(mHasAvatarIcon)

        // delete button
        setDeletable(mDeletable)

        // background color
        setChipBackgroundColor(mBackgroundColor)

        isChipSelected = mIsSelected
    }

    fun inflate(chip: ChipInterface) {
        this.chip = chip
        // label
        mLabel = chip.label
        // icon
        mAvatarIconUri = chip.avatarUri
        mAvatarIconDrawable = chip.avatarDrawable

        mIsSelected = chip.isSelected
        // inflate
        inflateWithAttributes()
    }

    var label: String?
        /**
         * Get label
         *
         * @return the label
         */
        get() = mLabel
        /**
         * Set label
         *
         * @param label the label to set
         */
        set(label) {
            mLabel = label
            mLabelTextView!!.text = label
        }

    /**
     * Set label color
     *
     * @param color the color to set
     */
    fun setLabelColor(color: ColorStateList?) {
        mLabelTextView!!.setTextColor(color)
    }

    /**
     * Set label color
     *
     * @param color the color to set
     */
    fun setLabelColor(@ColorInt color: Int) {
        mLabelColor = ColorStateList.valueOf(color)
        mLabelTextView!!.setTextColor(color)
    }

    /**
     * Show or hide avatar icon
     *
     * @param hasAvatarIcon true to show, false to hide
     */
    fun setHasAvatarIcon(hasAvatarIcon: Boolean) {
        mHasAvatarIcon = hasAvatarIcon

        if (!mHasAvatarIcon) {
            // hide icon
            mAvatarIconImageView!!.visibility = GONE
            // adjust padding
            if (mDeleteButton!!.visibility == VISIBLE) mLabelTextView!!.setPadding(
                ViewUtil.dpToPx(
                    12
                ), 0, 0, 0
            )
            else mLabelTextView!!.setPadding(ViewUtil.dpToPx(12), 0, ViewUtil.dpToPx(12), 0)
        } else {
            // show icon
            mAvatarIconImageView!!.visibility = VISIBLE
            // adjust padding
            if (mDeleteButton!!.visibility == VISIBLE) mLabelTextView!!.setPadding(
                ViewUtil.dpToPx(8),
                0,
                0,
                0
            )
            else mLabelTextView!!.setPadding(ViewUtil.dpToPx(8), 0, ViewUtil.dpToPx(12), 0)

            // set icon
            if (mAvatarIconUri != null) mAvatarIconImageView!!.setImageURI(mAvatarIconUri)
            else if (mAvatarIconDrawable != null) mAvatarIconImageView!!.setImageDrawable(
                mAvatarIconDrawable
            )
            else mAvatarIconImageView!!.setImageBitmap(mLetterTileProvider!!.getLetterTile(label))
        }
    }

    /**
     * Set avatar icon
     *
     * @param avatarIcon the icon to set
     */
    fun setAvatarIcon(avatarIcon: Drawable?) {
        mAvatarIconDrawable = avatarIcon
        mHasAvatarIcon = true
        inflateWithAttributes()
    }

    /**
     * Set avatar icon
     *
     * @param avatarUri the uri of the icon to set
     */
    fun setAvatarIcon(avatarUri: Uri?) {
        mAvatarIconUri = avatarUri
        mHasAvatarIcon = true
        inflateWithAttributes()
    }

    /**
     * Show or hide delte button
     *
     * @param deletable true to show, false to hide
     */
    fun setDeletable(deletable: Boolean) {
        mDeletable = deletable
        if (!mDeletable) {
            // hide delete icon
            mDeleteButton!!.visibility = GONE
            // adjust padding
            if (mAvatarIconImageView!!.visibility == VISIBLE) mLabelTextView!!.setPadding(
                ViewUtil.dpToPx(
                    8
                ), 0, ViewUtil.dpToPx(12), 0
            )
            else mLabelTextView!!.setPadding(ViewUtil.dpToPx(12), 0, ViewUtil.dpToPx(12), 0)
        } else {
            // show icon
            mDeleteButton!!.visibility = VISIBLE
            // adjust padding
            if (mAvatarIconImageView!!.visibility == VISIBLE) mLabelTextView!!.setPadding(
                ViewUtil.dpToPx(
                    8
                ), 0, 0, 0
            )
            else mLabelTextView!!.setPadding(ViewUtil.dpToPx(12), 0, 0, 0)

            // set icon
            if (mDeleteIcon != null) mDeleteButton!!.setImageDrawable(mDeleteIcon)
            if (mDeleteIconColor != null) mDeleteButton!!.drawable.mutate().setColorFilter(
                mDeleteIconColor!!.defaultColor, PorterDuff.Mode.SRC_ATOP
            )
        }
    }

    /**
     * Set delete icon color
     *
     * @param color the color to set
     */
    fun setDeleteIconColor(color: ColorStateList?) {
        mDeleteIconColor = color
        mDeletable = true
        inflateWithAttributes()
    }

    /**
     * Set delete icon color
     *
     * @param color the color to set
     */
    fun setDeleteIconColor(@ColorInt color: Int) {
        mDeleteIconColor = ColorStateList.valueOf(color)
        mDeletable = true
        inflateWithAttributes()
    }

    /**
     * Set delete icon
     *
     * @param deleteIcon the icon to set
     */
    fun setDeleteIcon(deleteIcon: Drawable?) {
        mDeleteIcon = deleteIcon
        mDeletable = true
        inflateWithAttributes()
    }

    /**
     * Set background color
     *
     * @param color the color to set
     */
    fun setChipBackgroundColor(color: ColorStateList?) {
        if (color != null) {
            setChipBackgroundColor(color.defaultColor)
        }
    }

    /**
     * Set background color
     *
     * @param color the color to set
     */
    fun setChipBackgroundColor(@ColorInt color: Int) {
        mContentLayout!!.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    /**
     * Set OnClickListener on the delete button
     *
     * @param onClickListener the OnClickListener
     */
    fun setOnDeleteClicked(onClickListener: OnClickListener?) {
        mDeleteButton!!.setOnClickListener(onClickListener)
    }


    fun setOnChipClicked(listener: OnChipClickListener) {
        if (onClickListenerList == null) {
            onClickListenerList = ArrayList()
        }

        onClickListenerList!!.add(listener)

        mContentLayout!!.setOnClickListener { v: View? ->
            for (i in onClickListenerList!!.indices) {
                onClickListenerList!![i].onClick(this)
            }
        }
    }

    /**
     * Builder class
     */
    class Builder(val context: Context) {
        var label: String? = null
        var labelColor: ColorStateList? = null
        var hasAvatarIcon: Boolean = false
        var avatarIconUri: Uri? = null
        var avatarIconDrawable: Drawable? = null
        var deletable: Boolean = false
        var deleteIcon: Drawable? = null
        var deleteIconColor: ColorStateList? = null
        var backgroundColor: ColorStateList? = null
        var isSelected: Boolean = false
        var selectedColor: ColorStateList? = null
        var selectedTextColor: ColorStateList? = null
        var chip: ChipInterface? = null

        fun label(label: String?): Builder {
            this.label = label
            return this
        }

        fun labelColor(labelColor: ColorStateList?): Builder {
            this.labelColor = labelColor
            return this
        }

        fun hasAvatarIcon(hasAvatarIcon: Boolean): Builder {
            this.hasAvatarIcon = hasAvatarIcon
            return this
        }

        fun avatarIcon(avatarUri: Uri?): Builder {
            this.avatarIconUri = avatarUri
            return this
        }

        fun avatarIcon(avatarIcon: Drawable?): Builder {
            this.avatarIconDrawable = avatarIcon
            return this
        }

        fun deletable(deletable: Boolean): Builder {
            this.deletable = deletable
            return this
        }

        fun deleteIcon(deleteIcon: Drawable?): Builder {
            this.deleteIcon = deleteIcon
            return this
        }

        fun deleteIconColor(deleteIconColor: ColorStateList?): Builder {
            this.deleteIconColor = deleteIconColor
            return this
        }

        fun backgroundColor(backgroundColor: ColorStateList?): Builder {
            this.backgroundColor = backgroundColor
            return this
        }

        fun selectedColor(selectedColor: ColorStateList?): Builder {
            this.selectedColor = selectedColor
            return this
        }

        fun selectedTextColor(selectedTextColor: ColorStateList?): Builder {
            this.selectedTextColor = selectedTextColor
            return this
        }

        fun isSelected(isSelected: Boolean): Builder {
            this.isSelected = isSelected
            return this
        }

        fun chip(chip: ChipInterface): Builder {
            this.chip = chip
            this.label = chip.label
            this.avatarIconDrawable = chip.avatarDrawable
            this.avatarIconUri = chip.avatarUri
            return this
        }

        fun build(): ChipView {
            return newInstance(this)
        }
    }

    fun setChipSelected() {
        isChipSelected = !mIsSelected
    }

    var isChipSelected: Boolean
        get() = mIsSelected
        set(isSelected) {
            this.mIsSelected = isSelected

            if (isSelected) {
                setChipBackgroundColor(mSelectedColor)
                setLabelColor(mSelectedTextColor)
            } else {
                setChipBackgroundColor(mBackgroundColor)
                setLabelColor(mLabelColor)
            }
        }

    companion object {
        private val TAG = ChipView::class.java.toString()

        // attributes
        private const val NONE = -1
        private fun newInstance(builder: Builder): ChipView {
            val chipView = ChipView(builder.context)
            chipView.mLabel = builder.label
            chipView.mLabelColor = builder.labelColor
            chipView.mHasAvatarIcon = builder.hasAvatarIcon
            chipView.mAvatarIconUri = builder.avatarIconUri
            chipView.mAvatarIconDrawable = builder.avatarIconDrawable
            chipView.mDeletable = builder.deletable
            chipView.mDeleteIcon = builder.deleteIcon
            chipView.mDeleteIconColor = builder.deleteIconColor
            chipView.mBackgroundColor = builder.backgroundColor
            chipView.mIsSelected = builder.isSelected
            chipView.mSelectedColor = builder.selectedColor
            chipView.mSelectedTextColor = builder.selectedTextColor
            chipView.chip = builder.chip
            chipView.inflateWithAttributes()

            return chipView
        }
    }
}
