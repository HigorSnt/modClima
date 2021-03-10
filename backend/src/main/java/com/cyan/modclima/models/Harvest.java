package com.cyan.modclima.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
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
@Table(name = "harvests")
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column
    private String code;

    @JsonSerialize(using = ToStringSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column
    private LocalDate start;

    @JsonSerialize(using = ToStringSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "end_date")
    private LocalDate end;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "mill_id")
    @JsonIgnore
    private Mill mill;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "harvest")
    private List<Farm> farms;

    public Harvest update(Harvest harvest) {
        this.code = harvest.getCode();
        this.start = harvest.getStart();
        this.end = harvest.getEnd();

        this.farms.clear();
        harvest.getFarms().forEach(farm -> farm.setHarvest(this));
        this.farms.addAll(harvest.getFarms());

        return this;
    }
}
