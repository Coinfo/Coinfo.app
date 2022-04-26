/*
 * Copyright (C) 2022 Coinfo App Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.coinfo.feature.coin.binders

import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.coinfo.feature.coin.details.R
import app.coinfo.repository.coins.model.DeveloperInfo
import app.coinfo.repository.coins.model.PriceDatePair
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin

private const val DASHED_LINE_LENGTH = 10f
private const val DASHED_LINE_SPACE_LENGTH = 5f
private const val DASHED_LINE_PHASE = 0f
private const val LINE_THICKNESS_WIDTH = 1f
private const val LINE_POINT_RADIUS = 3f

@BindingAdapter("developerInfo")
internal fun bindDeveloperIndo(chipGroup: ChipGroup, info: DeveloperInfo?) {
    info?.let {
        val context = chipGroup.context
        it.forks?.let { forks ->
            chipGroup.addView(
                Chip(context).apply {
                    text = context.getString(R.string.coin_dev_info_forks, forks)
                    chipIcon = AppCompatResources.getDrawable(context, R.drawable.coin_ic_fork)
                }
            )
        }
        it.stars?.let { stars ->
            chipGroup.addView(
                Chip(context).apply {
                    text = context.getString(R.string.coin_dev_info_stars, stars)
                    chipIcon = AppCompatResources.getDrawable(context, R.drawable.coin_ic_star)
                }
            )
        }
        it.subscribers?.let { subscribers ->
            chipGroup.addView(
                Chip(context).apply {
                    text = context.getString(R.string.coin_dev_info_subscribers, subscribers)
                }
            )
        }
        it.issues?.let { open ->
            chipGroup.addView(
                Chip(context).apply {
                    text = context.getString(R.string.coin_dev_info_issues_open, open)
                }
            )
        }
        it.closedIssues?.let { close ->
            chipGroup.addView(
                Chip(context).apply {
                    text = context.getString(R.string.coin_dev_info_issues_closed, close)
                }
            )
        }
        it.pullRequestsMerged?.let { merged ->
            chipGroup.addView(
                Chip(context).apply {
                    text = context.getString(R.string.coin_dev_info_pull_requests_merged, merged)
                }
            )
        }
    }
}

@BindingAdapter("markdown")
internal fun bindMarkdownText(textView: TextView, text: String) {
    Markwon.builder(textView.context)
        .usePlugin(HtmlPlugin.create())
        .build()
        .setMarkdown(textView, text)
}

@BindingAdapter("isRefreshing")
internal fun bindIsRefreshing(swipeRefreshLayout: SwipeRefreshLayout, isRefreshing: Boolean) {
    swipeRefreshLayout.isRefreshing = isRefreshing
}

@BindingAdapter("historicalMarketData")
internal fun bindHistoricalMarketData(chart: LineChart, data: List<PriceDatePair>) {

    val values = ArrayList<Entry>()

    data.forEach {
        values.add(Entry(it.date.toFloat(), it.price.toFloat()))
    }

    val set1 = LineDataSet(values, "Coin Price")

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
    chart.notifyDataSetChanged()
    chart.invalidate()
}
