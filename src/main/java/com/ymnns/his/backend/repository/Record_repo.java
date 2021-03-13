package com.ymnns.his.backend.repository;

import com.ymnns.his.backend.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Record_repo extends JpaRepository<Record, Integer> {
}
