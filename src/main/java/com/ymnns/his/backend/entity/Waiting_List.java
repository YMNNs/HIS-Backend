package com.ymnns.his.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Waiting_List {
    @Id
    // 设置自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer record_id;
    private Integer ticket_id;
    private Date sign_time;
    private Integer doctor_id;
    private Integer dept_id;
}
