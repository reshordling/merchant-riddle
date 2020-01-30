package org.dutch.merchant.profit;

import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.dutch.merchant.profit.ReportConfig.BASE_PRICE;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PileProfitTest {

    private final int ONE_M = 1_000_000;

    private final List<Integer> FIRST = asList(12, 3, 10, 7, 16, 5);
    private final List<Integer> SECOND = asList(7, 3, 11, 9, 10);
    private final List<Integer> THIRD = asList(1, 2, 3, 4, 10, 16, 10, 4, 16);

    private final List<Integer> TOO_EXPENSIVE = asList(BASE_PRICE, BASE_PRICE, BASE_PRICE);

    private final List<Integer> VERY_LARGE_CHEAP = IntStream
            .generate(() -> BASE_PRICE - 1)
            .limit(ONE_M)
            .boxed()
            .collect(toList());

    private final List<Integer> VERY_LARGE_EXPENSIVE = IntStream
            .generate(() -> BASE_PRICE)
            .limit(ONE_M)
            .boxed()
            .collect(toList());

    @Test
    public void defaultPiles() {
        validate(assess(FIRST), 8, 4);
        validate(assess(SECOND), 10, 2, 4, 5);
        validate(assess(THIRD), 30, 4, 5 ,8);
    }

    @Test
    public void largePiles() {
        validateMaxProfit(assess(VERY_LARGE_CHEAP), ONE_M);
        validateMaxProfit(assess(VERY_LARGE_EXPENSIVE), 0);

    }

    @Test
    public void tooExpensive() {
        validate(assess(TOO_EXPENSIVE), 0);
    }

    private PartyProfit assess(List<Integer> pile) {
        return new PileProfit(pile).calculate();
    }

    private void validate(PartyProfit partyProfit, int maxProfit, int... sizes) {
        validateMaxProfit(partyProfit, maxProfit);
        assertArrayEquals("party sizes", sizes, partyProfit.sizes.toArray());
    }

    private void validateMaxProfit(PartyProfit partyProfit, int maxProfit) {
        assertEquals("max profit", maxProfit, partyProfit.profit);
    }

}
