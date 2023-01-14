package com.ty.ems.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Builder
@AllArgsConstructor
@Data

@NoArgsConstructor
@Table(name = "employee_management_project")
public class EmployeePrimary implements UserDetails {

//	@GenericGenerator(name = "empnextId", strategy = "com.ty.ems.controller.idgenerator.IdGenerator", parameters = {
//			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
//			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "TYC"),
//			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	private String employeeId;

	private String empName;
	private String empEmail;
	private String password;
	@Column(name = "phone_number")
	private String phoneNumber;

	// one to many unidirectional mapping
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "Id", nullable = false)
	@JsonIgnore
	private List<EmployeeBank> banks;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "Id", nullable = false)
	@JsonIgnore
	private List<EmployeeAddress> addresses;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Id", nullable = false)
	@JsonIgnore
	private EmployeeDepartment departments;
	
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
	            joinColumns = @JoinColumn(name = "Id", nullable = false, updatable = false), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Roles> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//Set<Roles> roles2 = this.getRoles();
		//List<SimpleGrantedAuthority> authorities= new ArrayList<>();
	Set<SimpleGrantedAuthority> collect = this.roles.stream().map((role)-> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet());
 //     List<SimpleGrantedAuthority> collect=roles2.stream().map((role)-> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
		return collect;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.getEmployeeId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
