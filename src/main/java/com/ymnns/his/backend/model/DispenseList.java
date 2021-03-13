package com.ymnns.his.backend.model;

import lombok.Data;

@Data
public class DispenseList {
    private Integer prescription_content_id;
    private String drug_name;
    private String drug_code;
    private Integer quantity;
    private String status_name;
}
