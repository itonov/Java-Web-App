package org.softuni.webapp.domain.models.binding;

import org.softuni.webapp.constants.ErrorMessagesBg;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {
    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    @NotEmpty(message = ErrorMessagesBg.EMPTY_USERNAME_FIELD)
    @Size(min = 4, max = 15, message = ErrorMessagesBg.INVALID_LENGTH_USERNAME)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = ErrorMessagesBg.EMPTY_EMAIL_FIELD)
    @Email(message = ErrorMessagesBg.INVALID_EMAIL_FIELD)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = ErrorMessagesBg.EMPTY_PASSWORD_FIELD)
    @Size(min = 3, max = 25, message = ErrorMessagesBg.INVALID_PASSWORD_LENGTH)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = ErrorMessagesBg.EMPTY_PASSWORD_FIELD)
    @Size(min = 3, max = 25, message = ErrorMessagesBg.INVALID_PASSWORD_LENGTH)
    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
