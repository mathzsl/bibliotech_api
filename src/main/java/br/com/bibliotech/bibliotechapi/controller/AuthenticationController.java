package br.com.bibliotech.bibliotechapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bibliotech.bibliotechapi.dto.LoginDTO;
import br.com.bibliotech.bibliotechapi.dto.LoginResponseDTO;
import br.com.bibliotech.bibliotechapi.dto.RegisterDTO;
import br.com.bibliotech.bibliotechapi.dto.UserResponseDTO;
import br.com.bibliotech.bibliotechapi.model.User;
import br.com.bibliotech.bibliotechapi.service.AuthenticationService;
import br.com.bibliotech.bibliotechapi.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
  
  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
    try {
      User newUser = authenticationService.register(registerDTO);

      return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
    var userNamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
    var auth = this.authenticationManager.authenticate(userNamePassword);
    var user = (User) auth.getPrincipal();
    
    var token = tokenService.generateToken((User) auth.getPrincipal());

    UserResponseDTO userResponse = new UserResponseDTO(user.getEmail(), user.getName());

    LoginResponseDTO response = new LoginResponseDTO(token, userResponse);

    return ResponseEntity.ok(response);
  }
}
