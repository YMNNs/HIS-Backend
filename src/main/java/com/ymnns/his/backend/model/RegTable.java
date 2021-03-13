package com.ymnns.his.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RegTable {
    private Integer record_id;
    private String name;
    private String id_no;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date current_visit_date;
    private String period;
    private String dept_name;
    private String status_name;
}
