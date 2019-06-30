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


    companion object {
        const val GLOW_STRIKE_WIDTH = 6f
        const val COLOR_GLOW_ORANGE = 0xffFF9933.toInt()
        const val COLOR_GLOW_PINK = 0xffff3366.toInt()
    }

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
        val cx = bitmap.width / 2.toFloat()
        val cy = bitmap.height / 2.toFloat()
        canvas?.drawARGB(0, 0, 0, 0)
        paint.color = 0xff000000.toInt()
        paint.isAntiAlias = true
        canvas?.drawCircle(cx, cy, radius, paint)
        //SRC is always on top, DST is always on bottom
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas?.drawBitmap(bitmap, rect, rectF, paint)
        paint.xfermode = null

        //canvas?.saveLayer(rectF, paint)

        canvas?.save()
        paint.reset()
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = GLOW_STRIKE_WIDTH
        paint.color = COLOR_GLOW_PINK
        canvas?.rotate(45f, cx, cy)
        paint.shader = LinearGradient(0f, cy, cx * 2 / 3, cy, COLOR_GLOW_ORANGE, COLOR_GLOW_PINK, Shader.TileMode.CLAMP)

        // the stroke is drawn along the side of the circle, so if we don't want the stroke out of the bound, we need to minus the radius by GLOW_STRIKE_WIDTH
        canvas?.drawCircle(cx, cy, radius - GLOW_STRIKE_WIDTH / 2, paint)
        paint.reset()
        canvas?.restore()
    }

}