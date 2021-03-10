package com.cyan.modclima.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "harvest_id")
    @JsonIgnore
    private Harvest harvest;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "farm")
    private List<Field> fields;

    public Farm update(Farm farm) {
        this.code = farm.getCode();
        this.name = farm.getName();

        this.fields.clear();
        farm.getFields().forEach(field -> field.setFarm(this));
        this.fields.addAll(farm.getFields());

        return this;
    }

}
