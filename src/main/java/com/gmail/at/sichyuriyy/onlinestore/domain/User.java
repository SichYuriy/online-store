package com.gmail.at.sichyuriyy.onlinestore.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Yuriy on 3/27/2017.
 */
public class User {

    private Long id;
    private String login;
    private String password;
    private Boolean blackList;
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String login, String password, Boolean blackList) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.blackList = blackList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBlackList() {
        return blackList;
    }

    public void setBlackList(Boolean blackList) {
        this.blackList = blackList;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(blackList, user.blackList) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, blackList, roles);
    }
}
