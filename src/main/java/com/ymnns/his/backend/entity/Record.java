package com.ymnns.his.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Record {
    @Id
    // 设置自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer record_id;
    private Integer ticket_id;
    private String cc;
    private String hpi;
    private String hhi;
    private String hpi_treatment;
    private String allergic_history;
    private String physical;
    private String diagnosis;
    private Integer status;
}
