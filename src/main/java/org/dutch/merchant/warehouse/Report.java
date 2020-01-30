package org.dutch.merchant.warehouse;

import org.dutch.merchant.MerchantConfig;
import org.dutch.merchant.dto.SquadDto;
import org.dutch.merchant.model.Squad;
import org.dutch.merchant.profit.SquadProfit;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class Report {

    public void generate(Stream<Squad> squads, MerchantConfig conf) {
        squads.map(squad -> fillSquadReport(squad, conf))
                .forEach(Report::printSquadReport);
    }

    private static SquadDto fillSquadReport(Squad squad, MerchantConfig conf) {
        SquadDto dto = new SquadProfit(squad.piles).calculate(conf);
        dto.label = squad.label;
        return dto;
    }

    private static void printSquadReport(SquadDto dto) {
        System.out.println("shuurs " + dto.label);
        System.out.println("Maximum profit is " + dto.profit + ".");
        System.out.println("Number of fluts to buy: "
                + (dto.profit == 0 ? 0 : join(dto.sizes)));
    }

    private static String join(int[] sizes) {
        return stream(sizes)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
    }
}
