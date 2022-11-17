package com.budgeting.newbudgetapp.controller;


import com.budgeting.newbudgetapp.dao.UserDao;
import com.budgeting.newbudgetapp.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/users/current", method = RequestMethod.GET)
    public User getCurrentUser(Principal principal) {
        return userDao.findByUsername(principal.getName());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    @RequestMapping(path = "/users/role", method = RequestMethod.GET)
    public String getRole( Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        return userDao.getRole(userId);
    }

}
