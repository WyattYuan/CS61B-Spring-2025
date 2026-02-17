import deque.ArrayDeque61B;

import deque.Deque61B;
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
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);

        assertThat(ad.toList()).containsExactly(1, 2, 3).inOrder();
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

}
