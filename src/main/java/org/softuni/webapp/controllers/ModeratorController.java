package org.softuni.webapp.controllers;

import org.softuni.webapp.constants.AppConstants;
import org.softuni.webapp.constants.TitleConstants;
import org.softuni.webapp.domain.models.view.UserListModel;
import org.softuni.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

    private UserService userService;

    @Autowired
    public ModeratorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String viewAllUsers(Model model) {
        Set<UserListModel> allUsersAsModels = this.userService.getAllUsersAsListModels();

        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.ALL_USERS_VALUE);
        model.addAttribute(AppConstants.ALL_USERS_NAME, allUsersAsModels);

        return "users/all";
    }

    @GetMapping("/users/details/{username}")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String userDetails(@PathVariable("username") String username, Model model) {
        UserListModel userListModel = this.userService.findUserListModelByUsername(username);

        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.USER_DETAILS_VALUE);
        model.addAttribute(AppConstants.USER_DETAILS_MODEL, userListModel);

        return "users/details";
    }

    @GetMapping("/users/makeAdmin/{username}")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String makeUserAdmin(@PathVariable("username") String username) {
        this.userService.makeUserAdminByUsername(username);

        return "redirect:/moderator/users/all";
    }

    @GetMapping("/users/removeAdmin/{username}")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String removeAdminRoleFromUser(@PathVariable("username") String username) {
        this.userService.removeAdminRoleFromUser(username);

        return "redirect:/moderator/users/all";
    }
}
