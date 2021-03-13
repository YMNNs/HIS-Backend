package com.ymnns.his.backend.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Drugs {
    @Id
    private Integer id;
    private String name;
    private String format;
    private Float price;
    private String mnemonic_code;
    private String drug_code;
}
