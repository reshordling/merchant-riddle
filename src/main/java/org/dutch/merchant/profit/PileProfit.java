package org.dutch.merchant.profit;

import org.dutch.merchant.dto.PileDto;
import org.dutch.merchant.MerchantConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PileProfit {

    private final List<Integer> pile;
    // cumulative profit of pile N elements
    private final List<Integer> partyProfits;
    private int maxProfit;

    public PileProfit(List<Integer> pile) {
        this.pile = pile;
        this.partyProfits = new ArrayList<>(pile.size());
    }

    private IntStream estimateProfits(MerchantConfig conf) {
        return IntStream.range(0, pile.size())
                // profit depends on the
                .map(idx ->
                        getProfitOrDefault(idx - 1, 0)
                                // potential optimisation place
                                + conf.BASE_PRICE
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

    public PileDto calculate(MerchantConfig conf) {
        int maxProfit = estimateProfits(conf)
                .max()
                .orElse(0);
        if (maxProfit < 1) {
            return new PileDto();
        }

        IntStream partySizes = findPartySizes(maxProfit)
                // limit results for faster calculation
                .limit(conf.MAX_RESULTS);
        return new PileDto(partySizes, maxProfit);
    }

    private int getProfitOrDefault(int idx, int defaultProfit) {
        return idx < 0 ? defaultProfit : partyProfits.get(idx);
    }
}
