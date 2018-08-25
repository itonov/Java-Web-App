package org.softuni.webapp.domain.models.view;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserListModel {
    private String username;

    private String email;

    private Date registeredOn;

    private Set<String> authoritiesAsString;

    public UserListModel() {
        this.authoritiesAsString = new HashSet<>();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisteredOn() {
        return this.registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    public Set<String> getAuthoritiesAsString() {
        return this.authoritiesAsString;
    }

    public void setAuthoritiesAsString(Set<String> authoritiesAsString) {
        this.authoritiesAsString = authoritiesAsString;
    }
}
