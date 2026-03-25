package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap ngm;

    public HistoryTextHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    // 每当用户点击 History (Text) 按钮时，都会调用此方法。
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder response = new StringBuilder();
        for (String word : words) {
            response.append(word).append(": ").append(ngm.weightHistory(word, startYear, endYear)).append("\n");
        }
        return response.toString();
    }
}
