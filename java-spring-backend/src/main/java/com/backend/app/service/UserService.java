package com.backend.app.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.app.model.User;
import com.backend.app.repository.UserRepository;
@Service
public class UserService implements UserDetailsService{
@Autowired
private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
			User user=  userRepository.findByUsername(username);
			if(user == null){
				throw new UsernameNotFoundException("Invalid username or password.");
			}
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
		
	}
	
	private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}

	public Map<String, String> getUserDetails() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		auth.getPrincipal();
		auth.getAuthorities();
		Map<String,String> map= new HashMap<>();
		
		Object principal = auth.getPrincipal();
		
		String Username= ((UserDetails)principal). getUsername();
		
		if(auth.getAuthorities().toString().contains("USER")){
			map.put("role", "USER");
		}else if(auth.getAuthorities().toString().contains("ADMIN")){
			map.put("role", "ADMIN");
		}
		map.put("username", Username);
		
		
		// TODO Auto-generated method stub
		return map;
	}
	
	
	public String getUserRole()
	{
		String role = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth.getAuthorities().toString().contains("USER")){
			role="USER";
		}else if(auth.getAuthorities().toString().contains("ADMIN")){
			role="ADMIN";
		}
		return role;
			
		}
	
	public String getUserName(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		String userName = ((UserDetails)principal). getUsername();
		return userName;
	}

	
}
