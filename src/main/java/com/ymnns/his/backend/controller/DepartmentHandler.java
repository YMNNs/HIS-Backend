package com.ymnns.his.backend.controller;

import com.ymnns.his.backend.entity.Department;
import com.ymnns.his.backend.repository.Department_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentHandler {
    @Autowired
    private Department_repo department_repo;

    @GetMapping("/findAll")
    public List<Department> findAll() {
        return department_repo.findAll();
    }

}
