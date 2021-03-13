package com.ymnns.his.backend.repository;

import com.ymnns.his.backend.entity.Reg_Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Reg_info_repo extends JpaRepository<Reg_Info, Integer> {
    @Query(value = "select MAX(record_id+1) from Reg_Info")
    Integer findAvailableRecord_id();

    @Query(value = "from Reg_Info where record_id=:record_id")
    List<Reg_Info> findAllByRecord_id(Integer record_id);


}
