package com.ymnns.his.backend.repository;

import com.ymnns.his.backend.entity.Prescription_Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Prescription_content_repo extends JpaRepository<Prescription_Content, Integer> {
    @Query(value = "from Prescription_Content where prescription_id=:prescription_id")
    List<Prescription_Content> findAllByPrescription_id(Integer prescription_id);
}
