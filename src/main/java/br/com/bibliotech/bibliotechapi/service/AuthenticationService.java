package br.com.bibliotech.bibliotechapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.bibliotech.bibliotechapi.dto.RegisterDTO;
import br.com.bibliotech.bibliotechapi.model.User;
import br.com.bibliotech.bibliotechapi.model.enums.Role;
import br.com.bibliotech.bibliotechapi.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {
  
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username);
  }

  public User register(RegisterDTO registerDTO) {
    if (userRepository.findByEmail(registerDTO.getEmail()) != null) {
      throw new RuntimeException("Email already in use.");
    }

    String hashedPassword = passwordEncoder.encode(registerDTO.getPassword());

    User newUser = new User();
    newUser.setName(registerDTO.getName());
    newUser.setEmail(registerDTO.getEmail());
    newUser.setPasswordHash(hashedPassword);
    newUser.setRole(Role.USER); 

    return userRepository.save(newUser);
  }
}
