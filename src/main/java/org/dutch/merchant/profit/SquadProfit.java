package org.dutch.merchant.profit;

import org.dutch.merchant.MerchantConfig;
import org.dutch.merchant.cartesian.SortedSum;
import org.dutch.merchant.dto.SquadDto;
import org.dutch.merchant.mapper.DtoMapper;

import java.util.List;
import java.util.stream.IntStream;

// total profit is equal to sum of profits of each pile
public class SquadProfit {
    private final List<List<Integer>> listOfPiles;

    public SquadProfit(List<List<Integer>> listOfPiles) {
        this.listOfPiles = listOfPiles;
    }

    public SquadDto calculate(MerchantConfig conf) {
        return listOfPiles.stream()
                .parallel()
                .map(pile -> new PileProfit(pile).calculate(conf))
                .map(DtoMapper::to)
                .reduce(new SquadDto(), (left, right) -> accumulate(left, right, conf));
    }

    private static SquadDto accumulate(SquadDto left, SquadDto right, MerchantConfig conf) {
        return new SquadDto(
                SortedSum.limitedBy(IntStream.of(left.sizes), IntStream.of(right.sizes), conf.MAX_RESULTS).toArray(),
                left.profit + right.profit
        );
    }
}
