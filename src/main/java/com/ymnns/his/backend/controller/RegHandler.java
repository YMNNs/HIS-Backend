package com.ymnns.his.backend.controller;

import com.ymnns.his.backend.entity.*;
import com.ymnns.his.backend.model.Expense;
import com.ymnns.his.backend.model.RegTable;
import com.ymnns.his.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/register")
public class RegHandler {
    @Autowired
    private Reg_info_repo reg_info_repo;
    @Autowired
    private Users_repo users_repo;
    @Autowired
    private Department_repo department_repo;
    @Autowired
    private Waiting_list_repo waiting_list_repo;
    @Autowired
    private Prescription_content_repo prescription_content_repo;
    @Autowired
    private Prescription_repo prescription_repo;
    @Autowired
    private Drugs_repo drugs_repo;
    @Autowired
    private Patient_expense_repo patient_expense_repo;

    @GetMapping("/findAll")
    public List<Reg_Info> findAll() {
        return reg_info_repo.findAll();
    }

    @PostMapping("/save")
    public Integer save(@RequestBody Reg_Info reg_info) {
        reg_info.setRecord_id(getMaxRecordId());
        reg_info.setLevel_id(users_repo.findRegLevelIdById(reg_info.getDoctor_id()));
        reg_info.setReg_time(new Date());
        reg_info.setVisit_status(1); // 已挂号
        Reg_Info result = null;
        try {
            result = reg_info_repo.save(reg_info);
            Waiting_List waitingList = new Waiting_List();
            waitingList.setDept_id(reg_info.getDept_id());
            waitingList.setRecord_id(reg_info.getRecord_id());
            waitingList.setSign_time(new Date());
            waitingList.setDoctor_id(reg_info.getDoctor_id());
            waitingList.setTicket_id(result.getId());
            waiting_list_repo.save(waitingList);
            return result.getRecord_id();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Integer getMaxRecordId() {
        return reg_info_repo.findAvailableRecord_id();
    }

    @GetMapping("/findAllByRecord_id/{record_id}")
    public List<RegTable> findAllByRecord_id(@PathVariable("record_id") Integer record_id) {
        List<Reg_Info> reg_info = reg_info_repo.findAllByRecord_id(record_id);
        List<RegTable> regTables = new ArrayList<>();
        for (Reg_Info regInfo : reg_info) {
            RegTable regTable = new RegTable();
            regTable.setCurrent_visit_date(regInfo.getCurrent_visit_date());
            regTable.setId_no(regInfo.getId_no());
            regTable.setName(regInfo.getName());
            regTable.setRecord_id(regInfo.getRecord_id());
            regTable.setPeriod(regInfo.getPeriod());
            regTable.setDept_name(department_repo.getNameById(regInfo.getDept_id()).getName());
            switch (regInfo.getVisit_status()) {
                case 1: {
                    regTable.setStatus_name("已挂号");
                    break;
                }
                case 2: {
                    regTable.setStatus_name("已退号");
                    break;
                }
                case 3: {
                    regTable.setStatus_name("已看诊");
                    break;
                }
                default: {
                    regTable.setStatus_name("未知");
                }
            }
            regTables.add(regTable);
        }

        return regTables;
    }

    @GetMapping("/refund/{record_id}")
    public Integer refundByRecord_id(@PathVariable("record_id") Integer record_id) {
        int counter = 0;
        List<Reg_Info> reg_info = reg_info_repo.findAllByRecord_id(record_id);
        for (Reg_Info regInfo : reg_info) {
            if (regInfo.getVisit_status() == 1) {
                int waiting_id = waiting_list_repo.findByRecord_id(record_id).getId();
                waiting_list_repo.deleteById(waiting_id);
                regInfo.setVisit_status(2);
                reg_info_repo.save(regInfo);
                counter++;
            }
        }
        return counter;
    }

    @GetMapping("/cashier/{record_id}")
    public List<Expense> findExpenseByRecord_id(@PathVariable("record_id") Integer record_id) {
        List<Expense> expenses = new ArrayList<>();
        Prescription prescription = prescription_repo.findByRecord_id(record_id);
        List<Prescription_Content> prescription_contents = prescription_content_repo.findAllByPrescription_id(prescription.getId());
        for (Prescription_Content prescription_content : prescription_contents) {
            Expense expense = new Expense();
            expense.setDoctor_id(prescription.getDoctor_id());
            expense.setRecord_id(record_id);
            expense.setDrug_id(prescription_content.getDrug_id());
            expense.setIssue_time(prescription.getCreate_time());
            expense.setPrescription_content_id(prescription_content.getId());
            Drugs drug = drugs_repo.findById(prescription_content.getDrug_id()).orElse(null);
            assert drug != null;
            expense.setItem_name(drug.getName());
            expense.setPrice(drug.getPrice());
            expense.setName(reg_info_repo.findAllByRecord_id(record_id).get(0).getName());
            expense.setQuantity(prescription_content.getQuantity());
            if (prescription_content.getStatus() == 1) {
                expense.setStatus_name("已开立");
                expenses.add(expense);
            }
        }
        return expenses;
    }

    @PostMapping("/settle")
    public Integer settle(@RequestBody List<Expense> expenses, @RequestParam("settler_id") Integer settler_id, @RequestParam("settle_method") Integer settle_method, @RequestParam("receipt_id") Integer receipt_id) {
        Integer counter = 0;
        for (Expense expense : expenses) {
            //先设置prescription_content状态
            Objects.requireNonNull(prescription_content_repo.findById(expense.getPrescription_content_id()).orElse(null)).setStatus(2);
            //再写入消费表
            Patient_Expense patient_expense = new Patient_Expense();
            patient_expense.setReceipt_id(receipt_id);
            patient_expense.setItem_id(expense.getDrug_id());
            patient_expense.setItem_name(expense.getItem_name());
            patient_expense.setItem_type(1);
            patient_expense.setItem_price(expense.getPrice());
            patient_expense.setQuantity(expense.getQuantity());
            patient_expense.setIssue_time(expense.getIssue_time());
            patient_expense.setIssuer_id(expense.getDoctor_id());
            patient_expense.setSettle_time(new Date());
            patient_expense.setSettler_id(settler_id);
            patient_expense.setSettle_method(settle_method);
            patient_expense_repo.save(patient_expense);
            counter++;
        }
        return counter;
    }
}
