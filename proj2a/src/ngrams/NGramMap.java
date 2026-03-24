package ngrams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    HashMap<String, TimeSeries> wordTimeSeries;  // 单词及其TS对应
    TimeSeries totalCounts;  // 所有单词的总计TS

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     * WordsFile是每行第一项是单词。
     * 第二项是年份。
     * 第三项是该单词在该年出现在任何书籍中的次数。
     * 第四项是包含该单词的不同来源的数量。你的程序应该忽略第四列。
     * <p>
     * COUNTSFILE
     * 每行第一项是年份。
     * 第二项是该年度所有文本记录的总字数。
     * 第三项是该年度文本的总页数。
     * 第四项是该年度不同来源的总数。你的程序应该忽略第三列和第四列。
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // 需要一个数据结构，一个word对应一个TimeSeries
        // 感觉是 HashMap
        wordTimeSeries = new HashMap<>();
        totalCounts = new TimeSeries();

        try (BufferedReader br = new BufferedReader(new FileReader(wordsFilename))) {
            String line;
            String lastWord = null;
            TimeSeries lastTimeSeries = new TimeSeries();
            while ((line = br.readLine()) != null) {
                // 指定分隔符为\t
                StringTokenizer st = new StringTokenizer(line, "\t");
                String currentWord = st.nextToken();
                if (!currentWord.equals(lastWord) && lastWord != null) {
                    // 新单词
                    // 把上个单词放进去
                    wordTimeSeries.put(lastWord, new TimeSeries(lastTimeSeries));
                    lastTimeSeries.clear();
                }
                Integer year = Integer.parseInt(st.nextToken());
                Double wordFreq = Double.parseDouble(st.nextToken());
                lastTimeSeries.put(year, wordFreq);
                lastWord = currentWord;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader br2 = new BufferedReader(new FileReader(countsFilename))) {
            String line;
            while ((line = br2.readLine()) != null) {
                // 指定分隔符为\t
                StringTokenizer st = new StringTokenizer(line, ",");
                Integer year = Integer.parseInt(st.nextToken());
                Double count = Double.parseDouble(st.nextToken());
                totalCounts.put(year, count);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return new TimeSeries(wordTimeSeries.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return new TimeSeries(wordTimeSeries.get(word));
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     * 每年所有单词加起来
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(totalCounts);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     * 单词TS / 总TS
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordTS = countHistory(word, startYear, endYear);
        if (wordTS == null) {
            return new TimeSeries();
        }
        TimeSeries totalTS = new TimeSeries(totalCountHistory(), startYear, endYear);
        return wordTS.dividedBy(totalTS);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries wordTS = countHistory(word);
        if (wordTS == null) {
            return new TimeSeries();
        }
        TimeSeries totalTS = totalCountHistory();
        return wordTS.dividedBy(totalTS);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     * 返回一组词的占比加和
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries returnSummedTS = new TimeSeries();
        for (String w : words) {
            // 踩坑：记得更新returnSummedTS的值
            returnSummedTS = returnSummedTS.plus(weightHistory(w, startYear, endYear));
        }
        return returnSummedTS;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries returnSummedTS = new TimeSeries();
        for (String w : words) {
            // 踩坑：记得更新returnSummedTS的值
            returnSummedTS = returnSummedTS.plus(weightHistory(w));
        }
        return returnSummedTS;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
