package io.lana.ejb.bus;

import io.lana.ejb.lib.repo.Audited;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "bus_ride")
public class BusRide extends Audited<Integer> {
    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String driver;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Min(0)
    @Column(name = "number_of_passenger")
    private Integer numberOfPassenger;

    @NotNull
    @Min(0)
    private Double price;
}
