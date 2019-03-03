package com.agam.doors.controller;

import com.agam.doors.config.JwtTokenUtil;
import com.agam.doors.dao.UserDao;
import com.agam.doors.model.*;
import com.agam.doors.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDao userDao;

    @PostMapping(value = "/generate-token")
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser, @RequestHeader("X-TenantID")String  tenantId) throws AuthenticationException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final UserDto user = userDao.findByUsername(loginUser.getUsername(),tenantId);
        System.out.println(user.toString());
        final AuthToken token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success",token);
    }


}
