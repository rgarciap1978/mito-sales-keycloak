package com.mitocode.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idProvider;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 150)
    private String address;

    //@Enumerated(EnumType.ORDINAL)
    //@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Status enabled;
}
