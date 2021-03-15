package com.cyan.modclima.models;

import lombok.*;

import javax.persistence.*;
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

    @Column
    private String name;

    @OneToMany(mappedBy = "mill", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Harvest> harvests;

    public Mill update(Mill mill) {
        this.name = mill.getName();

        this.harvests.clear();
        mill.getHarvests().forEach(harvest -> harvest.setMill(this));
        this.harvests.addAll(mill.getHarvests());

        return this;
    }

}
