package com.zt.mvi.demo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.*

class FollowAnimationButton : View {

    private var rectRadius = 0f
    private var rect: RectF = RectF()
    private var rectPaint: Paint = Paint()
    private var plusPaint: Paint = Paint()
    private var plusLineWidth = 0f
    private var plusPath = Path()

    private var okPaint = Paint()
    private var okLineWidth = 0f
    private var okPath = Path()
    private var pathMeasure: PathMeasure? = null
    private var startDrawOk = false

    private var animatorDrawOk: ValueAnimator? = null

    private var effect: PathEffect? = null
    private var isAnimating = false

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        rectRadius = dp2px(context, 15f).toFloat()
        plusLineWidth = dp2px(context, 2f).toFloat()

        rectPaint.isAntiAlias = true
        rectPaint.color = 0xFFEE0051.toInt()
        rectPaint.style = Paint.Style.FILL

        plusPaint.isAntiAlias = true
        plusPaint.color = Color.WHITE
        plusPaint.style = Paint.Style.STROKE
        plusPaint.strokeWidth = plusLineWidth

        okLineWidth = dp2px(context, 2.5f).toFloat()
        okPaint.isAntiAlias = true
        okPaint.color = 0xFFEE0051.toInt()
        okPaint.style = Paint.Style.STROKE
        okPaint.strokeWidth = okLineWidth
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val plusSize = measuredHeight * 0.6f
        plusPath.moveTo(measuredWidth / 2f, (measuredHeight - plusSize) / 2)
        plusPath.lineTo(measuredWidth / 2f, (measuredHeight + plusSize) / 2)
        plusPath.moveTo((measuredWidth - plusSize) / 2, measuredHeight / 2f)
        plusPath.lineTo((measuredWidth + plusSize) / 2, measuredHeight / 2f)

        okPath.moveTo(measuredWidth * 0.31f, measuredHeight * 0.5f)
        okPath.lineTo(measuredWidth * 0.45f, measuredHeight * 0.75f)
        okPath.lineTo(measuredWidth * 0.68f, measuredHeight * 0.25f)
        pathMeasure = PathMeasure(okPath, true)
    }

    override fun onDraw(canvas: Canvas) {

        rect.apply {
            left = 0f
            top = 0f
            right = measuredWidth.toFloat()
            bottom = measuredHeight.toFloat()
        }

        //画勾
        if (startDrawOk) {
            rectPaint.color = Color.WHITE
            canvas.drawRoundRect(rect, rectRadius, rectRadius, rectPaint)
            canvas.drawPath(okPath, okPaint)
        } else {
            rectPaint.color = 0xFFEE0051.toInt()
            canvas.drawRoundRect(rect, rectRadius, rectRadius, rectPaint)
            //加号
            canvas.drawPath(plusPath, plusPaint)
        }

    }

    fun startFollowAnimation(animationEnd: () -> Unit) {
        isAnimating = true
        val startScaleAnimation = ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        startScaleAnimation.duration = 300
        startScaleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                startDrawOk = true
                animatorDrawOk = ValueAnimator.ofFloat(1f, 0f).apply {
                    duration = 1000
                    addUpdateListener {
                        val value = it.animatedValue as Float
                        effect = DashPathEffect(floatArrayOf(pathMeasure!!.length, pathMeasure!!.length), value * pathMeasure!!.length)
                        okPaint.pathEffect = effect
                        invalidate()
                    }
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            val animationSet = AnimationSet(true)
                            val alphaAnimation = AlphaAnimation(1f, 0.2f)
                            val scaleAnimation = ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
                            animationSet.addAnimation(alphaAnimation)
                            animationSet.addAnimation(scaleAnimation)
                            animationSet.duration = 300
                            animationSet.interpolator = AccelerateInterpolator()
                            animationSet.setAnimationListener(object : Animation.AnimationListener {
                                override fun onAnimationStart(animation: Animation?) {

                                }

                                override fun onAnimationEnd(animation: Animation?) {
                                    startDrawOk = false
                                    isAnimating = false
                                    animationEnd()
                                }

                                override fun onAnimationRepeat(animation: Animation?) {

                                }
                            })
                            startAnimation(animationSet)
                        }
                    })
                    start()
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        startAnimation(startScaleAnimation)
    }

    fun isAnimating(): Boolean {
        return isAnimating
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

}