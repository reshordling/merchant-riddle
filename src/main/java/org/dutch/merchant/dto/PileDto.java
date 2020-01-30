package org.dutch.merchant.dto;

import java.util.stream.IntStream;

public class PileDto {
    public final IntStream sizes;
    public final long profit;

    public PileDto(IntStream sizes, long profit) {
        this.sizes = sizes;
        this.profit = profit;
    }

    public PileDto() {
        this.sizes = IntStream.empty();
        this.profit = 0;
    }
}
