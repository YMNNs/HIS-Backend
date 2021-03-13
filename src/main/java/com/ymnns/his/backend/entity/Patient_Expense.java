package com.ymnns.his.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Patient_Expense {
    @Id
    // 设置自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer receipt_id;
    private Integer item_id;
    private Integer item_type;
    private String item_name;
    private Float item_price;
    private Integer quantity;
    private Date issue_time;
    private Integer issuer_id;
    private Date settle_time;
    private Integer settler_id;
    private Integer settle_method;

}
