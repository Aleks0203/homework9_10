package ru.mail.druk_aleksandr.app.repository.model;

public class User {

    private Integer id;
    private String username;
    private String password;
    private Boolean active;
    private Integer age;
    private UserInformation userInformation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }
}
