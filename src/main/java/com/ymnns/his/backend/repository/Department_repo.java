package com.ymnns.his.backend.repository;

import com.ymnns.his.backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Department_repo extends JpaRepository<Department, Integer> {
    @Query(value = "from Department where id=:id")
    Department getNameById(Integer id);
}
