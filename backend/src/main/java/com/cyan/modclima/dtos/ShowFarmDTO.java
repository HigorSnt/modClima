package com.cyan.modclima.dtos;

import com.cyan.modclima.models.Farm;
import com.cyan.modclima.translators.FieldTranslator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowFarmDTO {

    private Long id;
    private String code;
    private String name;
    private Harvest harvest;
    private List<FieldDTO> fields;

    public ShowFarmDTO(Farm farm) {
        this.id = farm.getId();
        this.code = farm.getCode();
        this.name = farm.getName();
        this.fields = FieldTranslator.toListDto(farm.getFields());
        this.harvest = new Harvest(
                farm.getHarvest().getId(),
                farm.getHarvest().getStart(),
                farm.getHarvest().getEnd(),
                farm.getHarvest().getCode()
        );
    }

}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Harvest {

    private Long id;
    private LocalDate start;
    private LocalDate end;
    private String code;

}
