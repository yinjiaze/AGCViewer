package edu.buaa.bravomikekilo.agcviewer

import android.app.Activity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        button.setOnClickListener { view -> change(view) }
        title = "Show"
    }

    fun change(view: View){
        chart.changeValues()
    }

}
package com.example.beihang.testkotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.collections.ArrayList

/**
 * TODO: document your custom view class.
 */
class ChartView : View {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        values =
        paint = Paint()
    }

    private var values: ArrayList<Int>
    private var paint:Paint
    private val rand: Random = Random()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLine(values,canvas,paint)

    }

        fun normalize(raw: Int, bound: Float): Float {
            return raw.toFloat() / 4096 * bound
        }

        fun changeValues() {
            addValues(values)
            invalidate()
        }

        fun addValues(values:ArrayList<Int>) {
            values.add(rand.nextInt(4096))
        }

        fun drawLine(values:ArrayList<Int>,canvas:Canvas,paint: Paint) {
            val vWidth = width.toFloat()
            val vHeight = height.toFloat()
            val step = vWidth / (values.size)-1
            var height = normalize(values[0], vHeight)
            for (i in values.indices) {
                val nHeight = normalize(values[i], vHeight)
                canvas.drawLine(
                        step * i - step,
                        height,
                        step * i,
                        nHeight,
                        paint
                )
                height = nHeight
            }
    }

    }


