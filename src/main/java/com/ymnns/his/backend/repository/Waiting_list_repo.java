package com.ymnns.his.backend.repository;

import com.ymnns.his.backend.entity.Waiting_List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Waiting_list_repo extends JpaRepository<Waiting_List, Integer> {

    @Query(value = "from Waiting_List where record_id=:record_id")
    Waiting_List findByRecord_id(Integer record_id);

    @Query(value = "from Waiting_List where doctor_id=:doctor_id")
    List<Waiting_List> findByDoctor_id(Integer doctor_id);

}
