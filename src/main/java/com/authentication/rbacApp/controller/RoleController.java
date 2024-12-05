package com.authentication.rbacApp.controller;

import com.authentication.rbacApp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/hello")
    public String hello(){
        return "the app is working please create";
    }

    @PostMapping("/create")
    public String createRole(@RequestParam String roleName) {
        roleService.createRole(roleName);
        return "Role created successfully";
    }

}
