package com.ymnns.his.backend.model;

import lombok.Data;

@Data
public class DrugList {
    private Integer id;
    private String name;
    private String format;
    private Float price;
    private String mnemonic_code;
    private Integer quantity;

}
