package com.ymnns.his.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Reg_Info {
    @Id
    // 设置自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer record_id;
    private String name;
    private String sex;
    private String id_no;
    private Integer age;
    private String age_type;
    private String address;
    private Date current_visit_date;
    private Date dob;
    private String period;
    private Integer dept_id;
    private Integer doctor_id;
    private Integer level_id;
    private Integer settlement_id;
    private Integer is_need_medical_history_book;
    private Date reg_time;
    private Integer operator_id;
    private Integer visit_status;


}
