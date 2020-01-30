package org.dutch.merchant.dto;

public class SquadDto {
    public Integer label;
    public final int[] sizes;
    public final long profit;

    public SquadDto() {
        sizes = new int[0];
        profit = 0;
    }

    public SquadDto(int[] sizes, long profit) {
        this.sizes = sizes;
        this.profit = profit;
    }
}
