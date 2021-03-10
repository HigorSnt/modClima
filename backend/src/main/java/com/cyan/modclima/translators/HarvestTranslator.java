package com.cyan.modclima.translators;

import com.cyan.modclima.dtos.HarvestDTO;
import com.cyan.modclima.models.Harvest;

import java.util.List;
import java.util.stream.Collectors;

public class HarvestTranslator {

    public static HarvestDTO toOneDto(Harvest harvest) {
        return new HarvestDTO(harvest);
    }

    public static List<HarvestDTO> toListDto(List<Harvest> harvests) {
        return harvests
                .stream()
                .map(HarvestTranslator::toOneDto)
                .collect(Collectors.toList());
    }

    public static Harvest toModel(HarvestDTO harvestDTO) {
        return Harvest
                .builder()
                .id(harvestDTO.getId())
                .farms(FarmTranslator.toListModel(harvestDTO.getFarms()))
                .start(harvestDTO.getStart())
                .end(harvestDTO.getEnd())
                .code(harvestDTO.getCode())
                .build();
    }

    public static List<Harvest> toListModel(List<HarvestDTO> harvestDTOS) {
        return harvestDTOS
                .stream()
                .map(HarvestTranslator::toModel)
                .collect(Collectors.toList());
    }

}
