package org.dutch.merchant.warehouse;

import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.Collections.nCopies;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;
import static org.junit.Assert.assertEquals;

public class AuditTest {

    private final int ONE_M = 1_000_000;
    private final String EOF = "0";

    private final Stream<String> OUT_OF_BOUNDS = of(
            "1",
            // too many fluts
            "1 1 2 2 2 2 2 2 2 2 2 2 2 2 2",
            EOF,
            // lines after EOF
            "1",
            "1 1"
    );

    private final Stream<String> LONG_PILE = of(
            "1",
            ONE_M + join("", nCopies(ONE_M, " 1")),
            EOF
    );

    private final Stream<String> MANY_PILES = concat(
            concat(
                    of(String.valueOf(ONE_M)),
                    nCopies(ONE_M, "1 1").stream()
            ),
            of(EOF)
    );

    private final Stream<String> MANY_SQUADS = concat(
            nCopies(ONE_M, asList("1", "1 1"))
                    .stream()
                    .flatMap(List::stream),
            of(EOF)
    );

    @Test
    public void hugePileLoaded() {
        new Audit().lines(LONG_PILE)
                .forEach(squad ->
                        squad.piles.forEach(pile ->
                                assertEquals("Pile size",
                                        ONE_M, pile.size())));
    }

    @Test
    public void hugePileNrLoaded() {
        new Audit().lines(MANY_PILES)
                .forEach(squad ->
                        assertEquals("Piles count",
                                ONE_M, squad.piles.size()));
    }

    @Test
    public void hugeSquadNrLoaded() {
        assertEquals("Squads count",
                ONE_M,
                new Audit().lines(MANY_SQUADS).count());
    }

    @Test
    public void outOfBoundsPrevented() {
        long squadCount = new Audit().lines(OUT_OF_BOUNDS)
                .peek(squad ->
                        assertEquals("Piles count",
                                1, squad.piles.size()))
                .peek(squad ->
                        squad.piles.forEach(pile ->
                                assertEquals("Pile size",
                                        1, pile.size())))
                .count();
        assertEquals("Squads count", 1, squadCount);
    }
}
