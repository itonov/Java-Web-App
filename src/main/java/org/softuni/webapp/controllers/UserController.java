package org.softuni.webapp.controllers;

import org.softuni.webapp.constants.AppConstants;
import org.softuni.webapp.constants.ErrorMessagesBg;
import org.softuni.webapp.constants.TitleConstants;
import org.softuni.webapp.domain.models.binding.UserRegisterBindingModel;
import org.softuni.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute(AppConstants.ERROR_NAME, ErrorMessagesBg.INVALID_USERNAME_OR_PASSWORD);
        }

        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.LOGIN_VALUE);

        return "users/login";
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String register(
            @ModelAttribute(name = "userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
            Model model) {

        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.REGISTER_VALUE);
        model.addAttribute(AppConstants.USER_REGISTER_MODEL_NAME, userRegisterBindingModel);

        return "users/register";
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  Model model) {
        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.REGISTER_VALUE);
        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {

            bindingResult.rejectValue(AppConstants.CONFIRM_PASSWORD_VALUE,
                    AppConstants.USER_REGISTER_MODEL_NAME,
                    ErrorMessagesBg.PASSWORDS_NOT_MATCHING);

            return "users/register";
        }

        boolean isEmailTaken = this.userService.isEmailAlreadyUsed(userRegisterBindingModel.getEmail());
        boolean isUsernameTaken = this.userService.isUsernameAlreadyUsed(userRegisterBindingModel.getUsername());

        if (isEmailTaken) {
            bindingResult.rejectValue(AppConstants.EMAIL_NAME,
                    AppConstants.USER_REGISTER_MODEL_NAME,
                    ErrorMessagesBg.EMAIL_ALREADY_EXISTS);
        }

        if (isUsernameTaken) {
            bindingResult.rejectValue(AppConstants.USERNAME_NAME,
                    AppConstants.USER_REGISTER_MODEL_NAME,
                    ErrorMessagesBg.USERNAME_ALREADY_EXISTS);
        }

        if (bindingResult.hasErrors()) {
            return "users/register";
        }

        this.userService.createUser(userRegisterBindingModel);

        return "redirect:/login";
    }
}
