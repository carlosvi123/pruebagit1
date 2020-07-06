package es.soprasteria.formacion.service;

import java.util.Arrays;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetails implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    if (username.equals("admin")) {
      return new User("admin", encoder.encode("admin"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
    } else if (username.equals("user")) {
      return new User("user", encoder.encode("user"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    } else {
      throw new UsernameNotFoundException("User not found");
    }
  }
}
