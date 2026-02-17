import deque.ArrayDeque61B;

import deque.Deque61B;
import deque.LinkedListDeque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    void addFirstTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);

        assertThat(ad.toList()).containsExactly(3, 2, 1).inOrder();
    }

    @Test
    void addLastTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        for (int i = 0; i < 20; i++) {
            ad.addLast(i);
        }

        assertThat(ad.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19).inOrder();
    }

    @Test
    void addTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(3);
        ad.addFirst(2);
        ad.addFirst(1);
        ad.addLast(4);
        ad.addLast(5);
        ad.addLast(6);

        assertThat(ad.toList()).containsExactly(1, 2, 3, 4, 5, 6).inOrder();
    }

    @Test
    void resizeTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        ad.resize(16);
        ad.resize(8);

        ad.addFirst(3);
        ad.addFirst(2);
        ad.addFirst(1);
        ad.addLast(4);
        ad.addLast(5);
        ad.addLast(6);

        ad.resize(16);
        assertThat(ad.toList()).containsExactly(1, 2, 3, 4, 5, 6).inOrder();

        ad.resize(8);
        assertThat(ad.toList()).containsExactly(1, 2, 3, 4, 5, 6).inOrder();
    }

    @Test
    void addAndResizeTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        for (int i = 0; i < 9; i++) {
            ad.addLast(i + 1);
        }
        assertThat(ad.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9).inOrder();

        for (int i = 0; i < 8; i++) {
            ad.addFirst(17 - i);
        }
        assertThat(ad.toList()).containsExactly(10, 11, 12, 13, 14, 15, 16, 17, 1, 2, 3, 4, 5, 6, 7, 8, 9).inOrder();
    }

    @Test
    void getTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        assertThat(ad.isEmpty()).isTrue();
        assertThat(ad.get(1)).isEqualTo(null);

        ad.addFirst(3);
        ad.addFirst(2);
        ad.addFirst(1);
        ad.addLast(4);
        ad.addLast(5);
        ad.addLast(6);
        ad.addLast(7);
        ad.addLast(8);

        assertThat(ad.isEmpty()).isFalse();

        for (int i = 0; i < ad.size(); i++) {
            assertThat(ad.get(i)).isEqualTo(i + 1);
        }
        assertThat(ad.get(ad.size())).isEqualTo(null);
        assertThat(ad.get(ad.size() + 1)).isEqualTo(null);
        assertThat(ad.get(100)).isEqualTo(null);
        assertThat(ad.get(-1)).isEqualTo(null);

    }

    @Test
    void ofTest() {
        ArrayDeque61B<Integer> ad = ArrayDeque61B.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertThat(ad.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9).inOrder();
    }

    @Test
    void removeFirstTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();

        ad.addFirst(3);
        ad.addFirst(2);
        ad.addFirst(1);
        ad.addLast(4);
        ad.addLast(5);

        ad.removeFirst();
        assertThat(ad.toList()).containsExactly(2, 3, 4, 5).inOrder();
        ad.removeFirst();
        assertThat(ad.toList()).containsExactly(3, 4, 5).inOrder();
        ad.removeFirst();
        assertThat(ad.toList()).containsExactly(4, 5).inOrder();
        ad.removeFirst();
        assertThat(ad.toList()).containsExactly(5).inOrder();
        ad.removeFirst();
        assertThat(ad.toList()).containsExactly().inOrder();
    }

    @Test
    void removeLastTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();

        ad.addFirst(3);
        ad.addFirst(2);
        ad.addFirst(1);
        ad.addLast(4);
        ad.addLast(5);

        ad.removeLast();
        assertThat(ad.toList()).containsExactly(1, 2, 3, 4).inOrder();
        ad.removeLast();
        assertThat(ad.toList()).containsExactly(1, 2, 3).inOrder();
        ad.removeLast();
        assertThat(ad.toList()).containsExactly(1, 2).inOrder();
        ad.removeLast();
        assertThat(ad.toList()).containsExactly(1).inOrder();
        ad.removeLast();
        assertThat(ad.toList()).containsExactly().inOrder();

        ArrayDeque61B<Integer> ad2 = new ArrayDeque61B<>();
        assertThat(ad2.removeFirst()).isEqualTo(null);
        assertThat(ad2.removeLast()).isEqualTo(null);
    }

    @Test
    void resizeTest2() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();

        ad.addFirst(3);
        ad.addFirst(2);
        ad.addFirst(1);
        ad.removeLast();

        ad.resize(16);
        assertThat(ad.toList()).containsExactly(1, 2).inOrder();
    }

    @Test
    void resizeTest3() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();

        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.removeFirst();
        ad.removeFirst();

        ad.resize(16);
        assertThat(ad.toList()).containsExactly(3).inOrder();
    }

    @Test
    void resizeTest4() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();

        for (int i = 0; i < 10000; i++) {
            ad.addLast(i);
        }
        assertThat(ad.getCapacity()).isGreaterThan(9999);
        for (int i = 0; i < 10000; i++) {
            ad.removeLast();
            assertThat(ad.shouldResizeDown()).isFalse();
        }
    }

    @Test
    void complexWrappingTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();

        // Add first to the front side
        ad.addFirst(3);
        ad.addFirst(2);
        ad.addFirst(1);

        // Remove one from front
        ad.removeFirst();

        // Add to back
        ad.addLast(4);
        ad.addLast(5);

        // Remove from back
        ad.removeLast();

        // Verify state
        assertThat(ad.toList()).containsExactly(2, 3, 4).inOrder();
        assertThat(ad.size()).isEqualTo(3);
    }

    @Test
    void mixedTypeTest() {
        ArrayDeque61B<Object> ad = new ArrayDeque61B<>();
        ad.addLast("string");
        ad.addLast(42);
        ad.addLast(3.14);
        ad.addLast(true);

        assertThat(ad.size()).isEqualTo(4);
        assertThat(ad.get(0)).isEqualTo("string");
        assertThat(ad.get(1)).isEqualTo(42);
        assertThat(ad.get(2)).isEqualTo(3.14);
        assertThat(ad.get(3)).isEqualTo(true);
    }

    @Test
    void largeOperationsTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        int n = 1000;

        // Add many elements
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                ad.addLast(i);
            } else {
                ad.addFirst(-i);
            }
        }

        assertThat(ad.size()).isEqualTo(n);

        // Verify we can get elements
        assertThat(ad.get(0)).isNotNull();
        assertThat(ad.get(n / 2)).isNotNull();
        assertThat(ad.get(n - 1)).isNotNull();
    }

    @Test
    void toListConsistency() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addFirst(0);
        ad.addLast(3);

        List<Integer> list1 = ad.toList();
        List<Integer> list2 = ad.toList();

        assertThat(list1).isEqualTo(list2);
        assertThat(ad.toList()).containsExactly(0, 1, 2, 3).inOrder();
    }

    @Test
    void shouldResizeDownConditions() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();

        // Initially capacity is too small to resize down
        ad.addLast(1);
        ad.removeFirst();
        assertThat(ad.shouldResizeDown()).isFalse();

        // Add many elements to trigger resize up
        for (int i = 0; i < 20; i++) {
            ad.addLast(i);
        }

        // Now capacity should be >= 16
        assertThat(ad.getCapacity()).isGreaterThan(15);

        // Remove to trigger resize down condition
        while (!ad.isEmpty() && !ad.shouldResizeDown()) {
            ad.removeFirst();
        }
    }


    @Test
    void iteratorTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        for (Integer x : ad) {
            System.out.println("Not supposed to see this line");
        }
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        int i = 1;
        for (Integer x : ad) {
            assertThat(x).isEqualTo(i);
            i += 1;
        }
    }

    @Test
    void isEqualTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);

        Deque61B<Integer> ad2 = new ArrayDeque61B<>();
        ad2.addLast(1);
        ad2.addLast(2);
        ad2.addLast(3);

        assertThat(ad).isEqualTo(ad2);

        ad2.addLast(4);
        assertThat(ad).isNotEqualTo(ad2);

        Deque61B<String> ad3 = new ArrayDeque61B<>()  ;
        ad3.addLast("front");
        ad3.addLast("mid");
        ad3.addLast("end");
        assertThat(ad).isNotEqualTo(ad3);
    }

    @Test
    void toStringTest(){
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        ad1.addLast("front");
        ad1.addLast("middle");
        ad1.addLast("back");

        System.out.println(ad1);

    }

}
