package com.netatmo.ylu.interactivepack.glowingview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import com.netatmo.ylu.interactivepack.R

class GlowingView @JvmOverloads constructor(context: Context, private val attrs: AttributeSet? = null,
                                            defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr) {
    private lateinit var rect: Rect
    private var repeatCount: Int = -1
    private var scanAngle = 45f
    private var interval = 0
    private var duration = 1000
    private var glowWidth: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var scanRadius: Float = 0f
    private val paint = Paint()
    private val mMatrix = Matrix()
    private var canvasScaleX = 0f
    private var canvasScaleY = 0f
    private var startTime = 0L

    companion object {
        const val COLOR_FULL_WHITE = 0xffffffff.toInt()
        const val COLOR_HALF_WHITE = 0x00000000.toInt()
        const val DEFAULT_SCAN_ANGLE = 45f
        const val DEFAULT_REPEAT_COUNT = -1
        const val DEFAULT_DURATION = 1000
        const val DEFAULT_INTERVAL = 0
    }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.GlowingView)
        scanAngle = ta.getFloat(R.styleable.GlowingView_angle, DEFAULT_SCAN_ANGLE)
        repeatCount = ta.getInt(R.styleable.GlowingView_repeat, DEFAULT_REPEAT_COUNT)
        duration = ta.getInt(R.styleable.GlowingView_duration, DEFAULT_DURATION)
        interval = ta.getInt(R.styleable.GlowingView_interval, DEFAULT_INTERVAL)
        ta.recycle()
    }

    fun glow() {
        startTime = System.currentTimeMillis()
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect = Rect(0, 0, w, h)
        centerX = w.toFloat() / 2
        centerY = h.toFloat() / 2
        scanRadius = Math.sqrt(w * w.toDouble() + h * h).toFloat() / 2
        glowWidth = width / 2.toFloat()
        paint.shader = LinearGradient(0f, 0f, glowWidth, 0f,
            intArrayOf(COLOR_HALF_WHITE, COLOR_FULL_WHITE, COLOR_FULL_WHITE, COLOR_HALF_WHITE),
            floatArrayOf(0f, 0.5f, 0.7f, 1f), Shader.TileMode.CLAMP)
        canvasScaleX = scanRadius * 2 / w
        canvasScaleY = scanRadius * 2 / h
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!shouldContinue()) {
            return
        }
        canvas.save()
        mMatrix.reset()
        mMatrix.setRotate(scanAngle, centerX, centerY)
        mMatrix.postScale(canvasScaleX, canvasScaleY)
        mMatrix.preTranslate(getTravelDistance(), 0f)
        canvas.concat(mMatrix)
        canvas.drawCircle(centerX, centerY, scanRadius, paint)
        canvas.restore()
        invalidate()
    }

    private fun getFraction(): Float {
        val travelTime = (System.currentTimeMillis() - startTime).toFloat()
        val singleTravelTime = travelTime % (duration + interval)
        return singleTravelTime / duration
    }

    private fun shouldContinue(): Boolean {
        return if (repeatCount < 0) {
            true
        } else {
            (System.currentTimeMillis() - startTime) < repeatCount * (duration + interval)
        }
    }

    private fun getTravelDistance(): Float {
        val fraction = getFraction()
        return if (fraction >= 1) {
            (2 * scanRadius + glowWidth) - glowWidth
        } else {
            getFraction() * (2 * scanRadius + glowWidth) - glowWidth
        }
    }

}