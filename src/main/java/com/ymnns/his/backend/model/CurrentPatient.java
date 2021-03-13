package com.ymnns.his.backend.model;

import lombok.Data;

@Data
public class CurrentPatient {
    private String name;
    private Integer record_id;
    private String sex;
    private Integer age;
    private String age_type;
}
