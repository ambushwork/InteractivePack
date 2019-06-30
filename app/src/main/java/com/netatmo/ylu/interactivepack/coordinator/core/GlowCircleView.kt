package com.netatmo.ylu.interactivepack.coordinator.core

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

/**
 * This view is to demonstrate the usage of PathMeasure and PorterDuff, to draw a circle image view with
 * a gradient glowing stoke running around.
 */
class GlowCircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ImageView(context, attrs, defStyleAttr) {
    private val paint = Paint()
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        if (drawable == null) {
            super.onDraw(canvas)
            return
        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val bitmapDrawable = drawable as BitmapDrawable?
        val bitmap = bitmapDrawable?.bitmap
        val radius = Math.min(bitmap!!.width, bitmap.height).toFloat() / 2
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        canvas?.drawARGB(0, 0, 0, 0)
        paint.color = 0xff000000.toInt()
        paint.isAntiAlias = true
        canvas?.drawCircle(bitmap.width / 2.toFloat(), bitmap.height / 2.toFloat(), radius, paint)
        //SRC is always on top, DST is always on bottom
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas?.drawBitmap(bitmap, rect, rectF, paint)
        paint.xfermode = null
    }

}