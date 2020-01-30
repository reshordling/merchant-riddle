package org.dutch.merchant.profit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.dutch.merchant.profit.PartyProfit.ZERO_PROFIT;
import static org.dutch.merchant.profit.ReportConfig.BASE_PRICE;
import static org.dutch.merchant.profit.ReportConfig.MAX_RESULTS;

public class PileProfit {

    private final List<Integer> pile;
    // cumulative profit of pile N elements
    private final List<Integer> partyProfits;
    private int maxProfit;

    public PileProfit(List<Integer> pile) {
        this.pile = pile;
        this.partyProfits = new ArrayList<>(pile.size());
    }

    private IntStream estimateProfits() {
        return IntStream.range(0, pile.size())
                // profit depends on the
                .map(idx ->
                        getProfitOrDefault(idx - 1, 0)
                                // potential optimisation place
                                + BASE_PRICE
                                - pile.get(idx))
                // can also skip remaining if the max possible profit
                // is too small
                .peek(partyProfits::add);
    }

    private IntStream findPartySizes(int maxProfit) {
        return IntStream.range(0, pile.size())
                // party size should be positive
                .filter(idx -> partyProfits.get(idx) == maxProfit)
                .map(i -> ++i);
    }

    public PartyProfit calculate() {
        int maxProfit = estimateProfits()
                .max()
                .orElse(0);
        if (maxProfit < 1) {
            return ZERO_PROFIT;
        }

        IntStream partySizes = findPartySizes(maxProfit)
                // limit results for faster calculation
                .limit(MAX_RESULTS);
        return new PartyProfit(partySizes, maxProfit);
    }

    private int getProfitOrDefault(int idx, int defaultProfit) {
        return idx < 0 ? defaultProfit : partyProfits.get(idx);
    }
}
