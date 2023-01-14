package com.ty.ems.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ty.ems.dao.EmployeeDao;
import com.ty.ems.entity.EmployeePrimary;
import com.ty.ems.exception.EmployeeException;

@Service
public class CustomUserDetailService  implements UserDetailsService{

	@Autowired
	private EmployeeDao dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//loadin user from database by username
		if (dao.findByEmployeeId(username)!=null) {
			EmployeePrimary primary = dao.findByEmployeeId(username);
		 return primary;
		}
		
		throw new EmployeeException("user not found");
	}

}
 