package com.ymnns.his.backend.controller;

import com.ymnns.his.backend.entity.Users;
import com.ymnns.his.backend.model.LoginForm;
import com.ymnns.his.backend.repository.Users_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersHandler {
    @Autowired
    private Users_repo users_repo;

    @GetMapping("/doctor/{deptId}")
    public List<Users> findByDept_id(@PathVariable("deptId") Integer deptId) {
        return users_repo.findAllByDept_id(deptId);
    }

    @PostMapping("/login")
    public Users login(@RequestBody LoginForm loginForm) {
        return users_repo.findByUsernameAndPassword(loginForm.getUsername(), loginForm.getPassword());
    }

    @PostMapping("/logout")
    public void logout() {

    }

}
