package com.cyan.modclima.dtos;

import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.models.Mill;
import com.cyan.modclima.translators.HarvestTranslator;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MillDTO {

    private Long id;

    @NotEmpty
    private String name;

    private List<HarvestDTO> harvests = new ArrayList<>();

    public MillDTO(Mill mill) {
        this.id = mill.getId();
        this.name = mill.getName();

        this.harvests.addAll(HarvestTranslator.toListDto(mill.getHarvests()));
    }
}
