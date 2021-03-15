package com.cyan.modclima.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFarmDTO {

    @NotEmpty
    private String code;

    @NotEmpty
    private String name;

    private List<FieldDTO> fields = new ArrayList<>();

    private Long harvestId;
}
