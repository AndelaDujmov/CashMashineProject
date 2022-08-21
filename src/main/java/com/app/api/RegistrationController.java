package com.app.api;

import com.app.persistence.entities.User;
import com.app.services.UserService;
import com.app.utils.dto.UserCreationDto;
import com.app.utils.jwt.AuthenticationRequest;
import com.app.utils.jwt.AuthenticationResponse;
import com.app.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public String getRegistrationPage(@RequestParam(name = "success", required = false) String success, Model model){
        model.addAttribute("success", success!=null);
        return "registration_page";
    }

    @PostMapping("/registration")
   public String registerNewUser(@RequestBody UserCreationDto userCreationDto, Model model){
        model.addAttribute("Registration", userCreationDto);
        return userService.register(userCreationDto);
   }

   @RequestMapping(value = "/login", method = RequestMethod.POST)
   public ResponseEntity<AuthenticationResponse> login(@RequestParam(name = "username", required = false) String username, @RequestParam(name = "password") String password) throws Exception {

        return authenticate(new AuthenticationRequest(username, password));
   }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password");
        }

       final var userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
       final var jwt = jwtUtil.generateToken(userDetails);

       return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
