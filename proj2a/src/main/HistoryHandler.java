package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;


public class HistoryHandler extends NgordnetQueryHandler {
    private final NGramMap map;

    public HistoryHandler(NGramMap map) {
        super();
        this.map = map;
    }


    @Override
    public String handle(NgordnetQuery q) {

        ArrayList<TimeSeries> lts = new ArrayList<>();
        for (String w : q.words()) {
            lts.add(map.weightHistory(w, q.startYear(), q.endYear()));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(q.words(), lts);

        return Plotter.encodeChartAsString(chart);
    }
}
