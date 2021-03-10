package com.cyan.modclima.models;

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
@Table(name = "mills")
public class Mill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mill", fetch = FetchType.EAGER)
    private List<Harvest> harvests;

    public Mill update(Mill mill) {
        this.name = mill.getName();

        this.harvests.clear();
        mill.getHarvests().forEach(harvest -> harvest.setMill(this));
        this.harvests.addAll(mill.getHarvests());

        return this;
    }

}
