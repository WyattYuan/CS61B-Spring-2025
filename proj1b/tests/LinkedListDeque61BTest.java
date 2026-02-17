import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class LinkedListDeque61BTest {

    @Test
    void iteratorTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        for (Integer x : lld) {
            System.out.println("Not supposed to see this line.");
        }
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        int i = 1;
        for (Integer x : lld) {
            assertThat(x).isEqualTo(i);
            i += 1;
        }
    }

    @Test
    void isEqualTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);

        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        lld2.addLast(1);
        lld2.addLast(2);
        lld2.addLast(3);

        assertThat(lld).isEqualTo(lld2);

        lld2.addLast(4);
        assertThat(lld).isNotEqualTo(lld2);

        Deque61B<String> lld3 = new LinkedListDeque61B<>()  ;
        lld3.addLast("front");
        lld3.addLast("mid");
        lld3.addLast("end");
        assertThat(lld).isNotEqualTo(lld3);
    }

    @Test
    void toStringTest(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        System.out.println(lld1);

    }
}
