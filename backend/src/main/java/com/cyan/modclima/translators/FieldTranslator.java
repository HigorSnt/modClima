package com.cyan.modclima.translators;

import com.cyan.modclima.dtos.FieldDTO;
import com.cyan.modclima.models.Field;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FieldTranslator {

    public static FieldDTO toOneDto(Field field) {
        return new FieldDTO(field);
    }

    public static List<FieldDTO> toListDto(List<Field> fields) {
        if (fields == null) return Collections.emptyList();
        return fields
                .stream()
                .map(FieldTranslator::toOneDto)
                .collect(Collectors.toList());
    }

    public static Field toModel(FieldDTO fieldDTO) {
        return Field
                .builder()
                .id(fieldDTO.getId())
                .geom(fieldDTO.getGeom())
                .code(fieldDTO.getCode())
                .build();
    }

    public static List<Field> toListModel(List<FieldDTO> fieldDTOS) {
        return fieldDTOS
                .stream()
                .map(FieldTranslator::toModel)
                .collect(Collectors.toList());
    }

}
