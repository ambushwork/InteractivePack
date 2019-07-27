package com.netatmo.ylu.interactivepack.vectoranimation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

class SecurityScanView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ImageView(context, attrs, defStyleAttr) {
    private val paint = Paint()
    private var angle1 = 180
    private var angle2 = 0

    companion object {
        const val ANGLE_INCREMENT_1 = 1
        const val ANGLE_INCREMENT_2 = 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = 0xff135484.toInt()
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        angle1 += ANGLE_INCREMENT_1
        angle2 += ANGLE_INCREMENT_2
        if (angle1 > 360) {
            angle1 %= 360
        }
        if (angle2 > 360) {
            angle2 %= 360
        }
        canvas.drawArc(0f, 0f, width.toFloat(), height.toFloat(), Math.min(angle1.toFloat(), angle2.toFloat()),
            Math.max(angle1.toFloat(), angle2.toFloat()), true, paint)
        invalidate()
    }
}