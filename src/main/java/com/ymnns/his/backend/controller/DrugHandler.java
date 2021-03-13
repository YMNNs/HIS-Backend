package com.ymnns.his.backend.controller;

import com.ymnns.his.backend.entity.Drugs;
import com.ymnns.his.backend.entity.Prescription;
import com.ymnns.his.backend.entity.Prescription_Content;
import com.ymnns.his.backend.model.DispenseList;
import com.ymnns.his.backend.repository.Drugs_repo;
import com.ymnns.his.backend.repository.Prescription_content_repo;
import com.ymnns.his.backend.repository.Prescription_repo;
import com.ymnns.his.backend.repository.Reg_info_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/drugs")

public class DrugHandler {
    @Autowired
    private Drugs_repo drugs_repo;
    @Autowired
    private Reg_info_repo reg_info_repo;
    @Autowired
    private Prescription_content_repo prescription_content_repo;
    @Autowired
    private Prescription_repo prescription_repo;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Drugs> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return drugs_repo.findAll(pageable);
    }

    @GetMapping("/findDrugList/{record_id}")
    public List<DispenseList> findDrugList(@PathVariable("record_id") Integer record_id) {
        List<DispenseList> dispenseLists = new ArrayList<>();
        Prescription prescription = prescription_repo.findByRecord_id(record_id);
        List<Prescription_Content> prescription_contents = prescription_content_repo.findAllByPrescription_id(prescription.getId());
        for (Prescription_Content prescription_content : prescription_contents) {
            if (prescription_content.getStatus() == 2) {
                DispenseList dispenseList = new DispenseList();
                Drugs drug = drugs_repo.findById(prescription_content.getDrug_id()).orElse(null);
                if (drug != null) {
                    dispenseList.setDrug_name(drug.getName());
                    dispenseList.setDrug_code(drug.getDrug_code());
                    dispenseList.setPrescription_content_id(prescription_content.getId());
                    dispenseList.setQuantity(prescription_content.getQuantity());
                    dispenseList.setStatus_name("已缴费");
                    dispenseLists.add(dispenseList);
                }
            }
        }
        return dispenseLists;
    }

    @PostMapping("/confirmDispense")
    public Integer confirmDispense(@RequestBody List<DispenseList> dispenseLists) {
        int counter = 0;
        for (DispenseList dispenseList : dispenseLists) {
            Prescription_Content prescription_content = prescription_content_repo.findById(dispenseList.getPrescription_content_id()).orElse(null);
            if (prescription_content != null) {
                prescription_content.setStatus(3);
                prescription_content_repo.save(prescription_content);
                counter++;
            }
        }
        return counter;
    }
}
