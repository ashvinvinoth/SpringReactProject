package com.backend.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.config.JwtTokenUtil;
import com.backend.app.domain.AuthToken;
import com.backend.app.domain.UserLogin;
import com.backend.app.model.User;
import com.backend.app.repository.UserRepository;
import com.backend.app.service.UserService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class JwtController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;

	 @RequestMapping(value = "/login", method = RequestMethod.POST)
	    public ResponseEntity<?> register(@RequestBody UserLogin loginUser) throws AuthenticationException {

	        final Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginUser.getUsername(),
	                        loginUser.getPassword()
	                )
	        );
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        final String token = jwtTokenUtil.generateToken(authentication);
	        System.out.println("token"+token);
	        return ResponseEntity.ok(new AuthToken(token));
	    }
	 
	 @GetMapping("/userDetails")
	 public ResponseEntity getUserDetails(){
		Map<String, String> user= userService.getUserDetails();
		System.out.println(user);
		return ResponseEntity.ok(user);
	 }
	 
}
