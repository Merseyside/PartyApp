package com.pchmn.materialchips.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.merseyside.utils.binding.viewBinding
import com.pchmn.materialchips.R
import com.pchmn.materialchips.databinding.DetailedChipViewBinding
import com.pchmn.materialchips.model.ChipInterface
import com.pchmn.materialchips.util.ColorUtil
import com.pchmn.materialchips.util.LetterTileProvider
import de.hdodenhof.circleimageview.CircleImageView

class DetailedChipView : RelativeLayout {
    // context
    private var mContext: Context

    private val binding by viewBinding<DetailedChipViewBinding>(R.layout.detailed_chip_view, attachToParent = false)

    // xml elements
    val mContentLayout: RelativeLayout by lazy { binding.content }

    val mAvatarIconImageView: CircleImageView by lazy { binding.avatarIcon }

    val mNameTextView: TextView by lazy { binding.name }

    val mInfoTextView: TextView by lazy { binding.info }

    val mDeleteButton: ImageButton by lazy { binding.deleteButton }

    // attributes
    private var mBackgroundColor: ColorStateList? = null

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
        // letter tile provider
        mLetterTileProvider = LetterTileProvider(mContext)

        // hide on first
        setVisibility(View.GONE)
        // hide on touch outside
        hideOnTouchOutside()
    }

    /**
     * Hide the view on touch outside of it
     */
    private fun hideOnTouchOutside() {
        // set focusable
        setFocusable(true)
        setFocusableInTouchMode(true)
        setClickable(true)
    }

    /**
     * Fade in
     */
    fun fadeIn() {
        val anim: AlphaAnimation = AlphaAnimation(0.0f, 1.0f)
        anim.setDuration(200)
        startAnimation(anim)
        setVisibility(View.VISIBLE)
        // focus on the view
        requestFocus()
    }

    /**
     * Fade out
     */
    fun fadeOut() {
        val anim: AlphaAnimation = AlphaAnimation(1.0f, 0.0f)
        anim.setDuration(200)
        startAnimation(anim)
        setVisibility(View.GONE)
        // fix onclick issue
        clearFocus()
        setClickable(false)
    }

    fun setAvatarIcon(icon: Drawable?) {
        mAvatarIconImageView.setImageDrawable(icon)
    }

    fun setAvatarIcon(icon: Bitmap?) {
        mAvatarIconImageView.setImageBitmap(icon)
    }

    fun setAvatarIcon(icon: Uri?) {
        mAvatarIconImageView.setImageURI(icon)
    }

    fun setName(name: String?) {
        mNameTextView!!.text = name
    }

    fun setInfo(info: String?) {
        if (info != null) {
            mInfoTextView!!.visibility = View.VISIBLE
            mInfoTextView!!.text = info
        } else {
            mInfoTextView!!.visibility = View.GONE
        }
    }

    fun setTextColor(color: ColorStateList) {
        mNameTextView.setTextColor(color)
        mInfoTextView.setTextColor(ColorUtil.alpha(color.getDefaultColor(), 150))
    }

    fun setBackGroundcolor(color: ColorStateList) {
        mBackgroundColor = color
        mContentLayout.getBackground()
            .setColorFilter(color.getDefaultColor(), PorterDuff.Mode.SRC_ATOP)
    }

    val backgroundColor: Int
        get() = if (mBackgroundColor == null) ContextCompat.getColor(
            mContext,
            R.color.colorAccent
        ) else mBackgroundColor!!.getDefaultColor()

    fun setDeleteIconColor(color: ColorStateList) {
        mDeleteButton.getDrawable().mutate()
            .setColorFilter(color.getDefaultColor(), PorterDuff.Mode.SRC_ATOP)
    }

    fun setOnDeleteClicked(onClickListener: View.OnClickListener?) {
        mDeleteButton.setOnClickListener(onClickListener)
    }

    fun alignLeft() {
        val params: RelativeLayout.LayoutParams =
            mContentLayout.getLayoutParams() as RelativeLayout.LayoutParams
        params.leftMargin = 0
        mContentLayout.setLayoutParams(params)
    }

    fun alignRight() {
        val params: RelativeLayout.LayoutParams =
            mContentLayout.getLayoutParams() as RelativeLayout.LayoutParams
        params.rightMargin = 0
        mContentLayout.setLayoutParams(params)
    }

    class Builder(val context: Context) {
        var avatarUri: Uri? = null
        var avatarDrawable: Drawable? = null
        var name: String? = null
        var info: String? = null
        var textColor: ColorStateList? = null
        var backgroundColor: ColorStateList? = null
        var deleteIconColor: ColorStateList? = null

        fun avatar(avatarUri: Uri?): Builder {
            this.avatarUri = avatarUri
            return this
        }

        fun avatar(avatarDrawable: Drawable?): Builder {
            this.avatarDrawable = avatarDrawable
            return this
        }

        fun name(name: String?): Builder {
            this.name = name
            return this
        }

        fun info(info: String?): Builder {
            this.info = info
            return this
        }

        fun chip(chip: ChipInterface): Builder {
            this.avatarUri = chip.getAvatarUri()
            this.avatarDrawable = chip.getAvatarDrawable()
            this.name = chip.getLabel()
            this.info = chip.getInfo()
            return this
        }

        fun textColor(textColor: ColorStateList?): Builder {
            this.textColor = textColor
            return this
        }

        fun backgroundColor(backgroundColor: ColorStateList?): Builder {
            this.backgroundColor = backgroundColor
            return this
        }

        fun deleteIconColor(deleteIconColor: ColorStateList?): Builder {
            this.deleteIconColor = deleteIconColor
            return this
        }

        fun build(): DetailedChipView {
            return newInstance(this)
        }
    }

    companion object {
        private val TAG = DetailedChipView::class.java.toString()

        // letter tile provider
        private var mLetterTileProvider: LetterTileProvider? = null
        private fun newInstance(builder: Builder): DetailedChipView {
            val detailedChipView = DetailedChipView(builder.context)
            // avatar
            if (builder.avatarUri != null) detailedChipView.setAvatarIcon(builder.avatarUri)
            else if (builder.avatarDrawable != null) detailedChipView.setAvatarIcon(builder.avatarDrawable)
            else detailedChipView.setAvatarIcon(mLetterTileProvider!!.getLetterTile(builder.name))

            // background color
            if (builder.backgroundColor != null) detailedChipView.setBackGroundcolor(builder.backgroundColor!!)

            // text color
            if (builder.textColor != null) detailedChipView.setTextColor(builder.textColor!!)
            else if (ColorUtil.isColorDark(detailedChipView.backgroundColor)) detailedChipView.setTextColor(
                ColorStateList.valueOf(
                    Color.WHITE
                )
            )
            else detailedChipView.setTextColor(ColorStateList.valueOf(Color.BLACK))

            // delete icon color
            if (builder.deleteIconColor != null) detailedChipView.setDeleteIconColor(builder.deleteIconColor!!)
            else if (ColorUtil.isColorDark(detailedChipView.backgroundColor)) detailedChipView.setDeleteIconColor(
                ColorStateList.valueOf(
                    Color.WHITE
                )
            )
            else detailedChipView.setDeleteIconColor(ColorStateList.valueOf(Color.BLACK))

            detailedChipView.setName(builder.name)
            detailedChipView.setInfo(builder.info)
            return detailedChipView
        }
    }
}
