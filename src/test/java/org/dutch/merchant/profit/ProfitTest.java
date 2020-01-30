package org.dutch.merchant.profit;

import org.dutch.merchant.MerchantConfig;
import org.dutch.merchant.dto.PileDto;
import org.dutch.merchant.dto.SquadDto;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ProfitTest {

    private final int ONE_M = 1_000_000;

    private final MerchantConfig CONFIG = new MerchantConfig();

    private final List<Integer> FIRST = asList(12, 3, 10, 7, 16, 5);
    private final List<Integer> SECOND = asList(7, 3, 11, 9, 10);
    private final List<Integer> THIRD = asList(1, 2, 3, 4, 10, 16, 10, 4, 16);

    private final List<Integer> TOO_EXPENSIVE = asList(CONFIG.BASE_PRICE,
            CONFIG.BASE_PRICE, CONFIG.BASE_PRICE);

    private final List<Integer> VERY_LARGE_CHEAP = IntStream
            .generate(() -> CONFIG.BASE_PRICE - 1)
            .limit(ONE_M)
            .boxed()
            .collect(toList());

    private final List<Integer> VERY_LARGE_EXPENSIVE = IntStream
            .generate(() -> CONFIG.BASE_PRICE)
            .limit(ONE_M)
            .boxed()
            .collect(toList());

    private final List<Integer> HUGE_REPEATING = IntStream
            .iterate(0, i -> (i + 1) % 4)
            .map(i -> i + 1)
            .limit(ONE_M * 4)
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
        validateMaxProfit(assess(HUGE_REPEATING), 30 * ONE_M);

    }

    @Test
    public void tooExpensive() {
        validate(assess(TOO_EXPENSIVE), 0);
    }


    @Test
    public void verifyWarehousePredefined() {
        validateSquad(assessWarehouse(Collections.singletonList(FIRST)), 8, 4);
        validateSquad(assessWarehouse(Arrays.asList(SECOND, THIRD)), 40, 6, 7, 8, 9, 10, 12, 13);
    }

    private PileDto assess(List<Integer> pile) {
        return new PileProfit(pile).calculate(CONFIG);
    }

    private SquadDto assessWarehouse(List<List<Integer>> piles) {
        return new SquadProfit(piles).calculate(CONFIG);
    }

    private void validate(PileDto pileDto, int maxProfit, int... sizes) {
        validateMaxProfit(pileDto, maxProfit);
        assertArrayEquals("party sizes", sizes, pileDto.sizes.toArray());
    }

    private void validateSquad(SquadDto squadDto, int maxProfit, int... sizes) {
        validateMaxProfit(squadDto, maxProfit);
        assertArrayEquals("party sizes", sizes, squadDto.sizes);
    }

    private void validateMaxProfit(PileDto pileDto, int maxProfit) {
        assertEquals("max profit", maxProfit, pileDto.profit);
    }

    private void validateMaxProfit(SquadDto squadDto, int maxProfit) {
        assertEquals("max profit", maxProfit, squadDto.profit);
    }

}
