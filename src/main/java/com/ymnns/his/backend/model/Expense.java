package com.ymnns.his.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Expense {
    private Integer prescription_content_id;
    private String name;
    private Integer record_id;
    private Integer drug_id;
    private Float price;
    private String item_name;
    private Integer quantity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issue_time;
    private String status_name;
    private Integer doctor_id;

}
