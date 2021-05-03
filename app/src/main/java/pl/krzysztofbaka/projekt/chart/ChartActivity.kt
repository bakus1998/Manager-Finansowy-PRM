package pl.krzysztofbaka.projekt.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.widget.Toast

class ChartActivity(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    val dataPoints = mutableListOf<DataPoint>()

    var xMin = 0
    var xMax = 0
    var yMin = 0
    var yMax = 0

    fun Int.toPaintX() = toFloat() / xMax * width
    fun Int.toPaintY() = toFloat() / yMax * height


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
       // canvas.translate(0f, height / 2.toFloat())
        val paint = Paint()
        paint.color = Color.GREEN
        paint.strokeWidth = 5f
        paint.style = Paint.Style.STROKE

        val path = Path()
        if(dataPoints.size>0) {
            path.moveTo(dataPoints[0].xVal.toPaintX(), dataPoints[0].yVal.toPaintY() / 2)
            for (i in 1 until dataPoints.size) {
                path.lineTo(dataPoints[i].xVal.toPaintX(), dataPoints[i].yVal.toPaintY() / 2)
            }
            canvas.drawPath(path, paint)
        }else{
            Toast.makeText(context, "Brak danych bądź brak przychodu", Toast.LENGTH_LONG).show()
        }

    }

    fun setData(lista: List<DataPoint>) {
        xMin = lista.minByOrNull { it.xVal }?.xVal ?: 0
        xMax = lista.maxByOrNull { it.xVal }?.xVal ?: 0
        yMin = lista.minByOrNull { it.yVal }?.yVal ?: 0
        yMax = lista.maxByOrNull { it.yVal }?.yVal ?: 0
        dataPoints.clear()
        dataPoints.addAll(lista)
        invalidate()
    }
}


