package com.app.api;

import com.app.persistence.entities.User;
import com.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

public class EmployeeController {
    @Autowired
    private UserService userService;
  /*  @PostMapping("/add")
    public User getNewEmployee(User user)
    {
        //return userService.addNewUser(user);
    }

   */
}
