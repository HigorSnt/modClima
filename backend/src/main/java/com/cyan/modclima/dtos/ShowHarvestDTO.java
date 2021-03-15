package com.cyan.modclima.dtos;

import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.translators.FarmTranslator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowHarvestDTO {

    private Long id;
    private String code;

    @JsonSerialize(using = ToStringSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate start;

    @JsonSerialize(using = ToStringSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate end;

    private List<FarmDTO> farms = new ArrayList<>();
    private Mill mill;

    public ShowHarvestDTO(Harvest harvest) {
        this.code = harvest.getCode();
        this.id = harvest.getId();
        this.start = harvest.getStart();
        this.end = harvest.getEnd();
        this.farms = FarmTranslator.toListDto(harvest.getFarms());
        this.mill = new Mill(harvest.getMill().getId(), harvest.getMill().getName());
    }

}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Mill {

    private Long id;
    private String name;

}
