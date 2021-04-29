package pl.krzysztofbaka.projekt

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.absoluteValue


/*class ChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        var anyChartView : AnyChartView
        anyChartView = findViewById(R.id.any_chart_view)
        var av : AnyChart
        var pie : Cartesian


        pie=AnyChart.line().background(true)

        var lista : ArrayList<DataEntry>
        lista = java.util.ArrayList()
        lista.add(ValueDataEntry("d",1))
        lista.add(ValueDataEntry("fd",4))
        lista.add(ValueDataEntry("as",-9))


        pie.data(lista)
        anyChartView.setChart(pie)

    }
}*/


class ChartActivity(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private val dataSet = mutableListOf<DataPoint>()
    private var xMin = 0
    private var xMax = 0
    private var yMin = 0
    private var yMax = 0
    private var roznica = 0

    private val dataPointPaint = Paint().apply {
        color = Color.RED
        strokeWidth = 7f
        style = Paint.Style.STROKE
    }

    private val dataPointFillPaint = Paint().apply {
        color = Color.WHITE
    }

    private val dataPointLinePaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 7f
        isAntiAlias = true
    }

    private val axisLinePaint = Paint().apply {
        color = Color.RED
        strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        dataSet.forEachIndexed { index, currentDataPoint ->
            val realX = currentDataPoint.xVal.toRealX()
            var realY = currentDataPoint.yVal.toRealY()

            var status = false
            if(realY<0){
                realY = realY.absoluteValue
                status =true
            }
            if (index < dataSet.size - 1) {
                val nextDataPoint = dataSet[index + 1]
                val startX = currentDataPoint.xVal.toRealX()
                var startY = currentDataPoint.yVal.toRealY()
                if(startY<0){
                    startY = startY.absoluteValue
                }
                val endX = nextDataPoint.xVal.toRealX()
                var endY = 0f
                if(status) {
                    endY = nextDataPoint.yVal.toRealY().absoluteValue
                    status = false
                }else{
                    endY = nextDataPoint.yVal.toRealY()
                }
                canvas.drawLine(startX, startY, endX, endY, dataPointLinePaint)
            }

            canvas.drawCircle(realX, realY, 7f, dataPointFillPaint)
            canvas.drawCircle(realX, realY, 7f, dataPointPaint)
        }

        canvas.drawLine(0f, 0f, 0f, height.toFloat(), axisLinePaint)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), axisLinePaint)
    }

    fun setData(newDataSet: LinkedList<DataPoint>, min:Int, max:Int) {
        xMin = newDataSet.minByOrNull { it.xVal }?.xVal ?: 0
        xMax = newDataSet.maxByOrNull { it.xVal }?.xVal ?: 0
        //yMin = newDataSet.minByOrNull { it.yVal }?.yVal ?: 0
        //yMax = newDataSet.maxByOrNull { it.yVal }?.yVal ?: 0


        yMin = min
        yMax = max

        roznica = (max.absoluteValue + min.absoluteValue) / 2
/*
        xMin = 0
        xMax = 5
        yMin = -50
        yMax = 50
*/


        dataSet.clear()
        dataSet.addAll(newDataSet)
        invalidate()
    }

    private fun Int.toRealX() = toFloat() / xMax * width
    private fun Int.toRealY() = toFloat() / yMax * height

    private fun Int.toRealYminus() = -(toFloat() / yMax * height)

}


