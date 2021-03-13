package com.ymnns.his.backend.repository;

import com.ymnns.his.backend.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Prescription_repo extends JpaRepository<Prescription, Integer> {

    @Query(value = "from Prescription where record_id=:record_id")
    Prescription findByRecord_id(Integer record_id);
}
