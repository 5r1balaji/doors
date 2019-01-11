package com.agam.doors.controller;

import com.agam.doors.config.JwtTokenUtil;
import com.agam.doors.model.ApiResponse;
import com.agam.doors.model.User;
import com.agam.doors.service.UserService;
import com.agam.doors.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.agam.doors.model.Constants.TOKEN_PREFIX;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/signup")
    public ApiResponse<User> saveUser(@RequestBody UserDto user){
        return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",userService.save(user));
    }

    @GetMapping
    public ApiResponse<List<User>> listUser(@RequestHeader(value="Authorization") String token){
        return new ApiResponse<>(HttpStatus.OK.value(), "User list fetched successfully.",userService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getOne(@PathVariable int id){
        return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.",userService.findById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDto> update(@RequestBody UserDto userDto,@RequestHeader(value="Authorization") String token) {
        return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.",userService.update(userDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable int id) {
        userService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "User deleted successfully.", null);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestHeader(value="Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            String  authToken = token.replace(TOKEN_PREFIX,"");
            jwtTokenUtil.invalidateToken(authToken);
            headers.remove("Authorization");
        }

        return  new  ResponseEntity<ApiResponse<String>>(new ApiResponse<>(200, "successfully logged out",null),headers, HttpStatus.CREATED);
    }


}
