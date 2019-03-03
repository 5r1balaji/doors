package com.agam.doors.dao;

import com.agam.doors.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="doors-usermanagement" )
public interface UserDao{

    @GetMapping("/users/{name}")
    UserDto findByUsername(@PathVariable String name, @RequestHeader("X-TenantID")String  tenantId);

    @PostMapping("/users/signup")
    UserDto registerUser(@RequestBody UserDto user,@RequestHeader("X-TenantID")String  tenantId);
}
