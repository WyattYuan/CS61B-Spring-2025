import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testConstructor(){
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries x = new TimeSeries(catPopulation,1991,1992);
        assertThat(x.years()).isEqualTo(Arrays.asList(1991,1992));

        TimeSeries y = new TimeSeries(catPopulation);
        assertThat(y != catPopulation).isTrue();
    }

    @Test
    public void testYears(){
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1992, 100.0);
        catPopulation.put(1991, 0.0);
        catPopulation.put(1994, 200.0);
        catPopulation.put(2000, 200.0);

        List<Integer> expected = new ArrayList<>();
        expected.addLast(1991);
        expected.addLast(1992);
        expected.addLast(1994);
        expected.addLast(2000);
        assertThat(catPopulation.years()).isEqualTo(expected);

        System.out.println(catPopulation.values());

        TimeSeries catPopulationCopy = new TimeSeries(catPopulation);
        assertThat(catPopulationCopy.years()).isEqualTo(expected);
    }

    @Test
    public void testDivision() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 300.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 1.0);
        dogPopulation.put(1992, 200.0);
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries fishPopulation = new TimeSeries();
        fishPopulation.put(1991, 1.0);
        fishPopulation.put(1992, 200.0);
        fishPopulation.put(1995, 400.0);

        TimeSeries totalPopulation = catPopulation.dividedBy(dogPopulation);
        // expected: 1991: 0,
        //           1992: 0.5
        //           1994: 0.75


        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(0.5);
        expectedTotal.add(0.75);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }

        try {
            totalPopulation.dividedBy(fishPopulation);
        } catch(Exception e) {
            // 测试是否抛出错误的类型
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testPlus(){
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 300.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1992, 200.0);
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0.0,
        //           1992: 300
        //           1994: 700
        //           1995: 500


        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(300.0);
        expectedTotal.add(700.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }

        TimeSeries empty = new TimeSeries();
        TimeSeries totalPopulation2 = empty.plus(totalPopulation);
        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation2.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }
} 