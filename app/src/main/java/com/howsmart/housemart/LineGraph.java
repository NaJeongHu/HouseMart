package com.howsmart.housemart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class LineGraph implements OnChartValueSelectedListener {

    private Context context;
    private LineChart chart;

    public LineGraph(Context context, LineChart chart) {
        this.context = context;
        this.chart = chart;
    }

    public void createChart() {
        { //chart
            chart.setBackgroundColor(Color.WHITE);
            chart.setTouchEnabled(true);
            chart.setOnChartValueSelectedListener(this);
            chart.setDrawGridBackground(false);
            chart.setDragEnabled(true);
            //chart.setScaleEnabled(true);
            chart.setPinchZoom(false);
            chart.getDescription().setEnabled(false);
            chart.getLegend().setEnabled(false);

            chart.animateX(600);
            chart.animateY(600);

            MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view);
            mv.setChartView(chart);
            chart.setMarker(mv);
        }

        { //xAxis
            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setAxisMinimum(0.8f);
            xAxis.setAxisMaximum(7f);
            xAxis.setLabelCount(7);

            xAxis.setAxisLineColor(Color.argb(150, 0, 0, 0));
            xAxis.setAxisLineWidth(0.7f);
            xAxis.setTextColor(Color.argb(150, 0, 0, 0));
            xAxis.setTextSize(11f);
            // vertical grid lines
//            xAxis.enableGridDashedLine(10f, 10f, 0);
//            xAxis.disableGridDashedLine();
//            xAxis.disableAxisLineDashedLine();
        }

        { //yAxis
            chart.getAxisRight().setEnabled(false);

            YAxis yAxis = chart.getAxisLeft();
            yAxis.setDrawGridLines(false);
            yAxis.setAxisMaximum(20000f);
            yAxis.setAxisMinimum(0f);

            yAxis.setAxisLineColor(Color.argb(150, 0, 0, 0));
            yAxis.setAxisLineWidth(0.7f);
            yAxis.setTextColor(Color.argb(150, 0, 0, 0));
            yAxis.setTextSize(11f);
//            yAxis.setDrawAxisLine(false);
//            yAxis.disableGridDashedLine();
            // horizontal grid lines
//            yAxis.enableGridDashedLine(10f, 10f, 0f);
        }
    }

    public void setData(ArrayList<Entry> values) {
//        ArrayList<Entry> values = new ArrayList<>();
//        for (int i = 1; i <= count; i++) {
//            float val = (float) (Math.random() * range) + 100f;
//            Entry entry = new Entry(i, val /*, getResources().getDrawable(R.drawable.star)*/);
//            values.add(entry);
//        }

        LineDataSet set1;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "월별 매매 실거래가 평균");
            set1.setDrawIcons(false);
            set1.setValueTextSize(13f);

            set1.setDrawCircles(true);
            set1.setDrawCircleHole(true);
            set1.setLineWidth(2f);
            set1.setCircleRadius(4.5f);
            set1.setCircleHoleRadius(2f);
            set1.setColor(Color.argb(240, 34, 101, 218));
            set1.setCircleColor(Color.argb(230, 255, 255, 255));
            set1.setCircleHoleColor(Color.argb(240, 34, 101, 218));

            set1.disableDashedHighlightLine();

            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_sky);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(R.color.white);
            }

//            set1.setFormLineWidth(1f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set1.setFormSize(15.f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            chart.setData(data);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
