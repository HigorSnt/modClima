package com.cyan.modclima.models;

import lombok.*;
import org.postgis.Point;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "fields")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column
    private String code;

    @Column
    private Point coordinates;

    public Field update(Field field) {
        this.code = field.getCode();
        this.coordinates = field.getCoordinates();

        return this;
    }

}
