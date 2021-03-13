package com.ymnns.his.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Users {
    @Id
    private Integer id;
    private String username;
    private String password;
    private String real_name;
    private Integer doctor_class_id;
    private Integer dept_id;
    private Integer reg_level_id;
}
