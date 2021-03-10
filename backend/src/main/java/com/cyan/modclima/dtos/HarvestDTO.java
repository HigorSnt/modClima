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

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDTO {

    private Long id;

    @NotEmpty
    private String code;

    @JsonSerialize(using = ToStringSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate start;

    @JsonSerialize(using = ToStringSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate end;

    private List<FarmDTO> farms = new ArrayList<>();

    public HarvestDTO(Harvest harvest) {
        this.id = harvest.getId();
        this.code = harvest.getCode();
        this.start = harvest.getStart();
        this.end = harvest.getEnd();

        this.farms.addAll(FarmTranslator.toListDto(harvest.getFarms()));
    }

}
