/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirpropertijavafxfadillahfajri;

import com.mycompany.kasirpropertijavafxfadillahfajri.User;
import com.mycompany.kasirpropertijavafxfadillahfajri.UserDAO;
/**
 *
 * @author Fadil
 */
public class UserController {
    public static boolean register(String username, String password, String role) {
        User user = new User(0, username, password, role);
        return UserDAO.registerUser(user);
    }

    public static User login(String username, String password) {
        return UserDAO.loginUser(username, password);
    }
}
