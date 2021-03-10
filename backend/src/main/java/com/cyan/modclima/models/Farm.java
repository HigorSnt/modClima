package com.cyan.modclima.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "farms")
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column
    private String code;

    @NotEmpty
    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Field> fields;

    public Farm update(Farm farm) {
        this.code = farm.getCode();
        this.name = farm.getName();

        this.fields.clear();
        this.fields.addAll(farm.getFields());

        return this;
    }

}
