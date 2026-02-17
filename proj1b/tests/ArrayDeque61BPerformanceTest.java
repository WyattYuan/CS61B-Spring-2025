import deque.ArrayDeque61B;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BPerformanceTest {
    private ArrayDeque61B<Integer> ad;

    // 每个测试前初始化，保证初始状态一致（控制变量）
    @BeforeEach
    void setUp() {
        ad = new ArrayDeque61B<>();
        // 先填充基础数据（模拟真实使用场景）
        for (int i = 0; i < 1000; i++) {
            ad.addLast(i);
        }
    }

    // 测试1：基础耗时测试（单次resize的耗时）
    @Test
    @DisplayName("Test resize single operation time cost")
    void resizeSingleTimeCost() {
        // 1. JVM预热：先执行几次resize，让JIT编译优化（避免首次执行耗时偏高）
        for (int i = 0; i < 100; i++) {
            ad.resize(2000);
            ad.resize(1000);
        }

        // 2. 高精度计时（System.nanoTime() 适合短时间操作，精度纳秒）
        long start = System.nanoTime();
        // 执行目标resize操作（扩容：1000→10000）
        ad.resize(10000);
        long end = System.nanoTime();

        // 3. 计算耗时（转换为毫秒，更易读）
        long costNs = end - start;
        double costMs = costNs / 1_000_000.0;

        // 打印结果（也可断言耗时上限，比如要求扩容10000元素耗时<1ms）
        System.out.printf("Single resize (1000→10000) cost: %d ns (%.3f ms)%n", costNs, costMs);
        // 可选：断言耗时不超过预期（比如针对10000元素，耗时<5ms）
        assertThat(costMs).isLessThan(5.0);
    }

    // 测试2：批量resize效率（模拟频繁resize场景）
    @Test
    @DisplayName("Test batch resize throughput")
    void resizeBatchThroughput() {
        // 1. JVM预热
        for (int i = 0; i < 1000; i++) {
            ad.resize(2000);
            ad.resize(1000);
        }

        // 2. 批量执行resize（1000次）
        int batchCount = 1000;
        long start = System.nanoTime();
        for (int i = 0; i < batchCount; i++) {
            // 模拟交替扩容/缩容（真实场景中常见的resize模式）
            ad.resize(1500);
            ad.resize(1000);
        }
        long end = System.nanoTime();

        // 3. 计算吞吐量（每秒能执行多少次resize操作）
        long totalCostNs = end - start;
        double totalCostSec = totalCostNs / 1_000_000_000.0;
        double throughput = (batchCount * 2) / totalCostSec; // 每次循环2次resize

        // 打印结果
        System.out.printf("Batch %d resize (expand+shrink) cost: %.3f sec%n", batchCount, totalCostSec);
        System.out.printf("Resize throughput: %.2f ops/sec%n", throughput);
        // 可选：断言吞吐量下限（比如要求>1000次/秒）
        assertThat(throughput).isGreaterThan(1000.0);
    }

    // 测试3：大数据量resize对比（扩容倍数对效率的影响）
    @Test
    @DisplayName("Test resize efficiency with large data")
    void resizeLargeDataComparison() {
        // 先填充10万条数据（模拟大数据量场景）
        ArrayDeque61B<Integer> largeAd = new ArrayDeque61B<>();
        for (int i = 0; i < 100_000; i++) {
            largeAd.addLast(i);
        }

        // JVM预热
        largeAd.resize(150_000);
        largeAd.resize(100_000);

        // 测试扩容到2倍（10万→20万）
        long start1 = System.nanoTime();
        largeAd.resize(200_000);
        long cost1 = System.nanoTime() - start1;

        // 测试扩容到1.5倍（10万→15万）
        long start2 = System.nanoTime();
        largeAd.resize(150_000);
        long cost2 = System.nanoTime() - start2;

        // 打印对比结果
        System.out.printf("Resize 100k→200k cost: %d ns%n", cost1);
        System.out.printf("Resize 100k→150k cost: %d ns%n", cost2);
        // 逻辑：扩容越大，耗时应越高（因为复制更多元素）
        assertThat(cost1).isGreaterThan(cost2);
    }
}
