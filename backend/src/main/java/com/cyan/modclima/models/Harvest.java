package com.cyan.modclima.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "harvests")
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column
    private String code;

    @Column
    private LocalDate start;

    @Column(name = "end_date")
    private LocalDate end;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Farm> farms;

    public Harvest update(Harvest harvest) {
        this.code = harvest.getCode();
        this.start = harvest.getStart();
        this.end = harvest.getEnd();

        this.farms.clear();
        this.farms.addAll(harvest.getFarms());

        return this;
    }
}
