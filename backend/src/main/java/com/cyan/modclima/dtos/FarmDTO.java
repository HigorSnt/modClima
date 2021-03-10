package com.cyan.modclima.dtos;

import com.cyan.modclima.models.Farm;
import com.cyan.modclima.translators.FieldTranslator;
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
public class FarmDTO {

    private Long id;

    @NotEmpty
    private String code;

    @NotEmpty
    private String name;

    private List<FieldDTO> fields = new ArrayList<>();

    public FarmDTO(Farm farm) {
        this.id = farm.getId();
        this.code = farm.getCode();
        this.name = farm.getName();

        this.fields.addAll(FieldTranslator.toListDto(farm.getFields()));
    }

}
