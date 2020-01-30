package org.dutch.merchant.cartesian;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
        int leftIdx = 0;
        int rightIdx = 0;
        List<Integer> merged = new ArrayList<>(maxSize);
        while (leftIdx < leftArray.length && rightIdx < rightArray.length && merged.size() < maxSize) {
            int sum = leftArray[leftIdx] + rightArray[rightIdx];
            if (merged.size() == 0 || merged.get(merged.size() - 1) < sum) {
                merged.add(sum);
            }
            if (leftArray[leftIdx] < rightArray[rightIdx]) {
                leftIdx++;
            } else {
                rightIdx++;
            }
        }

        while (leftIdx < leftArray.length && merged.size() < maxSize) {
            int sum = leftArray[leftIdx] + rightArray[rightArray.length - 1];
            if (merged.size() == 0 || merged.get(merged.size() - 1) < sum) {
                merged.add(sum);
            }
            leftIdx++;
        }

        while (rightIdx < rightArray.length && merged.size() < maxSize) {
            int sum = leftArray[leftArray.length - 1] + rightArray[rightIdx];
            if (merged.size() == 0 || merged.get(merged.size() - 1) < sum) {
                merged.add(sum);
            }

            rightIdx++;
        }

        return merged.stream().mapToInt(x -> x);
    }
}
