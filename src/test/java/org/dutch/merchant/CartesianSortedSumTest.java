package org.dutch.merchant;

import org.apache.commons.lang3.ArrayUtils;
import org.dutch.merchant.cartesian.SortedSum;
import org.junit.Test;

import java.util.*;

import static java.util.stream.IntStream.of;
import static org.junit.Assert.assertArrayEquals;

public class CartesianSortedSumTest {

    @Test
    public void testCommutative() {
        assertCartesian(array(1, 2), array(1, 2, 3), 5);
        assertCartesian(array(1, 2, 3), array(1, 2), 5);
    }

    @Test
    public void testEmpty() {
        assertCartesian(empty(), array(1, 2, 3), 5);
        assertCartesian(empty(), empty(), 5);
    }

    @Test
    public void testLimit() {
        assertCartesian(array(1, 2, 3, 4, 5), array(1, 2, 3), 1);
    }

    private static void assertCartesian(int[] left, int[] right, int limit) {
        int[] sum = SortedSum.limitedBy(of(left), of(right), limit).toArray();
        assertArrayEquals(dumb(left, right, limit), sum);
    }

    // direct cartesian product - sum, sort, uniq, limit
    // sum ((a), empty) == (a)
    private static int[] dumb(int[] left, int[] right, int limit) {
        if (left.length == 0) {
            return right;
        }
        if (right.length == 0) {
            return left;
        }
        Integer[] leftList = ArrayUtils.toObject(left);
        Integer[] rightList = ArrayUtils.toObject(right);
        Set<Integer> merged = new HashSet<>();
        for (int leftInt : leftList) {
            for (int rightInt : rightList) {
                merged.add(leftInt + rightInt);
            }
        }
        return merged.stream().sorted().limit(limit).mapToInt(i -> i).toArray();
    }

    private static int[] array(int... els) {
        return els;
    }

    private static int[] empty() {
        return array();
    }
}
