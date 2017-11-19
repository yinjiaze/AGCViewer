package edu.buaa.bravomikekilo.agcviewer

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.collections.ArrayList

/**
 * TODO: document your custom view class.
 * a simple view to draw a fraction graph with two axis
 */
class ChartView: View {

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        values = getRandomValues(10)
        paint = Paint()
    }

    private var values: ArrayList<Int>
    private var paint: Paint

    private val rand: Random = Random()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val vWidth = width.toFloat()
        val vHeight = height.toFloat()
        val step = vHeight / 9
        var height = normalize(values[0], vHeight)
        for(i in 1..9){
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

    fun changeValues(){
        values = getRandomValues(10)
        invalidate()
    }

    fun getRandomValues(size: Int): ArrayList<Int>{
        val ret = ArrayList<Int>()
        for(i in 1..size){
            ret.add(rand.nextInt(4096))
        }
        return ret
    }

    fun normalize(raw: Int, bound: Float): Float {
        return raw.toFloat() / 4096 * bound
    }

}
