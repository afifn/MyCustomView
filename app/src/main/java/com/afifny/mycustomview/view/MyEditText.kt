package com.afifny.mycustomview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.afifny.mycustomview.R

class MyEditText : AppCompatEditText, View.OnTouchListener {
    private lateinit var clearButtonImage : Drawable

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = "Masukkan nama anda"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24) as Drawable
        setOnTouchListener(this)

        // menampilkan clear button
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()) showClearButton() else hideClearButton()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun showClearButton() {
        setButtonDrawable(endOfText = clearButtonImage)
    }

    private fun hideClearButton() {
        setButtonDrawable()
    }

    private fun setButtonDrawable(
        startOfText: Drawable? = null,
        topOfText : Drawable? = null,
        endOfText: Drawable? = null,
        bottomOfText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfText, topOfText, endOfText, bottomOfText
        )
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicker = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (clearButtonImage.intrinsicWidth + paddingStart).toFloat()
                if (p1 != null) {
                    when {
                        p1.x < clearButtonEnd -> isClearButtonClicker = true
                    }
                }
            } else{
                clearButtonStart = (width - paddingEnd - clearButtonImage.intrinsicWidth).toFloat()
                if (p1 != null) {
                    when {
                        p1.x > clearButtonStart -> isClearButtonClicker = true
                    }
                }
            }
            if (isClearButtonClicker) {
                if (p1 != null) {
                    when (p1.action) {
                        MotionEvent.ACTION_DOWN -> {
                            clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24) as Drawable
                            showClearButton()
                            return true
                        }
                        MotionEvent.ACTION_UP -> {
                            clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24) as Drawable
                            when {
                                text != null -> text?.clear()
                            }
                            hideClearButton()
                            return true
                        }
                        else -> return false
                    }
                }
            }
            else return false
        }
        return false
    }
}
