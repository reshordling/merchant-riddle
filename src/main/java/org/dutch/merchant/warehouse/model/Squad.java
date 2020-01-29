package org.dutch.merchant.warehouse.model;

import java.util.ArrayList;
import java.util.List;

public class Squad {
    public final List<List<Integer>> piles = new ArrayList<>();
    public final Integer label;

    public Squad(Integer label) {
        this.label = label;
    }
}
