package org.dutch.merchant.mapper;

import org.dutch.merchant.dto.PileDto;
import org.dutch.merchant.dto.SquadDto;

public class DtoMapper {
    public static SquadDto to(PileDto pileDto) {
        return new SquadDto(pileDto.sizes.toArray(), pileDto.profit);
    }
}
