import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        int sum = 0;
        for (int num : L){
            sum += num;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> evenInL = new ArrayList<>();
        for (int num : L){
            if (num % 2 == 0){
                evenInL.add(num);
            }
        }
        return evenInL;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> mutableL1 = new ArrayList<>(L1);
        List<Integer> mutableL2 = new ArrayList<>(L2);
        mutableL1.sort(Comparator.naturalOrder());
        mutableL2.sort(Comparator.naturalOrder());
        return commonHelper(0, 0, mutableL1, mutableL2, new ArrayList<>());
    }

    public static List<Integer> commonHelper(int idx1, int idx2, List<Integer> L1, List<Integer> L2, List<Integer> commonList) {
//        My Answer
//        if (idx1 >= L1.size() || idx2 >= L2.size()) {
//            return commonList;
//        }
//        if (Objects.equals(L1.get(idx1), L2.get(idx2))) {
//            commonList.add(L1.get(idx1));
//            return commonHelper(idx1 + 1, idx2 + 1, L1, L2, commonList);
//        } else if (L1.get(idx1) < L2.get(idx2)) {
//            return commonHelper(idx1 + 1, idx2, L1, L2, commonList);
//        } else {
//            return commonHelper(idx1, idx2 + 1, L1, L2, commonList);
//        }
        // 只要有一个列表走完了，就结束
        if (idx1 >= L1.size() || idx2 >= L2.size()) {
            return commonList;
        }

        int val1 = L1.get(idx1);
        int val2 = L2.get(idx2);

        if (Objects.equals(val1, val2)) {
            commonList.add(val1);
            // 相等时，两个指针都向后移动
            return commonHelper(idx1 + 1, idx2 + 1, L1, L2, commonList);
        } else if (val1 < val2) {
            // 较小的那个指针向后移，寻找变大的可能
            return commonHelper(idx1 + 1, idx2, L1, L2, commonList);
        } else {
            return commonHelper(idx1, idx2 + 1, L1, L2, commonList);
        }
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int count = 0;
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == c) {
                    count++;
                }
            }
        }
        return count;
    }
}
