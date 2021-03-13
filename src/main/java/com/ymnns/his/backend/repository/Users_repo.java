package com.ymnns.his.backend.repository;

import com.ymnns.his.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Users_repo extends JpaRepository<Users, Integer> {
    @Query(value = "from Users where dept_id=:dept_id")
    List<Users> findAllByDept_id(Integer dept_id);

    @Query(value = "select reg_level_id from Users where id=:id")
    Integer findRegLevelIdById(Integer id);

    @Query(value = "from Users where username=:username and password=:password")
    Users findByUsernameAndPassword(String username, String password);
}
