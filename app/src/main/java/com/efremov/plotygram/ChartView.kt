package com.efremov.plotygram

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat

class ChartView : View {

    private val minDistance = 100

    private lateinit var paint: Paint
    private lateinit var paint2: Paint
    private lateinit var path: Path

    var currentXStart: Float = 100f
    var currentXEnd: Float = 100f + 30f

    var x1 = 0f
    var x2 = 0f

    private lateinit var chartData: ChartData

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        setWillNotDraw(false)

        context.let {
            paint = Paint()
            paint2 = Paint()

            path = Path()

            paint.color = ContextCompat.getColor(context, R.color.color4)
            paint.strokeWidth = 10f
            paint.isAntiAlias = true

            paint2.style = Paint.Style.STROKE
            paint2.color = ContextCompat.getColor(context, R.color.color5)
            paint2.strokeWidth = 5f
            paint2.isAntiAlias = true
            paint2.isDither = true
            paint2.strokeJoin = Paint.Join.ROUND
            paint2.strokeCap = Paint.Cap.ROUND
        }
    }

    fun setChartData(chartData: ChartData) {
        this.chartData = chartData
    }

    fun getChartData() : ChartData = this.chartData

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                x1 = event.x
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_MOVE -> {

                x2 = event.x

                val deltaX = x2 - x1

                if (Math.abs(deltaX) > minDistance) {

                    Log.d("LEFT_TO_RIGHT", x1.toString())
//                            if (event.x >= chartView.currentXStart && event.x <= chartView.currentXEnd) {
                    currentXStart = event.x
                    currentXEnd = event.x + 30f
                    invalidate()
//                            }

                } else {

                    Log.d("RIGHT_TO_LEFT", (-x1).toString())
//                            if (event.x >= chartView.currentXStart && event.x <= chartView.currentXEnd) {
                    currentXStart = event.x
                    currentXEnd = event.x - 30f
                    invalidate()
//                            }
                }
            }
        }

//                if (event?.x!! >= chartView.currentXStart && event.x <= chartView.currentXEnd) {
//                    chartView.currentXStart = event.x
//                    chartView.currentXEnd = event.x + 30f
//                    chartView.invalidate()
//                }

        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        canvas?.drawLine(20f, 0f, 500f, 300f, paint2)

        path.reset()
//        path.moveTo(50f, 50f)
//        path.lineTo(300f, 50f)
//        path.lineTo(100f, 400f)
//        path.lineTo(400f, 400f)
//        path.close()

        path.moveTo(50f, 50f)
        path.lineTo(100f, 200f)
        path.lineTo(150f, 100f)
        path.lineTo(800f, 50f)

        canvas?.drawPath(path, paint2)

        canvas?.drawLine(currentXStart, 0f, currentXStart, 600f, paint)
    }

    fun drawChart(chartData: ChartData, canvas: Canvas) {

        val yoColor = Color.parseColor(chartData.colors.y0)
        val y1Color = Color.parseColor(chartData.colors.y1)

        val xAxis = chartData.columns[0].subList(1, chartData.columns[0].size)
        val y0Axis = chartData.columns[1].subList(1, chartData.columns[1].size)
        val y1Axis = chartData.columns[2].subList(1, chartData.columns[2].size)

        val y0Paint = Paint()
        val y0Path = Path()

        y0Paint.style = Paint.Style.STROKE
        y0Paint.color = ContextCompat.getColor(context, R.color.color5)
        y0Paint.strokeWidth = 3f
        y0Paint.isAntiAlias = true
        y0Paint.isDither = true
        y0Paint.strokeJoin = Paint.Join.ROUND
        y0Paint.strokeCap = Paint.Cap.ROUND

        xAxis.asSequence().forEachIndexed { index, value ->

        }

    }
}