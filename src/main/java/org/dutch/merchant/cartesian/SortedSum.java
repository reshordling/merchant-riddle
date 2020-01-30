package org.dutch.merchant.cartesian;

import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;

// cartesian sum of two ordered streams
// result is also sorted and truncated by maxSize
// NB! (a, b) + () == (a, b)
public class SortedSum {

    // args are sorted
    public static IntStream limitedBy(IntStream left, IntStream right, int maxSize) {
        int[] leftArray = left.toArray();

        if (leftArray.length == 0) {
            return right;
        }
        int[] rightArray = right.toArray();
        if (rightArray.length == 0) {
            return IntStream.of(leftArray);
        }

        return range(0, leftArray.length).flatMap(i ->
                // this can be optimized by using simple heuristics:
                // leftArray[i] + rightArray[j] <= leftArray[i+a] + rightArray[j+b],
                // where any a,b >= 0
                range(0, rightArray.length).map(j -> leftArray[i] + rightArray[j]))
                .distinct()
                .sorted()
                .limit(maxSize);
    }
}
