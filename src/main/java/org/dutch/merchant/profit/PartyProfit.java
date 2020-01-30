package org.dutch.merchant.profit;

import java.util.stream.IntStream;

public class PartyProfit {
    public final IntStream sizes;
    public final long profit;

    public static final PartyProfit ZERO_PROFIT =
            new PartyProfit(IntStream.empty(), 0);

    public PartyProfit(IntStream sizes, long profit) {
        this.sizes = sizes;
        this.profit = profit;
    }
}
