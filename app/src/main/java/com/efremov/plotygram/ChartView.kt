package com.efremov.plotygram

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat

class ChartView : View {

    val paint = Paint()

    var currentX: Float = 100f

    constructor(context: Context) : super(context) {
//        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        init()
    }

//    private fun init() {
//        LayoutInflater.from(context).inflate(R.layout.chart_view, this, true)
//    }

    fun setCurrX(x: Float) {
        currentX = x
    }

    fun getCurrX() : Float = currentX

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.color = ContextCompat.getColor(context, R.color.color4)
        paint.strokeWidth = 30f

        canvas?.drawLine(currentX, 0f, currentX, 600f, paint)
//        canvas?.drawLine(20f, 0f, 0f, 20f, paint)
    }
}