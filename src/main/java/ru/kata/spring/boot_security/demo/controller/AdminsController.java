package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.RolePropertyEditor;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminsController {
    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("")
    public String getUsers(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User admin = (User) authentication.getPrincipal();
        model.addAttribute("admin", userService.show(admin.getId()));
        List<Role> roles = roleService.findAll();
        model.addAttribute("allRoles", roles);
        return "admin";
    }


    @PostMapping("")
    public String add(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIDs) {
        user.setRoles(roleService.getRolesByIDs(roleIDs));
        userService.add(user);
        return "redirect:/admin";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam("id") Long id, @RequestParam("roles") List<Long> roleIDs) {
        user.setRoles(roleService.getRolesByIDs(roleIDs));
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


}
