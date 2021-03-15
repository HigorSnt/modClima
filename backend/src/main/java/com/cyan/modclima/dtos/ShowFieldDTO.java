package com.cyan.modclima.dtos;

import com.cyan.modclima.models.Field;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowFieldDTO {

    private Long id;

    @NotEmpty
    private String code;

    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    private Point geom;

    private Farm farm;

    public ShowFieldDTO(Field field) {
        this.id = field.getId();
        this.code = field.getCode();
        this.farm = new Farm(field.getFarm().getId(), field.getFarm().getCode(), field.getFarm().getName());
        this.geom = field.getGeom();
    }

}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Farm {

    private Long id;
    private String code;
    private String name;

}