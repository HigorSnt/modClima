package com.cyan.modclima.translators;

import com.cyan.modclima.dtos.FarmDTO;
import com.cyan.modclima.models.Farm;

import java.util.List;
import java.util.stream.Collectors;

public class FarmTranslator {

    public static FarmDTO toOneDto(Farm farm) {
        return new FarmDTO(farm);
    }

    public static List<FarmDTO> toListDto(List<Farm> farms) {
        return farms
                .stream()
                .map(FarmTranslator::toOneDto)
                .collect(Collectors.toList());
    }

    public static Farm toModel(FarmDTO farmDTO) {
        return Farm
                .builder()
                .id(farmDTO.getId())
                .name(farmDTO.getName())
                .fields(FieldTranslator.toListModel(farmDTO.getFields()))
                .code(farmDTO.getCode())
                .build();
    }

    public static List<Farm> toListModel(List<FarmDTO> farmDTOS) {
        return farmDTOS
                .stream()
                .map(FarmTranslator::toModel)
                .collect(Collectors.toList());
    }

}
