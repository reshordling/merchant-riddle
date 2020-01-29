package org.dutch.merchant.warehouse;

import org.dutch.merchant.warehouse.model.Squad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class Audit {

    private final String ZERO = "0";

    public Stream<Squad> lines(Stream<String> lines) {
        var labelWrapper = new Object() {
            int remainingPiles = 0;
        };

        // speed-wise LinkedList can be also a good candidate as
        // it keeps the tail-reference, no need for size-checks
        List<Squad> auditedSquads = new ArrayList<>();

        lines.map(String::trim)
                .takeWhile(line -> !ZERO.equals(line))
                // this is a potentially great place for Flux or ReactiveJava
                .forEach(line -> {
                    if (labelWrapper.remainingPiles-- == 0) {
                        labelWrapper.remainingPiles = Integer.valueOf(line);
                        int squadLabel = auditedSquads.size() + 1;
                        auditedSquads.add(new Squad(squadLabel));
                    } else {
                        auditedSquads
                                .get(auditedSquads.size() - 1)
                                .piles
                                .add(toPile(line));
                    }
                });

        return auditedSquads.stream();
    }

    private List<Integer> toPile(String pileDesc) {
        var wrapper = new Object() { Integer size; };
        return Arrays.stream(pileDesc.split("\\s+"))
                .takeWhile(ignored -> wrapper.size == null || wrapper.size-- > 0)
                .map(Integer::valueOf)
                .filter(nr -> {
                    if (wrapper.size == null) {
                        wrapper.size = nr;
                        return false;
                    }
                    return true;
                })
                .collect(toCollection(ArrayList::new));
    }
}
