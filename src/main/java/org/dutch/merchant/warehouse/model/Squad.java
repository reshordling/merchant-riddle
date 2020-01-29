package org.dutch.merchant.warehouse.model;

import java.util.ArrayList;
import java.util.List;

// better class name than "case" in the task description
// as it won't clash wish reserved keywords
public class Squad {
    public final List<List<Integer>> piles = new ArrayList<>();
    public final Integer label;

    public Squad(Integer label) {
        this.label = label;
    }
}
