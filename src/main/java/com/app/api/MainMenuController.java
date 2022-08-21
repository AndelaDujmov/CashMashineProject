package com.app.api;

import com.app.utils.jwt.AuthenticationRequest;
import com.app.utils.jwt.AuthenticationResponse;
import com.app.utils.jwt.JwtUtil;
import com.app.utils.userdetails.MyUserDetailsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class MainMenuController {
    @GetMapping("/home")
    public String homePage(){
        return "index";
    }

}
