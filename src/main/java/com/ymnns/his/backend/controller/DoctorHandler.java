package com.ymnns.his.backend.controller;

import com.ymnns.his.backend.entity.*;
import com.ymnns.his.backend.model.CurrentPatient;
import com.ymnns.his.backend.model.DrugList;
import com.ymnns.his.backend.model.WaitingTable;
import com.ymnns.his.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorHandler {

    @Autowired
    private Waiting_list_repo waiting_list_repo;
    @Autowired
    private Reg_info_repo reg_info_repo;
    @Autowired
    private Record_repo record_repo;
    @Autowired
    private Prescription_repo prescription_repo;
    @Autowired
    private Prescription_content_repo prescription_content_repo;

    @GetMapping("/getWaitingList/{doctor_id}")
    public List<WaitingTable> getWaitingList(@PathVariable("doctor_id") Integer doctor_id) {
        List<WaitingTable> waitingTables = new ArrayList<>();
        List<Waiting_List> waitingLists;
        try {
            waitingLists = waiting_list_repo.findByDoctor_id(doctor_id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return waitingTables;
        }

        for (Waiting_List waitingList : waitingLists) {
            Reg_Info reg_info = reg_info_repo.findById(waitingList.getTicket_id()).orElse(null);
            if (reg_info == null) {
                continue;
            }
            WaitingTable waitingTable = new WaitingTable();
            waitingTable.setName(reg_info.getName());
            waitingTable.setRecord_id(reg_info.getRecord_id());
            waitingTables.add(waitingTable);
        }
        return waitingTables;
    }

    @GetMapping("/confirmTicket/{record_id}")
    public CurrentPatient confirmTicket(@PathVariable("record_id") Integer record_id) {
        Reg_Info reg_info = reg_info_repo.findAllByRecord_id(record_id).get(0);
        if (reg_info.getVisit_status() != 1)
            return null;
        reg_info.setVisit_status(3);
        reg_info_repo.save(reg_info);
        int waiting_id = waiting_list_repo.findByRecord_id(record_id).getId();
        waiting_list_repo.deleteById(waiting_id);
        CurrentPatient currentPatient = new CurrentPatient();
        currentPatient.setName(reg_info.getName());
        currentPatient.setAge(reg_info.getAge());
        currentPatient.setRecord_id(reg_info.getRecord_id());
        currentPatient.setSex(reg_info.getSex());
        currentPatient.setAge_type(reg_info.getAge_type());
        return currentPatient;
    }

    @PostMapping("/confirmRecord")
    public Integer confirmRecord(@RequestBody Record record) {
        record.setStatus(1);
        return record_repo.save(record).getId();
    }

    @PostMapping("/confirmPrescription")
    public Integer confirmPrescription(@RequestBody List<DrugList> drugLists, @RequestParam("doctor_id") Integer doctor_id, @RequestParam("record_id") Integer record_id) {
        Prescription prescription = new Prescription();
        prescription.setRecord_id(record_id);
        prescription.setDoctor_id(doctor_id);
        prescription.setCreate_time(new Date());
        prescription.setPrescription_name("处方");
        prescription.setStatus(1);
        Integer prescription_id = prescription_repo.save(prescription).getId();
        int counter = 0;
        for (DrugList drugList : drugLists) {
            Prescription_Content prescription_content = new Prescription_Content();
            prescription_content.setDrug_id(drugList.getId());
            prescription_content.setQuantity(drugList.getQuantity());
            prescription_content.setPrescription_id(prescription_id);
            prescription_content.setStatus(1);
            prescription_content_repo.save(prescription_content);
            counter++;
        }
        return counter;
    }
}
