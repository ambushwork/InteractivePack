package com.netatmo.ylu.interactivepack.matrix

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

class TouchableImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                   defStyleAttr: Int = 0) :
    AppCompatImageView(context, attrs, defStyleAttr) {
    private var mBitmap: Bitmap? = null
    override fun onDraw(canvas: Canvas) {
        canvas.save()
        if (drawable is BitmapDrawable) {
            mBitmap = (drawable as BitmapDrawable).bitmap
            canvas.drawBitmap(mBitmap!!, matrix, null)
        }
        canvas.restore()
    }
}