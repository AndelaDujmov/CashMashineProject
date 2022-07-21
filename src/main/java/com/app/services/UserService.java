package com.app.services;


import com.app.persistence.entities.Cathegory;
import com.app.persistence.entities.User;
import com.app.persistence.repositories.UserRepository;
import com.app.persistence.repositories.UserRoleRepository;
import com.app.utils.dto.UserCreationDto;
import com.app.utils.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    public User createUser(UserCreationDto user){
        var role = userRoleRepository.findRoleByName(UserType.EMPLOYEE);
        var newUser = new User();
        newUser.setCodeName(user.getCodeName());
        newUser.setFirstName(user.getFirstName());
        newUser.setPassword(user.getPassword());
        newUser.setTokenExpired(false);
        return newUser;
    }

    public User findUserByRole(long idRole) { return userRepository.findUserByRoles(idRole); }

    public User findUserByID(long id){
        return userRepository.findUserByID(id);
    }

    public void deleteUser(long id){ userRepository.deleteById(id);}

    public void softDeleteUser(long id){
        var user = userRepository.findUserByID(id);
        user.setDeleted(true);
        userRepository.save(user);
    }

}
