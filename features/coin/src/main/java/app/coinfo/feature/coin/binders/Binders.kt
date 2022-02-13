package app.coinfo.feature.coin.binders

import android.graphics.Color
import androidx.databinding.BindingAdapter
import app.coinfo.library.cloud.model.PriceDatePair
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

private const val DASHED_LINE_LENGTH = 10f
private const val DASHED_LINE_SPACE_LENGTH = 5f
private const val DASHED_LINE_PHASE = 0f
private const val LINE_THICKNESS_WIDTH = 1f
private const val LINE_POINT_RADIUS = 3f

@BindingAdapter("historicalMarketData")
internal fun bindHistoricalMarketData(chart: LineChart, data: List<PriceDatePair>) {

    val values = ArrayList<Entry>()

    data.forEach {
        values.add(Entry(it.date.toFloat(), it.price.toFloat()))
    }

    val set1 = LineDataSet(values, "DataSet 1")

    set1.setDrawIcons(false)

    // draw dashed line

    // draw dashed line
    set1.enableDashedLine(DASHED_LINE_LENGTH, DASHED_LINE_SPACE_LENGTH, DASHED_LINE_PHASE)

    // black lines and points

    // black lines and points
    set1.color = Color.BLACK
    set1.setDrawCircles(false)
    set1.setCircleColor(Color.BLACK)

    // line thickness and point size
    set1.lineWidth = LINE_THICKNESS_WIDTH
    set1.circleRadius = LINE_POINT_RADIUS

    // draw points as solid circles

    // draw points as solid circles
    set1.setDrawCircleHole(false)

    // customize legend entry

    // customize legend entry
//    set1.formLineWidth = 1f
//    set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
//    set1.formSize = 15f

    // text size of values

    // text size of values
//    set1.valueTextSize = 9f

    // draw selection line as dashed

    // draw selection line as dashed
//    set1.enableDashedHighlightLine(10f, 5f, 0f)

    // set the filled area

    // set the filled area
    set1.setDrawFilled(true)
    set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }

    // set color of filled area

//    // set color of filled area
//    if (Utils.getSDKInt() >= 18) {
//        // drawables only supported on api level 18 and above
//        val drawable = ContextCompat.getDrawable(chart.context, R.drawable.fade_red)
//        set1.fillDrawable = drawable
//    } else {
//        set1.fillColor = Color.BLACK
//    }

    val dataSets = ArrayList<ILineDataSet>()
    dataSets.add(set1) // add the data sets

    chart.data = LineData(dataSets)
}
