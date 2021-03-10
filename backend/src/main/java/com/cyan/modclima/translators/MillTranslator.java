package com.cyan.modclima.translators;

import com.cyan.modclima.dtos.MillDTO;
import com.cyan.modclima.models.Mill;

import java.util.List;
import java.util.stream.Collectors;

public class MillTranslator {

    public static MillDTO toOneDto(Mill mill) {
        return new MillDTO(mill);
    }

    public static List<MillDTO> toListDto(List<Mill> mills) {
        return mills
                .stream()
                .map(MillTranslator::toOneDto)
                .collect(Collectors.toList());
    }

    public static Mill toModel(MillDTO millDTO) {
        return Mill
                .builder()
                .id(millDTO.getId())
                .harvests(HarvestTranslator.toListModel(millDTO.getHarvests()))
                .name(millDTO.getName())
                .build();
    }

}
