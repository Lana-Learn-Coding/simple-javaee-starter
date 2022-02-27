package io.lana.ejb.customer;

import io.lana.ejb.lib.repo.Audited;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends Audited<Integer> {
    @Id
    @GeneratedValue
    private Integer id;

    @FormParam("name")
    @NotBlank
    private String name;

    @FormParam("gender")
    private boolean gender = true;

    @FormParam("email")
    @Email
    @NotBlank
    private String email;

    @FormParam("phone")
    @Pattern(regexp = "^0[0-9]{9,10}$")
    @NotBlank
    private String phone;

    @FormParam("birthDate")
    @NotNull
    @Column(name = "birth_date")
    private LocalDate birthDate;
}
