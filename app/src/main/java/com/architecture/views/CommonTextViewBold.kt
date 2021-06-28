package com.architecture.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.util.AttributeSet

/**
 * Created by Piyush Prajapati on 27-JAN-2020.
 * at http://www.dotsquares.com/
 */
class CommonTextViewBold : androidx.appcompat.widget.AppCompatTextView {


    /**
     * Instantiates a new Common text view.
     *
     * @param context the context
     */
    constructor(context: Context) : super(context) {
        val face = Typeface.createFromAsset(context.assets, "fonts/poppins_bold.ttf")
        this.typeface = face
    }

    /**
     * Instantiates a new Common text view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val face = Typeface.createFromAsset(context.assets, "fonts/poppins_bold.ttf")
        this.typeface = face
    }

    /**
     * Instantiates a new Common text view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        val face = Typeface.createFromAsset(context.assets, "fonts/poppins_bold.ttf")
        this.typeface = face
    }

    /**
     * On draw.
     *
     * @param canvas the canvas
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


    }
}
