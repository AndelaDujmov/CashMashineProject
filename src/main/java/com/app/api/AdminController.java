package com.app.api;

import com.app.persistence.entities.User;
import com.app.persistence.entities.UserRole;
import com.app.persistence.repositories.UserRoleRepository;
import com.app.services.UserService;
import com.app.utils.admin.SoftDeletingService;
import com.app.utils.dto.UserDto;
import com.app.utils.enums.UserType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AdminController {

    @Autowired
    private final SoftDeletingService adminService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRoleRepository userRoleRepository;

    @GetMapping ("/userPage")
    public String usersPage( HttpServletRequest request, Model model){
        var Allusers = userService.getAll();
        var usersList = new ArrayList<UserDto>();
        Allusers.forEach( user -> usersList.add(new UserDto(user,  request)));
        model.addAttribute("This are the current users of the app:", usersList);
        return "adminPage";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "param") User user, String[] roles){

        user.getRoles().clear();
        Set userRoles = Arrays.stream(roles).map(UserType::valueOf).map(role -> {var userRoleOptional = userRoleRepository.findRoleByName(role);
        if (!userRoleOptional.equals(null))
            return userRoleOptional.getUserList();
            try {
                throw new IllegalAccessException("There is no user role like " + role);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toSet());

        user.setRoles(userRoles);
        return "/redirect" + "/edit";
    }

    @PostMapping("/retrieveTransaction/{idT}")
    public Model retrieve(Model model, @PathVariable  long idT, long idB){
        var transaction = adminService.retrieveTransactions(idT, idB);
        model.addAttribute("transaction added", transaction);
        return model;

    }

    @GetMapping("/checkTransactions")
    public Model checkDeletedTransactions(Model model, long id){
        model.addAttribute("the list of deleted transactions:", adminService.readBalance(id));
        return model;
    }

    @GetMapping("/check")
    public Model checkDeletedProducts(Model model){

        model.addAttribute("the list of deleted products:", adminService.readProducts());
        return model;
    }

    @PostMapping("/retrieveProduct/{id}")
    public Model retrieveProduct(Model model, @PathVariable(required = true) String productCode){
        var products = adminService.retrieveProduct(productCode);
        model.addAttribute("retrieved products", products);
        return model;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(long id){
        userService.deleteUser(id);
        return "/redirect";
    }
}
