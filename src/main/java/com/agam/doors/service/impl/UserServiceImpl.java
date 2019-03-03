package com.agam.doors.service.impl;

import com.agam.doors.dao.UserDao;
import com.agam.doors.model.UserDto;
import com.agam.doors.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private HttpServletRequest request;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("hiii"+request.getHeader("X-TenantID"));
		UserDto user = userDao.findByUsername(username,request.getHeader("X-TenantID"));
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}


    @Override
    public UserDto save(UserDto user) {
        return userDao.registerUser(user,request.getHeader("X-TenantID"));
    }
}
