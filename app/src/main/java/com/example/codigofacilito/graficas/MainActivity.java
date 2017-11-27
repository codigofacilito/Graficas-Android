package com.example.codigofacilito.graficas;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Arrays;



public class MainActivity extends AppCompatActivity {
    private PieChart pieChart;
    private BarChart barChart;
    private LineChart lineChart;
    private BubbleChart bubbleChart;
    private RadarChart radarChart;
    private ScatterChart scatterChart;
    private CandleStickChart candleStickChart;

    //Eje X
    private String[]months=new String[]{"Enero","Febrero","Marzo","Abril","Mayo"};
    //Eje Y
    private int[]sale=new int[]{25,20,38,10,15};


    //Grafica de burbuja(Bubble) 3 dato (Este define el tamaño de la burbuja)
    private int[]purchase=new int[]{50,400,100,200,500};

    //Grafica de Radar(Radar)
    //Los criterios que se evaluaran el la grafica
    private String[]variable=new String[]{"Speed","Durability","Comfort","Power","Space"};
    //Valor para los criterios en un auto Chevrolet
    private int[]valueChevrolet=new int[]{5,6,10,4,9};
    //Valor para los criterios en un auto Jeep
    private int[]valueJeep=new int[]{10,5,3,4,5};


    //Grafica de Vela(Candlestick)
    //Es estos arreglos el:
    //Primero es altista (abrio en 4 y cerro en 6)
    //Segunfo es bajista (abrio en 7 y cerro en 4)
    //Tercero es altista (abrio en 6 y cerro en 8)
    //Cuarto es bajista (abrio en 6 y cerro en 3)
    //Quinto es altista (abrio en 2 y cerro en 5)

    //Maximo
    private int[]shadowHigh=new int[]{7, 10, 9,  8,  6};
    //Minimo
    private int[]shadowLow=new int[] {3, 5,  4,  2,  0};
    //Abrir
    private int[]open=new int[]      {4, 7,  6,  6,  2};
    //Cerra
    private int[]close=new int[]     {6, 4,  8,  3,  5};


    //Colors
    private int[]colors=new int[]{Color.BLACK,Color.RED,Color.GREEN,Color.BLUE,Color.LTGRAY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barChart=(BarChart)findViewById(R.id.barChart);
        pieChart=(PieChart) findViewById(R.id.pieChart);
        lineChart = (LineChart) findViewById(R.id.lineChart);
        bubbleChart = (BubbleChart) findViewById(R.id.bubbleChart);
        radarChart = (RadarChart) findViewById(R.id.radarChart);
        scatterChart = (ScatterChart) findViewById(R.id.scatterChart);
        candleStickChart = (CandleStickChart) findViewById(R.id.candleStickChart);
        createCharts();

    }
    //Carasteristicas comunes en las graficas
    private Chart getSameChart(Chart chart,String description,int textColor,int background,int animateY,boolean leyenda){
        chart.getDescription().setText(description);
        chart.getDescription().setTextColor(textColor);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);

        //Validar porque la grafica de radar y dispersion tiene dos datos especificos y la leyenda se crea de acuerdo a esos datos.
        if(leyenda)
        legend(chart);
        return chart;
    }

    private void legend(Chart chart) {
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries = new ArrayList<>();
        for (int i = 0; i < months.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colors[i];
            entry.label = months[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
    private ArrayList<BarEntry>getBarEntries(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < sale.length; i++)
            entries.add(new BarEntry(i,sale[i]));
        return entries;
    }
    private ArrayList<PieEntry>getPieEntries(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < sale.length; i++)
            entries.add(new PieEntry(sale[i]));
        return entries;
    }

    private ArrayList<Entry> getLineEntries() {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < sale.length; i++)
            entries.add(new Entry(i, sale[i]));
        return entries;
    }

    private ArrayList<BubbleEntry>getBubbleEntries(){
        //Grafica de burbuja necesita 3 valores
        ArrayList<BubbleEntry> entries = new ArrayList<>();
        for (int i = 0; i < sale.length; i++)
            entries.add(new BubbleEntry(i,sale[i],purchase[i]));
        return entries;
    }

    private ArrayList<RadarEntry> getRadarEntriesChevrolet() {
        ArrayList<RadarEntry> entries = new ArrayList<>();
        for (int i = 0; i < valueChevrolet.length; i++)
            entries.add(new RadarEntry(valueChevrolet[i]));
        return entries;
    }
    private ArrayList<RadarEntry> getRadarEntriesJeep() {
        ArrayList<RadarEntry> entries = new ArrayList<>();
        for (int i = 0; i < valueJeep.length; i++)
            entries.add(new RadarEntry(valueJeep[i]));
        return entries;
    }
    private ArrayList<String> getVariable() {
        ArrayList<String> entries = new ArrayList<>();
        entries.addAll(Arrays.asList(variable));
        return entries;
    }
    private ArrayList<Entry> getScatterEntries() {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < sale.length; i++)
            entries.add(new Entry(i, sale[i]));
        return entries;
    }

    private ArrayList<CandleEntry> getCandleEntries() {
        ArrayList<CandleEntry> entries = new ArrayList<>();
        for (int i = 0; i < sale.length; i++)
            entries.add(new CandleEntry(i,shadowHigh[i],shadowLow[i],open[i],close[i]));
        return entries;
    }

    //Eje horizontal o eje X
    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(months));
    }
    //Eje Vertical o eje Y lado izquierdo
    private void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
        axis.setGranularity(20);
    }
    //Eje Vertical o eje Y lado Derecho
    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }

    //Crear graficas
    public void createCharts(){
        //BarChart
        barChart=(BarChart)getSameChart(barChart,"Series",Color.RED,Color.CYAN,3000,true);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(true);
        barChart.setData(getBarData());
        barChart.invalidate();
        barChart.getLegend().setEnabled(false);
        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());

        //PieChart
        pieChart=(PieChart)getSameChart(pieChart,"Ventas",Color.GRAY,Color.MAGENTA,3000,true);
        pieChart.setHoleRadius(10);
        pieChart.setTransparentCircleRadius(12);
        pieChart.setData(getPieData());
        pieChart.invalidate();
        pieChart.setDrawHoleEnabled(false);

        //LineChart
        lineChart = (LineChart) getSameChart(lineChart, "Ventas", Color.BLUE, Color.YELLOW, 3000,true);
        lineChart.setData(getLineData());
        lineChart.invalidate();
        axisX(lineChart.getXAxis());
        axisLeft(lineChart.getAxisLeft());
        axisRight(lineChart.getAxisRight());

        //BubbleChart
        bubbleChart = (BubbleChart) getSameChart(bubbleChart, "Compras", Color.GRAY, Color.YELLOW, 3000,true);
        bubbleChart.setData(getBubbleData());
        bubbleChart.invalidate();
        axisX(bubbleChart.getXAxis());
        axisLeft(bubbleChart.getAxisLeft());
        axisRight(bubbleChart.getAxisRight());

        //Para que el eje X inicie de -0.5 y termine en 5 con esto las burbujas no se veran cortada(Borrar y ejecutar para ver la diferencia)
        bubbleChart.getXAxis().setAxisMinimum(-1f);
        bubbleChart.getXAxis().setAxisMaximum(months.length);


        //ScatterChart
        scatterChart = (ScatterChart) getSameChart(scatterChart, "Temperaturas en Mèxico", Color.BLACK, Color.LTGRAY, 3000,false);
        scatterChart.setData(getScatterData());
        axisX(scatterChart.getXAxis());
        axisLeft(scatterChart.getAxisLeft());
        axisRight(scatterChart.getAxisRight());

        //Para que el eje X inicie de -0.5 y termine en 5 con esto los puntos no se veran cortada(Borrar y ejecutar para ver la diferencia)
        scatterChart.getXAxis().setAxisMinimum(-1f);
        scatterChart.getXAxis().setAxisMaximum(months.length);



        //CandleStickChart
        candleStickChart = (CandleStickChart) getSameChart(candleStickChart, "Oferta-Demanda Oro", Color.BLUE, Color.LTGRAY, 3000,true);
        candleStickChart.setData(getCandleData());

        //Desactivar la leyenda porque no se maneja
        candleStickChart.getLegend().setEnabled(false);
        axisX(candleStickChart.getXAxis());
        axisRight(candleStickChart.getAxisRight());
        candleStickChart.invalidate();



        //RadarChart

        //En radar se valido la leyenda porque no podemos perzonalizarlo la leyenda se crea de acuerdo a los datos que se tienen dentro de la grafica.
        radarChart = (RadarChart) getSameChart(radarChart, "Mejor carro", Color.YELLOW, Color.LTGRAY, 3000,false);
        radarChart.setData(getRadarData());
        radarChart.invalidate();
        axisX(radarChart.getXAxis());

    }

    //Carasteristicas comunes en dataset
    private DataSet getDataSame(DataSet dataSet){
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private BarData getBarData(){
        BarDataSet barDataSet=(BarDataSet)getDataSame(new BarDataSet(getBarEntries(),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }
    private PieData getPieData(){
        PieDataSet pieDataSet=(PieDataSet)getDataSame(new PieDataSet(getPieEntries(),""));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueFormatter(new PercentFormatter());
        return new PieData(pieDataSet);
    }
    private LineData getLineData() {
        LineDataSet lineDataSet = (LineDataSet) getDataSame(new LineDataSet(getLineEntries(), ""));
        lineDataSet.setLineWidth(2.5f);
        //Color de los circulos de la grafica
        lineDataSet.setCircleColors(colors);
        //Tamaño de los circulos de la grafica
        lineDataSet.setCircleRadius(5f);
        //Sombra grafica
        lineDataSet.setDrawFilled(true);
        //Estilo de la linea picos(linear) o curveada(cubic) cuadrada(Stepped)
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        return new LineData(lineDataSet);
    }

    private BubbleData getBubbleData() {
        BubbleDataSet bubbleDataSet = (BubbleDataSet) getDataSame(new BubbleDataSet(getBubbleEntries(), ""));
        BubbleData bubbleData=new BubbleData(bubbleDataSet);
        return bubbleData;
    }

    private RadarData getRadarData() {

        RadarDataSet chevrolet = (RadarDataSet) getDataSame(new RadarDataSet(getRadarEntriesChevrolet(), "Chevrolet"));
        //Definimos un color especial para chevrolet para no cargar el arreglo de colores
        chevrolet.setColor(Color.MAGENTA);

        //Definimos un color especial para jeep para no cargar el arreglo de colores
        RadarDataSet jeep = (RadarDataSet) getDataSame(new RadarDataSet(getRadarEntriesJeep(), "Jeep"));
        jeep.setColor(Color.RED);

        ArrayList<IRadarDataSet> dataSets = new ArrayList<IRadarDataSet>();
        dataSets.add(chevrolet);
        dataSets.add(jeep);

        RadarData data = new RadarData(dataSets);
        data.setLabels(getVariable());
        return data;
    }
    private ScatterData getScatterData() {
        ScatterDataSet scatterDataSet = (ScatterDataSet) getDataSame(new ScatterDataSet(getScatterEntries(), "Temperatura"));
        scatterDataSet.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet.setColor(Color.MAGENTA);
        return new ScatterData(scatterDataSet);
    }

    private CandleData getCandleData() {
        CandleDataSet candleDataSet = (CandleDataSet) getDataSame(new CandleDataSet(getCandleEntries(), ""));
        candleDataSet.setIncreasingColor(Color.GREEN);
        candleDataSet.setDecreasingColor(Color.RED);
        candleDataSet.setShadowColorSameAsCandle(true);
        return new CandleData(candleDataSet);
    }
}
