package com.netatmo.ylu.interactivepack.coordinator.core

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.interpolator.view.animation.FastOutLinearInInterpolator

class ScaleBehavior<V : View?>(private val context: Context, private val attributeSet: AttributeSet) :
    CoordinatorLayout.Behavior<V>(context, attributeSet) {
    var isAnimating: Boolean = false
    val fastOutLinearInInterpolator = FastOutLinearInInterpolator()

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View,
                                     target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View, dxConsumed: Int,
                                dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type,
            consumed)
        if (dyConsumed > 0 && !isAnimating && child!!.visibility == View.VISIBLE) {
            scaleOut(child)
        } else if (dyConsumed < 0 && !isAnimating && child!!.visibility == View.INVISIBLE) {
            scaleIn(child)
        }
    }

    fun scaleIn(v: V) {
        v!!.visibility = View.VISIBLE
        ViewCompat.animate(v).scaleX(1f).scaleY(1f).setListener(object : ViewPropertyAnimatorListener {
            override fun onAnimationEnd(view: View?) {
                isAnimating = false
            }

            override fun onAnimationCancel(view: View?) {
                isAnimating = false
            }

            override fun onAnimationStart(view: View?) {
                isAnimating = true
            }

        }).setInterpolator(fastOutLinearInInterpolator).start()
    }

    fun scaleOut(v: V) {
        ViewCompat.animate(v!!).scaleX(0f).scaleY(0f).setListener(object : ViewPropertyAnimatorListener {
            override fun onAnimationEnd(view: View?) {
                isAnimating = false
                v.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(view: View?) {
                isAnimating = false
            }

            override fun onAnimationStart(view: View?) {
                isAnimating = true
            }

        }).setInterpolator(fastOutLinearInInterpolator).start()
    }
}